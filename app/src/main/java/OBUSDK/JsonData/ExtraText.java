package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class ExtraText {
    @SerializedName("Text")
    private Text text;

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }
}
