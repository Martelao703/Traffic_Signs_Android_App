package OBUSDK.Extra;

public class Speed {
    private int SpeedValue;
    private int SpeedConfidence;

    public Speed(int speedValue, int speedConfidence) {
        SpeedValue = speedValue;
        SpeedConfidence = speedConfidence;
    }

    public Speed() {
    }

    public int getSpeedValue() {
        return SpeedValue;
    }

    public void setSpeedValue(int speedValue) {
        SpeedValue = speedValue;
    }

    public int getSpeedConfidence() {
        return SpeedConfidence;
    }

    public void setSpeedConfidence(int speedConfidence) {
        SpeedConfidence = speedConfidence;
    }
}
