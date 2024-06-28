package OBUSDK.JsonData;

import com.google.gson.annotations.SerializedName;

public class ReferencePosition {
    @SerializedName("lat")
    private float latitude;
    @SerializedName("lng")
    private float longitude;
    @SerializedName("positionConfidenceElipse")
    private PosConfidenceEllipse positionConfidenceEllipse;
    private Altitude altitude;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public PosConfidenceEllipse getPositionConfidenceEllipse() {
        return positionConfidenceEllipse;
    }

    public void setPositionConfidenceEllipse(PosConfidenceEllipse positionConfidenceEllipse) {
        this.positionConfidenceEllipse = positionConfidenceEllipse;
    }

    public Altitude getAltitude() {
        return altitude;
    }

    public void setAltitude(Altitude altitude) {
        this.altitude = altitude;
    }
}
