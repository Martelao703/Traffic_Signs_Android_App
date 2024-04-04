public class PolygonalLine {

    private GPSCoordinate origin;
    private GPSCoordinate destination;

    public PolygonalLine(GPSCoordinate origin, GPSCoordinate destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public PolygonalLine() {
    }

    protected void setCoordinates(GPSCoordinate origin, GPSCoordinate destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public GPSCoordinate getOrigin() {
        return origin;
    }

    public GPSCoordinate getDestination() {
        return destination;
    }
}
