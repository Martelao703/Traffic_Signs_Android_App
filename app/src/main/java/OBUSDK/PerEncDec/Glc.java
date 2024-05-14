package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

public class Glc {
    private ReferencePosition referencePosition;
    private Long referencePositionTime;
    @SerializedName("parts")
    private GlcParts glcParts;

    //atributos heading e speed n vem no json, é preciso??

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

    public GlcParts getGlcParts() {
        return glcParts;
    }

    public void setGlcParts(GlcParts glcParts) {
        this.glcParts = glcParts;
    }
}
