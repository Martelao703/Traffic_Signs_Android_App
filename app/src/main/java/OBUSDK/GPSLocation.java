package OBUSDK;

public class GPSLocation extends GPSCoordinate {
    private double bearing;

    public double getBearing() {
        return bearing;
    }

    public GPSLocation(double latitude, double longitude, double bearing) {
        super(latitude, longitude);
        this.bearing = bearing;
    }
}
