package OBUSDK.JsonData;

public class DeltaPosition {
    private long deltaLatitude;
    private long deltaLongitude;

    public long getDeltaLatitude() {
        return deltaLatitude;
    }

    public void setDeltaLatitude(long deltaLatitude) {
        this.deltaLatitude = deltaLatitude;
    }

    public long getDeltaLongitude() {
        return deltaLongitude;
    }

    public void setDeltaLongitude(long deltaLongitude) {
        this.deltaLongitude = deltaLongitude;
    }
}
