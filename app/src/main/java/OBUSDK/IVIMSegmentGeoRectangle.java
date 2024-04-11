package OBUSDK;

public class IVIMSegmentGeoRectangle {
    public static final float EARTH_RADIUS = 6371000f; // Radius of the Earth
    private GPSCoordinate pointA;
    private GPSCoordinate pointB;
    private GPSCoordinate pointC;
    private GPSCoordinate pointD;

    private GeoCalculator geoCalculator;

    public IVIMSegmentGeoRectangle() {
        geoCalculator = new GeoCalculator();
    }

    public GPSCoordinate getPointA() {
        return pointA;
    }

    public void setPointA(GPSCoordinate pointA) {
        this.pointA = pointA;
    }

    public GPSCoordinate getPointB() {
        return pointB;
    }

    public void setPointB(GPSCoordinate pointB) {
        this.pointB = pointB;
    }

    public GPSCoordinate getPointC() {
        return pointC;
    }

    public void setPointC(GPSCoordinate pointC) {
        this.pointC = pointC;
    }

    public GPSCoordinate getPointD() {
        return pointD;
    }

    public void setPointD(GPSCoordinate pointD) {
        this.pointD = pointD;
    }

    public GPSCoordinate getUpperLeft() {
        return UpperLeft;
    }

    public void setUpperLeft(GPSCoordinate upperLeft) {
        UpperLeft = upperLeft;
    }

    public GPSCoordinate getUpperRight() {
        return UpperRight;
    }

    public void setUpperRight(GPSCoordinate upperRight) {
        UpperRight = upperRight;
    }

    public GPSCoordinate getBottomLeft() {
        return BottomLeft;
    }

    public void setBottomLeft(GPSCoordinate bottomLeft) {
        BottomLeft = bottomLeft;
    }

    public GPSCoordinate getBottomRight() {
        return BottomRight;
    }

    public void setBottomRight(GPSCoordinate bottomRight) {
        BottomRight = bottomRight;
    }

    private GPSCoordinate UpperLeft;
    private GPSCoordinate UpperRight;
    private GPSCoordinate BottomLeft;
    private GPSCoordinate BottomRight;

    public void computeRect(PolygonalLine polygonalLine, double offset) {
        this.computeRect(polygonalLine.getOrigin(), polygonalLine.getDestination(), offset);
    }

    public void computeRect(GPSCoordinate origin, GPSCoordinate destination, double offset) {

        double bearingDegree = geoCalculator.getBearing(origin, destination); // BearingCalculator.GeoRefBearing(origin, destination);
        double angleMinus90 = bearingDegree - 90;
        double anglePlus90 = bearingDegree + 90;

        // TODO: Only works for angles between -180..-360
        if (angleMinus90 < -180) {
            double angleTemp = angleMinus90 + 180;
            angleMinus90 = angleTemp + 180;
        }
        if (anglePlus90 > 180) {
            double angleTemp = anglePlus90 - 180;
            anglePlus90 = angleTemp - 180;
        }

        this.pointA = geoCalculator.getPointDistanceAndBearing(origin, offset, anglePlus90);
        this.pointB = geoCalculator.getPointDistanceAndBearing(origin, offset, angleMinus90);
        this.pointC = geoCalculator.getPointDistanceAndBearing(destination, offset, angleMinus90);
        this.pointD = geoCalculator.getPointDistanceAndBearing(destination, offset, anglePlus90);
    }

    @Override
    public String toString() {
        return "UpperLeft: " + this.UpperLeft +
                "\nUpperRight: " + this.UpperRight +
                "\nBottomLeft: " + this.BottomLeft +
                "\nBottomRight: " + this.BottomRight;
    }
}
