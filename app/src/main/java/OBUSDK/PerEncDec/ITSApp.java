package OBUSDK.PerEncDec;

public class ITSApp {
    private boolean enabled;
    private Facilities facilities;

    public ITSApp(boolean enabled, Facilities facilities) {
        this.enabled = enabled;
        this.facilities = facilities;
    }

    public ITSApp() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Facilities getFacilities() {
        return facilities;
    }

    public void setFacilities(Facilities facilities) {
        this.facilities = facilities;
    }
}
