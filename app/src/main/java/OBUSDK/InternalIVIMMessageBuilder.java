package OBUSDK;

public class InternalIVIMMessageBuilder {
    private InternalIVIMMessage ivimMessage;
    private ZonesAdapter awarenessZones;
    private ZonesAdapter detectionZones;
    private ZonesAdapter relevanceZones;
    private GPSCoordinate lastGPSCoordinate;

    public InternalIVIMMessageBuilder() {
        this.ivimMessage = new InternalIVIMMessage();
        this.awarenessZones = new ZonesAdapter();
        this.detectionZones = new ZonesAdapter();
        this.relevanceZones = new ZonesAdapter();
    }

    public ZonesAdapter getAwarenessZones() {
        return awarenessZones;
    }

    public ZonesAdapter getDetectionZones() {
        return detectionZones;
    }

    public ZonesAdapter getRelevanceZones() {
        return relevanceZones;
    }

    public boolean createHeader(int protocolVersion, int messageId, long stationId) {
        if (messageId != 6) { // 6 - IVI Message
            return false;
        } else {
            this.ivimMessage.setHeader(new IVIMHeader(stationId));
            return true;
        }
    }

    public void createMandatory(int countryCode, int providerIdentifier, long iviIdentificationNumber,
                                String timeStamp, String validFrom, String validTo, int iviStatus) {
        this.ivimMessage.getMandatory().getServiceProvider().setCountryCode(countryCode);
        this.ivimMessage.getMandatory().getServiceProvider().setProviderIdentifier(providerIdentifier);
        this.ivimMessage.getMandatory().setIviIdentificationNumber(iviIdentificationNumber);
        this.ivimMessage.getMandatory().setTimeStamp(timeStamp);
        this.ivimMessage.getMandatory().setValidFrom(validFrom);
        this.ivimMessage.getMandatory().setValidTo(validTo);
        this.ivimMessage.getMandatory().setIviStatus(iviStatus);
    }

    public void createSignal(double refPosLatitude, double refPosLongitude, long layoutId, int countryCode,
                             int serviceCategoryCode, int pictogramCategoryCode, int layoutComponentId) {
        this.ivimMessage.getIviSignal().setRefPosLatitude(refPosLatitude);
        this.ivimMessage.getIviSignal().setRefPosLongitude(refPosLongitude);
        this.ivimMessage.getIviSignal().getIviDisplay().setLayoutId(layoutId);
        this.ivimMessage.getIviSignal().getIviDisplay().getIso14823().setCountryCode(countryCode);
        this.ivimMessage.getIviSignal().getIviDisplay().getIso14823().setServiceCategoryCode(serviceCategoryCode);
        this.ivimMessage.getIviSignal().getIviDisplay().getIso14823().setPictogramCategoryCode(pictogramCategoryCode);
        this.ivimMessage.getIviSignal().getIviDisplay().getIso14823().setLayoutComponentId(layoutComponentId);
    }

    public void createSignalNoIso(double refPosLatitude, double refPosLongitude, long layoutId) {
        this.ivimMessage.getIviSignal().setRefPosLatitude(refPosLatitude);
        this.ivimMessage.getIviSignal().setRefPosLongitude(refPosLongitude);
        this.ivimMessage.getIviSignal().getIviDisplay().setLayoutId(layoutId);
        //this.ivimMessage.getIviSignal().getIviDisplay().getIso14823().setLayoutComponentId(layoutComponentId);
    }

    public void createSignalText(int layoutComponentId, int language, String textContent) {
        this.ivimMessage.getIviSignal().getIviDisplay().getIviText().setLayoutComponentId(layoutComponentId);
        this.ivimMessage.getIviSignal().getIviDisplay().getIviText().setLanguage(language);
        this.ivimMessage.getIviSignal().getIviDisplay().getIviText().setTextContent(textContent);
    }

    public InternalIVIMMessage buildMessage() {
        for (ZoneAdapter zone : this.awarenessZones.getZones()) {
            buildZone(zone, this.ivimMessage.getAwarenessZones(), false);
        }
        for (ZoneAdapter zone : this.detectionZones.getZones()) {
            buildZone(zone, this.ivimMessage.getDetectionZones(), false);
        }
        for (ZoneAdapter zone : this.relevanceZones.getZones()) {
            buildZone(zone, this.ivimMessage.getRelevanceZones(), true);
        }
        return this.ivimMessage;
    }

    private void buildZone(ZoneAdapter zoneAdapter, IVIZoneType messageZones, boolean isRelevanceZone) {
        boolean firstCoordinate = true;
        GPSCoordinate originPoint = null;
        IVIZone iviZone = new IVIZone();
        GeoCalculator gc = new GeoCalculator();

        for (GPSCoordinate coordinate : zoneAdapter.getGPSCoordinates()) {
            if (firstCoordinate) {
                this.lastGPSCoordinate = coordinate;
                firstCoordinate = false;
            } else {
                originPoint = coordinate;
            }

            if (originPoint != null) {
                IVIMSegment segment = new IVIMSegment(originPoint, this.lastGPSCoordinate, 2);
                if (!isRelevanceZone) {
                    segment.setBearing(gc.getBearing(originPoint, this.lastGPSCoordinate));
                } else {
                    segment.setBearing(gc.getBearing(this.lastGPSCoordinate, originPoint));
                }
                iviZone.addSegment(segment);
                this.lastGPSCoordinate = originPoint;
            }
        }
        messageZones.getIVIZones().add(iviZone);
    }

    public void addZone(IVIZone zone, IVIZoneEnum zoneType, int laneWidth) {
        assignLaneWidthToZone(zone, laneWidth);
        switch (zoneType) {
            case IVI_ZONE_DETECTION:
                assignBearingsToZone(zone, false);
                this.ivimMessage.getDetectionZones().getIVIZones().add(zone);
                break;
            case IVI_ZONE_RELEVANCE:
                assignBearingsToZone(zone, true);
                this.ivimMessage.getRelevanceZones().getIVIZones().add(zone);
                break;
            case IVI_ZONE_AWARENESS:
                assignBearingsToZone(zone, false);
                this.ivimMessage.getAwarenessZones().getIVIZones().add(zone);
                break;
        }
    }

    private void assignBearingsToZone(IVIZone zone, boolean isRelevanceZone) {
        GeoCalculator gc = new GeoCalculator();
        for (IVIMSegment segment : zone.getSegments()) {
            if (isRelevanceZone) {
                segment.setBearing(gc.getBearing(segment.getOrigin(), segment.getDestination()));
            } else {
                segment.setBearing(gc.getBearing(segment.getDestination(), segment.getOrigin()));
            }
        }
    }

    private void assignLaneWidthToZone(IVIZone zone, int laneWidth) {
        for (IVIMSegment segment : zone.getSegments()) {
            segment.setSegmentWidth(laneWidth);
        }
    }
}