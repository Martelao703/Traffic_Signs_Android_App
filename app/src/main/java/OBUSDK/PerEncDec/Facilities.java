package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Facilities {
    private boolean enabled;
    private int AppInterval;
    @SerializedName("IVIMAPP")
    private List<IVIMap> IVIMap;

    public Facilities(boolean enabled, int appInterval, List<IVIMap> IVIMap) {
        this.enabled = enabled;
        AppInterval = appInterval;
        this.IVIMap = IVIMap;
    }

    public Facilities() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getAppInterval() {
        return AppInterval;
    }

    public void setAppInterval(int appInterval) {
        AppInterval = appInterval;
    }

    public List<IVIMap> getIVIMap() {
        return IVIMap;
    }

    public void setIVIMap(List<IVIMap> IVIMap) {
        this.IVIMap = IVIMap;
    }
}
