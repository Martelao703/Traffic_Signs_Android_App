package OBUSDK.JsonData;

public class Altitude {
    private long altitudeValue;
    private int altitudeConfidence;

    public long getAltitudeValue() {
        return altitudeValue;
    }

    public void setAltitudeValue(long altitudeValue) {
        this.altitudeValue = altitudeValue;
    }

    public int getAltitudeConfidence() {
        return altitudeConfidence;
    }

    public void setAltitudeConfidence(int altitudeConfidence) {
        this.altitudeConfidence = altitudeConfidence;
    }
}
