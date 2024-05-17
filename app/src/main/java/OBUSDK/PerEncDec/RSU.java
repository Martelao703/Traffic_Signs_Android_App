package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

import OBUSDK.PerEncDec.ITSApp;

public class RSU {
    private VirtualRSU virtualRSU;
    @SerializedName("ITSAPP")
    private ITSApp ITSApp;

    public VirtualRSU getVirtualRSU() {
        return virtualRSU;
    }

    public void setVirtualRSU(VirtualRSU virtualRSU) {
        this.virtualRSU = virtualRSU;
    }

    public ITSApp getITSApp() {
        return ITSApp;
    }

    public void setITSApp(ITSApp ITSApp) {
        this.ITSApp = ITSApp;
    }
}
