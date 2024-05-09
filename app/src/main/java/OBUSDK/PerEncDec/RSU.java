package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

public class RSU {
    private VirtualRSU virtualRSU;
    @SerializedName("ITSAPP")
    private ITSApp ITSApp;

    public RSU(VirtualRSU virtualRSU, OBUSDK.PerEncDec.ITSApp ITSApp) {
        this.virtualRSU = virtualRSU;
        this.ITSApp = ITSApp;
    }

    public RSU() {
    }

    public VirtualRSU getVirtualRSU() {
        return virtualRSU;
    }

    public void setVirtualRSU(VirtualRSU virtualRSU) {
        this.virtualRSU = virtualRSU;
    }

    public OBUSDK.PerEncDec.ITSApp getITSApp() {
        return ITSApp;
    }

    public void setITSApp(OBUSDK.PerEncDec.ITSApp ITSApp) {
        this.ITSApp = ITSApp;
    }
}
