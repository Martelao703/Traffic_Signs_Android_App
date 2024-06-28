package OBUSDK;

public class GPSCoordinate {

    protected double longitude;
    protected double latitude;

    public GPSCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
