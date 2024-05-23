package OBUSDK.JsonData;

public class PosConfidenceEllipse {

    private int semiMajorConfidence;
    private int semiMinorConfidence;
    private int semiMajorOrientation;

    public int getSemiMajorConfidence() {
        return semiMajorConfidence;
    }

    public void setSemiMajorConfidence(int semiMajorConfidence) {
        this.semiMajorConfidence = semiMajorConfidence;
    }

    public int getSemiMinorConfidence() {
        return semiMinorConfidence;
    }

    public void setSemiMinorConfidence(int semiMinorConfidence) {
        this.semiMinorConfidence = semiMinorConfidence;
    }

    public int getSemiMajorOrientation() {
        return semiMajorOrientation;
    }

    public void setSemiMajorOrientation(int semiMajorOrientation) {
        this.semiMajorOrientation = semiMajorOrientation;
    }
}
