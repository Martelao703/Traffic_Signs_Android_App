package OBUSDK.PerEncDec;

public class IVIM {
    private ItsPduHeader itsPduHeader;
    private IviStructure iviStructure;

    public ItsPduHeader getItsPduHeader() {
        return itsPduHeader;
    }

    public void setItsPduHeader(ItsPduHeader itsPduHeader) {
        this.itsPduHeader = itsPduHeader;
    }

    public IviStructure getIviStructure() {
        return iviStructure;
    }

    public void setIviStructure(IviStructure iviStructure) {
        this.iviStructure = iviStructure;
    }
}