package OBUSDK.PerEncDec;

public class ReferencePosition {
    private long latitude;
    private long longitude;
    private PosConfidenceEllipse positionConfidenceEllipse;
    private Altitude altitude;

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
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
