package OBUSDK.PerEncDec;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rsu implements Serializable {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
