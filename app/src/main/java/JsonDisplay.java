public class JsonDisplay {

    private long layoutId;
    private ISO14823 iso14823;
    private JsonText jsonText;

    public JsonDisplay(){
        this.iso14823 = new ISO14823();
        this.jsonText = new JsonText();
    }

    public long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(long layoutId) {
        this.layoutId = layoutId;
    }

    public ISO14823 getIso14823() {
        return iso14823;
    }

    public void setIso14823(ISO14823 iso14823) {
        this.iso14823 = iso14823;
    }

    public JsonText getJsonText() {
        return jsonText;
    }

    public void setJsonText(JsonText jsonText) {
        this.jsonText = jsonText;
    }
}
