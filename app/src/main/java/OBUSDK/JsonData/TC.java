package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class TC {
    @SerializedName("TcPart")
    private TcPart tcPart;

    public TcPart getTcPart() {
        return tcPart;
    }

    public void setTcPart(TcPart tcPart) {
        this.tcPart = tcPart;
    }
}
