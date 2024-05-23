package OBUSDK;

import OBUSDK.JsonData.IviManagementContainer;

public class InternalIVIMMessage {
    private IVIMHeader header;
    private IviManagementContainer mandatory;
    private IVISignal iviSignal;

    private IVIZoneType awarenessZones;
    private IVIZoneType detectionZones;
    private IVIZoneType relevanceZones;

    private IVIUIStateEnum uIAwarenessZoneState;
    private IVIUIStateEnum uIDetectionZoneState;
    private IVIUIStateEnum uIRelevanceZoneState;

    public InternalIVIMMessage() {
        this.mandatory = new IviManagementContainer();
        this.iviSignal = new IVISignal();
        this.detectionZones = new IVIZoneType();
        this.relevanceZones = new IVIZoneType();
        this.awarenessZones = new IVIZoneType();
        this.uIAwarenessZoneState = IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE;
        this.uIDetectionZoneState = IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE;
        this.uIRelevanceZoneState = IVIUIStateEnum.IVI_STATE_CURRENTLY_NOT_IN_ZONE;
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

    public IVIUIStateEnum getUIAwarenessZoneState() {
        return uIAwarenessZoneState;
    }

    public void setUIAwarenessZoneState(IVIUIStateEnum uIAwarenessZoneState) {
        this.uIAwarenessZoneState = uIAwarenessZoneState;
    }

    public IVIUIStateEnum getUIDetectionZoneState() {
        return uIDetectionZoneState;
    }

    public void setUIDetectionZoneState(IVIUIStateEnum uIDetectionZoneState) {
        this.uIDetectionZoneState = uIDetectionZoneState;
    }

    public IVIUIStateEnum getUIRelevanceZoneState() {
        return uIRelevanceZoneState;
    }

    public void setUIRelevanceZoneState(IVIUIStateEnum uIRelevanceZoneState) {
        this.uIRelevanceZoneState = uIRelevanceZoneState;
    }

    public IVIMHeader getHeader() {
        return header;
    }

    public void setHeader(IVIMHeader header) {
        this.header = header;
    }

    public IviManagementContainer getMandatory() {
        return mandatory;
    }

    public void setMandatory(IviManagementContainer mandatory) {
        this.mandatory = mandatory;
    }

    public IVISignal getIviSignal() {
        return iviSignal;
    }

    public void setIviSignal(IVISignal iviSignal) {
        this.iviSignal = iviSignal;
    }
}
