package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
