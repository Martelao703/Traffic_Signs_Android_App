package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facilities {
    private boolean enabled;
    @SerializedName("AppInterval")
    private int appInterval;
    @SerializedName("IVIMAPP")
    private List<IVIMap> IVIMap;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getAppInterval() {
        return appInterval;
    }

    public void setAppInterval(int appInterval) {
        this.appInterval = appInterval;
    }

    public List<IVIMap> getIVIMap() {
        return IVIMap;
    }

    public void setIVIMap(List<IVIMap> IVIMap) {
        this.IVIMap = IVIMap;
    }
}
