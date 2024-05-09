package OBUSDK.PerEncDec;

public class Altitude {
    private long altitudeValue;
    private AltitudeConfidence altitudeConfidence;

    public long getAltitudeValue() {
        return altitudeValue;
    }

    public void setAltitudeValue(long altitudeValue) {
        this.altitudeValue = altitudeValue;
    }

    public AltitudeConfidence getAltitudeConfidence() {
        return altitudeConfidence;
    }

    public void setAltitudeConfidence(AltitudeConfidence altitudeConfidence) {
        this.altitudeConfidence = altitudeConfidence;
    }
}
