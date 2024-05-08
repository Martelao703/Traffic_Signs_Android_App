package OBUSDK.PerEncDec;

public class Facilities {
    private boolean enabled;
    private int AppInterval;
    private IVIMap IVIMap;

    public Facilities(boolean enabled, int appInterval, OBUSDK.PerEncDec.IVIMap IVIMap) {
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

    public OBUSDK.PerEncDec.IVIMap getIVIMap() {
        return IVIMap;
    }

    public void setIVIMap(OBUSDK.PerEncDec.IVIMap IVIMap) {
        this.IVIMap = IVIMap;
    }
}
