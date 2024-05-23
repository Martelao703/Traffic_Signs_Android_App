package OBUSDK.Extra;

public class ComputedSegment {
    private long zoneId;
    private long laneNumber;
    private int laneWidth;
    private Long offsetDistance;
    private DeltaReferencePosition offsetPosition;

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public long getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(long laneNumber) {
        this.laneNumber = laneNumber;
    }

    public int getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(int laneWidth) {
        this.laneWidth = laneWidth;
    }

    public Long getOffsetDistance() {
        return offsetDistance;
    }

    public void setOffsetDistance(Long offsetDistance) {
        this.offsetDistance = offsetDistance;
    }

    public DeltaReferencePosition getOffsetPosition() {
        return offsetPosition;
    }

    public void setOffsetPosition(DeltaReferencePosition offsetPosition) {
        this.offsetPosition = offsetPosition;
    }
}
