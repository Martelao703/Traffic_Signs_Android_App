package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

public class ITSApp {
    private boolean enabled;
    @SerializedName("Facilities")
    private Facilities facilities;

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
