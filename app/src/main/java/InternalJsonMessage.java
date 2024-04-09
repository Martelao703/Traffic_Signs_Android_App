public class InternalJsonMessage {
    private IVIMHeader header;
    private JsonMandatory mandatory;
    private JsonSignal iviSignal;

    private IVIZoneType awarenessZones;
    private IVIZoneType detectionZone;
    private IVIZoneType relevanceZone;

    private iviUIStateEnum uIAwarenessZoneState;
    private iviUIStateEnum uIDetectionZoneState;
    private iviUIStateEnum uIRelevanceZoneState;

    public InternalJsonMessage() {
        this.mandatory = new JsonMandatory();
        this.iviSignal = new JsonSignal();
        this.awarenessZones = new IVIZoneType();
        this.detectionZone = new IVIZoneType();
        this.relevanceZone = new IVIZoneType();
        this.uIAwarenessZoneState = iviUIStateEnum.iviStateCurrentlyNotInZone;
        this.uIDetectionZoneState = iviUIStateEnum.iviStateCurrentlyNotInZone;
        this.uIRelevanceZoneState = iviUIStateEnum.iviStateCurrentlyNotInZone;
    }

    public IVIZoneType getAwarenessZones() {
        return awarenessZones;
    }

    public IVIZoneType getDetectionZone() {
        return detectionZone;
    }

    public IVIZoneType getRelevanceZone() {
        return relevanceZone;
    }

    public iviUIStateEnum getuIAwarenessZoneState() {
        return uIAwarenessZoneState;
    }

    public void setuIAwarenessZoneState(iviUIStateEnum uIAwarenessZoneState) {
        this.uIAwarenessZoneState = uIAwarenessZoneState;
    }

    public iviUIStateEnum getuIDetectionZoneState() {
        return uIDetectionZoneState;
    }

    public void setuIDetectionZoneState(iviUIStateEnum uIDetectionZoneState) {
        this.uIDetectionZoneState = uIDetectionZoneState;
    }

    public iviUIStateEnum getuIRelevanceZoneState() {
        return uIRelevanceZoneState;
    }

    public void setuIRelevanceZoneState(iviUIStateEnum uIRelevanceZoneState) {
        this.uIRelevanceZoneState = uIRelevanceZoneState;
    }

    public JsonMandatory getMandatory() {
        return mandatory;
    }

    public void setMandatory(JsonMandatory mandatory) {
        this.mandatory = mandatory;
    }

    public JsonSignal getIviSignal() {
        return iviSignal;
    }

    public void setIviSignal(JsonSignal iviSignal) {
        this.iviSignal = iviSignal;
    }

    public IVIMHeader getHeader() {
        return header;
    }

    public void setHeader(IVIMHeader header) {
        this.header = header;
    }
}

