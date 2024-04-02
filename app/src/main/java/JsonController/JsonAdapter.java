package JsonController;

public class JsonAdapter {
    private String rootIVI;

    public JsonAdapter(String rootIVI) {
        this.rootIVI = rootIVI;
    }

    public IVIMMemoryStructures buildIVIMStructures() {
        IVIMMemoryStructures iVIMMemoryStructures = new IVIMMemoryStructures();
        InternalIVIMMessage message = buildIVIMStructure();
        iVIMMemoryStructures.internalIVIMessages.add(message);
        return iVIMMemoryStructures;
    }

    public InternalIVIMMessage buildIVIMStructure() {
        DataExtracter extracter = new DataExtracter(rootIVI);
        CoordinateConverter coordConverter = new CoordinateConverter();
        DataTransformer transformer = new DataTransformer(extracter);
        int serviceCategoryCode;
        int pictogramCategoryCode;
        int countryCategoryCode;
        int textLanguage;
        String textContent;
        IVIZone zone;
        long[] detectionZoneIDs;
        long[] awarenessZoneIDs;
        long[] relevanceZoneIDs;
        Integer laneWidth;
        SafeByteConverter byteConverter = new SafeByteConverter();

        InternalIVIMMessageBuilder builder = new InternalIVIMMessageBuilder();
        ItsPduHeader header = extracter.getITSPduHeader();

        if (header == null) {
            return null;
        }

        builder.createHeader(header.getProtocolVersion(), header.getMessageID(), header.getStationID());

        IviManagementContainer mandatory = extracter.getMandatoryContainer();
        int countryCode = byteConverter.countryCodeToInt32(mandatory.getServiceProviderId().getCountryCode().getA501());

        DateTime timeStamp = byteConverter.toIVIDateTime(mandatory.getTimeStamp());
        DateTime validFrom = byteConverter.toIVIDateTime(mandatory.getValidFrom());
        DateTime validTo = byteConverter.toIVIDateTime(mandatory.getValidTo());

        builder.createMandatory(countryCode, mandatory.getServiceProviderId().getProviderIdentifier(),
                mandatory.getIviIdentificationNumber(), timeStamp, validFrom, validTo, 0, mandatory.getIviStatus());

        IviContainer givContainer = extracter.getGivContainer();
        IviContainer glcContainer = extracter.getGlcContainer();
        GPSCoordinate refPosition = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(),
                glcContainer.getGlc().getReferencePosition().getLongitude());

        refPosition = coordConverter.convertCoordinateInt2Double(refPosition);

        serviceCategoryCode = transformer.getServiceCategoryCode(
                givContainer.getGiv().get(0).getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());
        pictogramCategoryCode = transformer.getPictogramCategoryCode(
                givContainer.getGiv().get(0).getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());
        countryCategoryCode = transformer.getPictogramCountryCode(
                givContainer.getGiv().get(0).getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());

        builder.createSignal(refPosition.getLatitude(), refPosition.getLongitude(), 0, countryCategoryCode,
                serviceCategoryCode, pictogramCategoryCode, 0);

        textLanguage = byteConverter.languageToInt32(
                givContainer.getGiv().get(0).getExtraText().get(0).getLanguage().getA501());

        textContent = transformer.getExtraTextContent(givContainer);

        builder.createSignalText(0, textLanguage, textContent);

        detectionZoneIDs = transformer.getDetectionZoneIds(givContainer);
        for (long zoneID : detectionZoneIDs) {
            zone = transformer.getZoneById(zoneID);
            laneWidth = transformer.getZoneLaneWidthById(zoneID);
            builder.addZone(zone, iviZoneEnum.iviZoneDetection, laneWidth);
        }

        relevanceZoneIDs = transformer.getRelevantZoneIds(givContainer);
        for (long zoneID : relevanceZoneIDs) {
            zone = transformer.getZoneById(zoneID);
            laneWidth = transformer.getZoneLaneWidthById(zoneID);
            builder.addZone(zone, iviZoneEnum.iviZoneRelevance, laneWidth);
        }

        if (transformer.hasAwarenessZone(givContainer)) {
            awarenessZoneIDs = transformer.getAwarenessZoneIds(givContainer);
            for (long zoneID : awarenessZoneIDs) {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID);
                builder.addZone(zone, iviZoneEnum.iviZoneAwareness, laneWidth);
            }
        }

        return builder.buildMessage();
    }
}
