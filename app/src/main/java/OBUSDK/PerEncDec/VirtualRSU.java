package OBUSDK.PerEncDec;

import java.sql.Timestamp;

public class VirtualRSU {
    private int virtualStationID;
    private String reference;
    private String description;
    private ReferencePosition referencePosition;
    private int range;
    private String timestamp;

    public VirtualRSU(int virtualStationID, String reference, String description, ReferencePosition referencePosition, int range, String timestamp) {
        this.virtualStationID = virtualStationID;
        this.reference = reference;
        this.description = description;
        this.referencePosition = referencePosition;
        this.range = range;
        this.timestamp = timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
