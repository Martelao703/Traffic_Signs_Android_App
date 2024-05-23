package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Parts {
    @SerializedName("GlcPart")
    private List<GlcPart> glcPart;

    public List<GlcPart> getGlcPart() {
        return glcPart;
    }

    public void setGlcPart(List<GlcPart> glcPart) {
        this.glcPart = glcPart;
    }
}
