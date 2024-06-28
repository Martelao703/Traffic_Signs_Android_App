package OBUSDK;

public class TriangleAreaOperator implements IGeoOperator {

    private double triangleArea = 0;
    //private DeltaPosition pointOfRectangle = null;
    private IVIMSegmentGeoRectangle rectangle = null;
    private final GeoCalculator geoCalculator;

    public double getTriangleArea() {
        return triangleArea;
    }

    public void setTriangleArea(double triangleArea) {
        this.triangleArea = triangleArea;
    }

    public TriangleAreaOperator() {
        this.geoCalculator = new GeoCalculator();
    }

    public boolean isInsideZone(GPSCoordinate point, PolygonalLine line, double maxOffSet){
        //(Diferenca bearing carro para bearing do linha) > 90
        // . FORA
        /*Stopwatch stopwatch = new Stopwatch();
        stopwatch.Start();*/
        double deltaAngle;

        double segmentBearing = geoCalculator.getBearing(line.getOrigin(), line.getDestination());

        double carDestinationBearing = geoCalculator.getBearing(point, line.getDestination());  // Inside when value are negative
        double carOriginBearing = geoCalculator.getBearing(point, line.getOrigin());  // Inside when value are positive


        if (segmentBearing < 0) {
            segmentBearing = 360 + segmentBearing;
        }
        if (carDestinationBearing < 0) {
            carDestinationBearing = 360 + carDestinationBearing;
        }
        if (carOriginBearing < 0) {
            carOriginBearing = 360 + carOriginBearing;
        }

        if (carOriginBearing > segmentBearing) {
            deltaAngle = carOriginBearing - segmentBearing;
        } else {
            deltaAngle = segmentBearing - carOriginBearing;
        }

        if (deltaAngle < 90 || deltaAngle > 270) {
            return false;
        }
        /*
        if (deltaAngle > 90) //carDestinationBearing > 0 || carOriginBearing < 0 ||
        {
            return false;

        }*/

        if (carDestinationBearing > segmentBearing) {
            deltaAngle = carDestinationBearing - segmentBearing;
        } else {
            deltaAngle = segmentBearing - carDestinationBearing;
        }

        if (deltaAngle > 90 && deltaAngle < 270) {
            return false;
        }


        //calcular fora
        if (rectangle == null) {
            rectangle = new IVIMSegmentGeoRectangle();
            rectangle.computeRect(line, maxOffSet);
        }

        if (triangleArea == 0) {
            /*OBUSDK.PolygonalLine line2 = new OBUSDK.PolygonalLine(new DeltaPosition(39.733745, -8.805327), new DeltaPosition(39.733974, -8.804694));
            OBUSDK.IVIMSegmentGeoRectangle rectangle2 = new OBUSDK.IVIMSegmentGeoRectangle();
            rectangle2.ComputeRect(line2, 20);*/
            triangleArea = this.ComputeAreaTriangle(line, rectangle.getPointB());
        }

        double areaCarTriangle = ComputeAreaTriangle(line, point);

        if (areaCarTriangle <= triangleArea) {  //dentro
            return true;
        }else { //fora
            return false;
        }
        //stopwatch.Stop();
    }

    private double ComputeAreaTriangle(PolygonalLine line, GPSCoordinate point){
        //-(1/2*(a1*(b2-c2)+b1*(c2-a2)+c1*(a2-b2)))
        // A - line.Origin
        // B - point
        // C - line.Destination
        // 1 - Latitude
        // 2 - Longitude

        return Math.abs(0.5* ((line.getOrigin().getLatitude()) * (point.getLongitude() - line.getDestination().getLongitude()) + point.getLatitude() * (line.getDestination().getLongitude() - line.getOrigin().getLongitude())
                + line.getDestination().getLatitude() * (line.getOrigin().getLongitude() - point.getLongitude())));
    }
}
