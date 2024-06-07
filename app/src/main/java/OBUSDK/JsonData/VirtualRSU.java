package OBUSDK.JsonData;

import java.io.Serializable;

public class VirtualRSU {
    private int virtualStationID;
    private String reference;
    private String description;
    private ReferencePosition referencePosition;
    private int range;
    private String timeStamp;
    private double distance;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getVirtualStationID() {
        return virtualStationID;
    }

    public void setVirtualStationID(int virtualStationID) {
        this.virtualStationID = virtualStationID;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReferencePosition getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(ReferencePosition referencePosition) {
        this.referencePosition = referencePosition;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
