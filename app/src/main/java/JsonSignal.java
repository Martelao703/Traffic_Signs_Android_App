public class JsonSignal {

    private double refPosLatitude;
    private double refPosLongitude;
    private JsonDisplay jsonDisplay;

    public JsonSignal() {
        this.jsonDisplay = new JsonDisplay();
    }

    public double getRefPosLatitude() {
        return refPosLatitude;
    }

    public void setRefPosLatitude(double refPosLatitude) {
        this.refPosLatitude = refPosLatitude;
    }

    public double getRefPosLongitude() {
        return refPosLongitude;
    }

    public void setRefPosLongitude(double refPosLongitude) {
        this.refPosLongitude = refPosLongitude;
    }

    public JsonDisplay getJsonDisplay() {
        return jsonDisplay;
    }

    public void setJsonDisplay(JsonDisplay jsonDisplay) {
        this.jsonDisplay = jsonDisplay;
    }
}
