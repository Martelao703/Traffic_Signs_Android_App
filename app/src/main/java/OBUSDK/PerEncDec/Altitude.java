package OBUSDK.PerEncDec;

public class Altitude {
    private long AltitudeValue;
    private AltitudeConfidence AltitudeConfidence;

    public long getAltitudeValue() {
        return AltitudeValue;
    }

    public void setAltitudeValue(long altitudeValue) {
        AltitudeValue = altitudeValue;
    }

    public AltitudeConfidence getAltitudeConfidence() {
        return AltitudeConfidence;
    }

    public void setAltitudeConfidence(AltitudeConfidence altitudeConfidence) {
        AltitudeConfidence = altitudeConfidence;
    }
}
