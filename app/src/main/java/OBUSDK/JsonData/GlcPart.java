package OBUSDK.JsonData;

public class GlcPart {
    private long zoneId;
    private Long laneNumber;
    private Integer zoneExtension;
    private Integer zoneHeading;
    private Zone zone;

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getLaneNumber() {
        return laneNumber;
    }

    public void setLaneNumber(Long laneNumber) {
        this.laneNumber = laneNumber;
    }

    public Integer getZoneExtension() {
        return zoneExtension;
    }

    public void setZoneExtension(Integer zoneExtension) {
        this.zoneExtension = zoneExtension;
    }

    public Integer getZoneHeading() {
        return zoneHeading;
    }

    public void setZoneHeading(Integer zoneHeading) {
        this.zoneHeading = zoneHeading;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
}