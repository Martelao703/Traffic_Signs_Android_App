public class IVIMSegment extends PolygonalLine{

    private long idSegment;
    private int segmentWidth;
    private double bearing;

    public IVIMSegment() {
    }

    public IVIMSegment(long idSegment, int segmentWidth, double bearing) {
        this.idSegment = idSegment;
        this.segmentWidth = segmentWidth;
        this.bearing = bearing;
    }

    public IVIMSegment(GPSCoordinate originPoint, GPSCoordinate endPoint, long idSegment, int segmentWidth)
    {
        super(originPoint, endPoint);
        this.idSegment = idSegment;
        this.segmentWidth = segmentWidth;
    }

    public IVIMSegment(double originLatitude, double originLongitude, double endLatitude,
                       double endLongitude, long idSegment, int segmentWidth)
    {
        GPSCoordinate originPoint = new GPSCoordinate(originLatitude, originLongitude);
        GPSCoordinate endPoint = new GPSCoordinate(endLatitude, endLongitude);
        this.setCoordinates(originPoint, endPoint);

        this.idSegment = idSegment;
        this.segmentWidth = segmentWidth;
    }

    public long getIdSegment() {
        return idSegment;
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
