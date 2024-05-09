package OBUSDK.PerEncDec;

public class Glc {
    private ReferencePosition referencePosition;
    private Long referencePositionTime;
    private Parts parts;

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

    public Parts getParts() {
        return parts;
    }

    public void setParts(Parts parts) {
        this.parts = parts;
    }
}
