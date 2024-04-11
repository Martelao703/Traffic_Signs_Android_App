package OBUSDK;

public class CoordinateConverter {
    public static final double COEFFICIENT = 0.000001;

    public double convertValue(int value) {
        return value * COEFFICIENT;
    }

    public GPSCoordinate convertCoordinateInt2Double(int latitude, int longitude) {
        return new GPSCoordinate(latitude * COEFFICIENT, longitude * COEFFICIENT);
    }

    public GPSCoordinate convertCoordinateInt2Double(GPSCoordinate coordinate) {
        return convertCoordinateInt2Double((int) coordinate.getLatitude(), (int) coordinate.getLongitude());
    }

    public GPSCoordinate convertToAbsolute(long refPointLatitude, long refPointLongitude, long deltaLatitude, long deltaLongitude) {
        long latitude = refPointLatitude - deltaLatitude;
        long longitude = refPointLongitude - deltaLongitude;
        return new GPSCoordinate(latitude, longitude);
    }
}
