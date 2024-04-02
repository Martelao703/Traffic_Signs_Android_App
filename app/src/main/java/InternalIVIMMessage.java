public class InternalIVIMMessage {

    private JsonMandatory mandatory;
    private JsonSignal iviSignal;

    private IVIZoneType awarenessZones;
    private IVIZoneType detectionZone;
    private IVIZoneType relevanceZone;

    private IVIMEngine.iviUIStateEum uIAwarenessZoneState;
    private IVIMEngine.iviUIStateEum uIDetectionZoneState;
    private IVIMEngine.iviUIStateEum uIRelevanceZoneState;

    public InternalIVIMMessage() {
        this.mandatory = new JsonMandatory();
        this.iviSignal = new JsonSignal();
        this.awarenessZones = new IVIZoneType();
        this.detectionZone = new IVIZoneType();
        this.relevanceZone = new IVIZoneType();
        this.uIAwarenessZoneState = IVIMEngine.iviUIStateEum.IVI_STATE_CURRENTLY_NOT_IN_ZONE;
        this.uIDetectionZoneState = IVIMEngine.iviUIStateEum.IVI_STATE_CURRENTLY_NOT_IN_ZONE;
        this.uIRelevanceZoneState = IVIMEngine.iviUIStateEum.IVI_STATE_CURRENTLY_NOT_IN_ZONE;
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

    public IVIMEngine.iviUIStateEum getuIAwarenessZoneState() {
        return uIAwarenessZoneState;
    }

    public void setuIAwarenessZoneState(IVIMEngine.iviUIStateEum uIAwarenessZoneState) {
        this.uIAwarenessZoneState = uIAwarenessZoneState;
    }

    public IVIMEngine.iviUIStateEum getuIDetectionZoneState() {
        return uIDetectionZoneState;
    }

    public void setuIDetectionZoneState(IVIMEngine.iviUIStateEum uIDetectionZoneState) {
        this.uIDetectionZoneState = uIDetectionZoneState;
    }

    public IVIMEngine.iviUIStateEum getuIRelevanceZoneState() {
        return uIRelevanceZoneState;
    }

    public void setuIRelevanceZoneState(IVIMEngine.iviUIStateEum uIRelevanceZoneState) {
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

    //Falta o IVIMHeader que não sei se foi apagado na classe do C# ou o que se passou!!!!!!

}

