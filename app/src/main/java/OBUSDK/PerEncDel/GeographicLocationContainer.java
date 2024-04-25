package OBUSDK.PerEncDel;

public class GeographicLocationContainer {

    private ReferencePosition referencePosition;
    private Long referencePositionTime;
    private Heading referencePositionHeading;
    private Speed referencePositionSpeed;
    private GlcParts glcParts;

    public ReferencePosition getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(ReferencePosition referencePosition) {
        this.referencePosition = referencePosition;
    }

    public Long getReferencePositionTime() {
        return referencePositionTime;
    }

    public void setReferencePositionTime(Long referencePositionTime) {
        this.referencePositionTime = referencePositionTime;
    }

    public Heading getReferencePositionHeading() {
        return referencePositionHeading;
    }

    public void setReferencePositionHeading(Heading referencePositionHeading) {
        this.referencePositionHeading = referencePositionHeading;
    }

    public Speed getReferencePositionSpeed() {
        return referencePositionSpeed;
    }

    public void setReferencePositionSpeed(Speed referencePositionSpeed) {
        this.referencePositionSpeed = referencePositionSpeed;
    }

    public GlcParts getGlcParts() {
        return glcParts;
    }

    public void setGlcParts(GlcParts glcParts) {
        this.glcParts = glcParts;
    }
}