package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class Optional {
    @SerializedName("IviContainer")
    private IviContainer iviContainer;

    public IviContainer getIviContainer() {
        return iviContainer;
    }

    public void setIviContainer(IviContainer iviContainer) {
        this.iviContainer = iviContainer;
    }
}
