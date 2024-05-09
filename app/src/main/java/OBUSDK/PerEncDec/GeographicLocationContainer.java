package OBUSDK.PerEncDec;

import java.util.List;

public class GeographicLocationContainer {

    private ReferencePosition referencePosition;
    private Long referencePositionTime;
    private Heading referencePositionHeading;
    private Speed referencePositionSpeed;
    private List<GlcPart> glcParts;

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

    public List<GlcPart> getGlcParts() {
        return glcParts;
    }

    public void setGlcParts(List<GlcPart> glcParts) {
        this.glcParts = glcParts;
    }
}