package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class Data {
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
