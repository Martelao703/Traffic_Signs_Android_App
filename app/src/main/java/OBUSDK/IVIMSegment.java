package OBUSDK;

public class IVIMSegment extends PolygonalLine{

    private int segmentWidth;
    private double bearing;

    public IVIMSegment() {
    }

    public IVIMSegment(int segmentWidth, double bearing) {
        this.segmentWidth = segmentWidth;
        this.bearing = bearing;
    }

    public IVIMSegment(GPSCoordinate originPoint, GPSCoordinate endPoint, int segmentWidth)
    {
        super(originPoint, endPoint);
        this.segmentWidth = segmentWidth;
    }

    public IVIMSegment(double originLatitude, double originLongitude, double endLatitude,
                       double endLongitude, int segmentWidth)
    {
        GPSCoordinate originPoint = new GPSCoordinate(originLatitude, originLongitude);
        GPSCoordinate endPoint = new GPSCoordinate(endLatitude, endLongitude);
        this.setCoordinates(originPoint, endPoint);

        this.segmentWidth = segmentWidth;
    }


    public int getSegmentWidth() {
        return segmentWidth;
    }

    public void setSegmentWidth(int segmentWidth) {
        this.segmentWidth = segmentWidth;
    }

    public double getBearing() {
        return bearing;
    }

    public void setBearing(double bearing) {
        this.bearing = bearing;
    }
}
