package OBUSDK.JsonController;

import java.util.Date;

import OBUSDK.CoordinateConverter;
import OBUSDK.GPSCoordinate;
import OBUSDK.IControllerAdapter;
import OBUSDK.IVIZone;
import OBUSDK.IVIZoneEnum;
import OBUSDK.InternalIVIMMessage;
import OBUSDK.InternalIVIMMessageBuilder;
import OBUSDK.PerEncDec.IVIM;
import OBUSDK.IVIMMemoryStructures;
import OBUSDK.PerEncDec.Header;
import OBUSDK.PerEncDec.IviContainer;
import OBUSDK.PerEncDec.IviManagementContainer;
import OBUSDK.SafeByteConverter;

public class JsonAdapter implements IControllerAdapter {
    private IVIM rootIVI;

    public JsonAdapter(IVIM rootIVI) {
        this.rootIVI = rootIVI;
    }

    public IVIMMemoryStructures buildIVIMStructures() {
        IVIMMemoryStructures ivimMemoryStructures = new IVIMMemoryStructures();
        InternalIVIMMessage message = this.buildIVIMStructure();
        ivimMemoryStructures.getInternalIVIMessages().add(message);
        return ivimMemoryStructures;
    }

    public InternalIVIMMessage buildIVIMStructure() {
        boolean iVIMessageComplete = true;
        DataExtracter extracter = new DataExtracter(this.rootIVI);
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
        Header header = extracter.getItsPduHeader();

        if (header == null) {
            return null;
        }

        builder.createHeader(header.getProtocolVersion(), header.getMessageID(), header.getStationID());


        IviManagementContainer mandatory = extracter.getMandatoryContainer();
        //TODO - mandatory.ConnectedIviStructures is an array of longs

        int countryCode = byteConverter.countryCodeToInt32(mandatory.getServiceProviderId().getCountryCode().getA501());

        Date timeStamp = byteConverter.toIVIDateTime((long) mandatory.getTimeStamp());
        Date validFrom = byteConverter.toIVIDateTime((long) mandatory.getValidFrom());
        Date validTo = byteConverter.toIVIDateTime((long) mandatory.getValidTo());

        builder.createMandatory(countryCode, mandatory.getServiceProviderId().getProviderIdentifier(), mandatory.getIviIdentificationNumber(), timeStamp, validFrom, validTo, 0, mandatory.getIviStatus());

        IviContainer givContainer = extracter.GetGivContainer();
        IviContainer glcContainer = extracter.GetGlcContainer();
        GPSCoordinate refPosition = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());

        refPosition = coordConverter.convertCoordinateInt2Double(refPosition);

        serviceCategoryCode = transformer.getServiceCategoryCode(givContainer.getGiv().get(0).getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());
        pictogramCategoryCode = transformer.getPictogramCategoryCode(givContainer.getGiv().get(0).getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());
        countryCategoryCode = transformer.getPictogramCountryCode(givContainer.getGiv().get(0).getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());

        builder.createSignal(refPosition.getLatitude(), refPosition.getLongitude(), 0, countryCategoryCode, serviceCategoryCode, pictogramCategoryCode, 0);

        //textLanguage = transformer.GetExtraTextLanguage(givContainer);
        textLanguage = byteConverter.languageToInt32(givContainer.getGiv().get(0).getExtraText().get(0).getLanguage().getA501());
        textContent = transformer.getExtraTextContent(givContainer);

        builder.createSignalText(0, textLanguage, textContent);

        detectionZoneIDs = transformer.getDetectionZoneIds(givContainer);
        for (long zoneID : detectionZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID);
                builder.addZone(zone, IVIZoneEnum.IVI_ZONE_DETECTION, laneWidth);
            } catch (Exception e) {
                //TODO confirmar se é assim que se trata do erro
                System.out.println("Error: " + e.getMessage());
            }
        }

        relevanceZoneIDs = transformer.getRelevantZoneIds(givContainer);

        for (long zoneID : relevanceZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID);
                builder.addZone(zone, IVIZoneEnum.IVI_ZONE_RELEVANCE, laneWidth);
            } catch (Exception e) {
                //TODO confirmar se é assim que se trata do erro
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (transformer.hasAwarenessZone(givContainer)) {
            awarenessZoneIDs = transformer.getAwarenessZoneIds(givContainer);
            for (long zoneID : awarenessZoneIDs) {
                try {
                    zone = transformer.getZoneById(zoneID);
                    laneWidth = transformer.getZoneLaneWidthById(zoneID);
                    builder.addZone(zone, IVIZoneEnum.IVI_ZONE_AWARENESS, laneWidth);
                } catch (Exception e) {
                    //TODO confirmar se é assim que se trata do erro
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        return builder.buildMessage();
    }
}
