package OBUSDK.Extra;

public class DeltaReferencePosition {
    private long deltaLatitude;
    private long deltaLongitude;
    private long deltaAltitude;

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

    public long getDeltaAltitude() {
        return deltaAltitude;
    }

    public void setDeltaAltitude(long deltaAltitude) {
        this.deltaAltitude = deltaAltitude;
    }
}
