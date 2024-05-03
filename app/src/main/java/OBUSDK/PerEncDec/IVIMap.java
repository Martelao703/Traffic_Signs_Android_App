package OBUSDK.PerEncDec;

public class IVIMap {
    private boolean enabled;
    private IVIM ivim;

    public IVIMap() {
    }

    public IVIMap(boolean enabled, IVIM ivim) {
        this.enabled = enabled;
        this.ivim = ivim;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public IVIM getIvim() {
        return ivim;
    }

    public void setIvim(IVIM ivim) {
        this.ivim = ivim;
    }
}
