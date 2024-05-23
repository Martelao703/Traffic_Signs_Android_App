package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class RoadSignCode {
    @SerializedName("RSCode")
    private RSCode rsCode;

    public RSCode getRsCode() {
        return rsCode;
    }

    public void setRsCode(RSCode rsCode) {
        this.rsCode = rsCode;
    }
}
