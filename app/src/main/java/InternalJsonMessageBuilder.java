import java.time.LocalDateTime;

public class InternalJsonMessageBuilder {

    private InternalJsonMessage ivimMessage;

    private ZonesAdapter awarenessZones;
    private ZonesAdapter detectionZone;
    private ZonesAdapter relevanceZone;

    private GPSCoordinate lastGPSCoordinate;

    public InternalJsonMessageBuilder() {
        this.ivimMessage = new InternalJsonMessage();
        this.awarenessZones = new ZonesAdapter();
        this.detectionZone = new ZonesAdapter();
        this.relevanceZone = new ZonesAdapter();
    }

    public ZonesAdapter getAwarenessZones() {
        return awarenessZones;
    }

    public ZonesAdapter getDetectionZone() {
        return detectionZone;
    }

    public ZonesAdapter getRelevanceZone() {
        return relevanceZone;
    }

    public boolean CreateHeader(int protocolVersion, int messageId, long stationId){
        if (messageId != 6) //6 - IVIM message
            return false;
        else {
            this.ivimMessage.setHeader(new IVIMHeader(stationId));
            return true;
        }
    }

    public void CreateMandatory (int countryCode, int providerIdentifier, long iviIdentificationNumber, LocalDateTime timeStamp,
                                 LocalDateTime validFrom, LocalDateTime validTo, int connectedIviStructures, int iviStatus){
        this.ivimMessage.getMandatory().getServiceProvider().setCountryCode(countryCode);
        this.ivimMessage.getMandatory().getServiceProvider().setProviderIdentifier(providerIdentifier);
        this.ivimMessage.getMandatory().setIviIdentificationNumber(iviIdentificationNumber);
        this.ivimMessage.getMandatory().setTimestamp(timeStamp);
        this.ivimMessage.getMandatory().setValidForm(validFrom);
        this.ivimMessage.getMandatory().setValidTo(validTo);
        this.ivimMessage.getMandatory().setConnectedJsonStructures(connectedIviStructures);
        this.ivimMessage.getMandatory().setJsonStatus(iviStatus);
    }

    public void CreateSignal(double refPosLatitude, double refPosLongitude, long layoutId, int countryCode, int serviceCategoryCode,
                             int pictogramCategoryCode, int layoutComponentId){
        this.ivimMessage.getIviSignal().setRefPosLatitude(refPosLatitude);
        this.ivimMessage.getIviSignal().setRefPosLongitude(refPosLongitude);
        this.ivimMessage.getIviSignal().getJsonDisplay().setLayoutId(layoutId);
        this.ivimMessage.getIviSignal().getJsonDisplay().getIso14823().setCountryCode(countryCode);
        this.ivimMessage.getIviSignal().getJsonDisplay().getIso14823().setServiceCategoryCode(serviceCategoryCode);
        this.ivimMessage.getIviSignal().getJsonDisplay().getIso14823().setPictogramCategoryCode(pictogramCategoryCode);
        this.ivimMessage.getIviSignal().getJsonDisplay().getIso14823().setLayoutComponentId(layoutComponentId);
    }

    public void CreateSignalText(int layoutComponentId, int language, String textContext){
        this.ivimMessage.getIviSignal().getJsonDisplay().getJsonText().setLayoutComponentId(layoutComponentId);
        this.ivimMessage.getIviSignal().getJsonDisplay().getJsonText().setLanguage(language);
        this.ivimMessage.getIviSignal().getJsonDisplay().getJsonText().setTextContent(textContext);
    }

    //Add awareness, detection and relevance zone points if there are any

    public InternalJsonMessage BuildMessage(){

        for (ZoneAdapter zone : this.awarenessZones.getZones()) {
            // Build awareness zone
            buildZone(zone, this.ivimMessage.getAwarenessZones(), false);
        }

        for (ZoneAdapter zone : this.detectionZone.getZones()) {
            // Build detection zone
            buildZone(zone, this.ivimMessage.getDetectionZone(), false);
        }

        for (ZoneAdapter zone : this.relevanceZone.getZones()) {
            // Build relevance zone
            buildZone(zone, this.ivimMessage.getRelevanceZone(), true);
        }

        return this.ivimMessage;
    }

    public void buildZone(ZoneAdapter zoneAdapter, IVIZoneType messageZones, boolean isRelevanceZone){
        boolean firstCoordinate;
        GPSCoordinate originPoint;
        int idSegment = 0;
        IVIZone iviZone = new IVIZone();
        GeoCalculator gc = new GeoCalculator();     //Não foi implementada ainda a classe, não esquecer!!!!!!

        firstCoordinate = true;
        originPoint = null;
        for (GPSCoordinate coordinate : zoneAdapter.getGPSCoordinates()) {
             if (firstCoordinate){
                 this.lastGPSCoordinate = coordinate;
                 firstCoordinate = false;
             }else{
                 originPoint = coordinate;
             }

             if (originPoint != null){
                 IVIMSegment segment = new IVIMSegment(originPoint, this.lastGPSCoordinate, idSegment++, 2);
                 if (!isRelevanceZone){
                     segment.setBearing(gc.GetBearing(originPoint, this.lastGPSCoordinate));
                 }else{
                     segment.setBearing(gc.GetBearing(this.lastGPSCoordinate, originPoint));
                 }

                 iviZone.getSegments().add(segment);
                    this.lastGPSCoordinate = originPoint;
             }
        }
        messageZones.getIvIZones().add(iviZone);
    }

    public void AddZone(IVIZone zone, iviZoneEnum zoneType, int laneWidth){

        this.AssignLaneWidthToZone(zone, laneWidth);

        switch (zoneType){
            case iviZoneDetection:
                this.AssignBearingsToZone(zone, false);
                this.ivimMessage.getDetectionZone().getIvIZones().add(zone);
                break;
            case iviZoneRelevance:
                this.AssignBearingsToZone(zone, true);
                this.ivimMessage.getRelevanceZone().getIvIZones().add(zone);
                break;
            case iviZoneAwareness:
                this.AssignBearingsToZone(zone, false);
                this.ivimMessage.getAwarenessZones().getIvIZones().add(zone);
                break;
        }
    }

    public void AssignBearingsToZone(IVIZone zone, boolean isRelevanceZone){
        GeoCalculator gc = new GeoCalculator();

        for (IVIMSegment segment : zone.getSegments()) {
            if (isRelevanceZone){
                segment.setBearing(gc.GetBearing(segment.getOrigin(), segment.getDestination()));
            }else{
                segment.setBearing(gc.GetBearing(segment.getDestination(), segment.getOrigin()));

            }
        }
    }

    private void AssignLaneWidthToZone(IVIZone zone, int laneWidth){
        for (IVIMSegment segment : zone.getSegments()) {
            segment.setSegmentWidth(laneWidth);
        }
    }



}
