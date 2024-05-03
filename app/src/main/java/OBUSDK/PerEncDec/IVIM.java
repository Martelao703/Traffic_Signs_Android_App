package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

public class IVIM {
    @SerializedName("header")
    private ItsPduHeader itsPduHeader;
    @SerializedName("ivi")
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