public class InternalIVIMMessage {
    private IVIMHeader header;
    private IVIMMandatory mandatory;
    private IVISignal iviSignal;

    private IVIZoneType awarenessZones;
    private IVIZoneType detectionZones;
    private IVIZoneType relevanceZones;

    private iviUIStateEnum uIAwarenessZoneState;
    private iviUIStateEnum uIDetectionZoneState;
    private iviUIStateEnum uIRelevanceZoneState;

    public InternalIVIMMessage() {
        this.mandatory = new IVIMMandatory();
        this.iviSignal = new IVISignal();
        this.detectionZones = new IVIZoneType();
        this.relevanceZones = new IVIZoneType();
        this.awarenessZones = new IVIZoneType();
        this.uIAwarenessZoneState = iviUIStateEnum.iviStateCurrentlyNotInZone;
        this.uIDetectionZoneState = iviUIStateEnum.iviStateCurrentlyNotInZone;
        this.uIRelevanceZoneState = iviUIStateEnum.iviStateCurrentlyNotInZone;
    }

    public IVIZoneType getAwarenessZones() {
        return awarenessZones;
    }

    public IVIZoneType getRelevanceZones() {
        return relevanceZones;
    }

    public IVIZoneType getDetectionZones() {
        return detectionZones;
    }

    public iviUIStateEnum getUIAwarenessZoneState() {
        return uIAwarenessZoneState;
    }

    public void setUIAwarenessZoneState(iviUIStateEnum uIAwarenessZoneState) {
        this.uIAwarenessZoneState = uIAwarenessZoneState;
    }

    public iviUIStateEnum getUIDetectionZoneState() {
        return uIDetectionZoneState;
    }

    public void setUIDetectionZoneState(iviUIStateEnum uIDetectionZoneState) {
        this.uIDetectionZoneState = uIDetectionZoneState;
    }

    public iviUIStateEnum getUIRelevanceZoneState() {
        return uIRelevanceZoneState;
    }

    public void setUIRelevanceZoneState(iviUIStateEnum uIRelevanceZoneState) {
        this.uIRelevanceZoneState = uIRelevanceZoneState;
    }

    public IVIMHeader getHeader() {
        return header;
    }

    public void setHeader(IVIMHeader header) {
        this.header = header;
    }

    public IVIMMandatory getMandatory() {
        return mandatory;
    }

    public void setMandatory(IVIMMandatory mandatory) {
        this.mandatory = mandatory;
    }

    public IVISignal getIviSignal() {
        return iviSignal;
    }

    public void setIviSignal(IVISignal iviSignal) {
        this.iviSignal = iviSignal;
    }
}
