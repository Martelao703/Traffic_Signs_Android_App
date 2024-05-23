package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class Giv {
    @SerializedName("GicPart")
    private GicPart gicPart;

    public GicPart getGicPart() {
        return gicPart;
    }

    public void setGicPart(GicPart gicPart) {
        this.gicPart = gicPart;
    }
}
