public class TriangleAreaOperator implements IGeoOperator {
    private double triangleArea = 0;
    private IVIMSegmentGeoRectangle rectangle = null;
    private GeoCalculator geoCalculator = null;

    public double getTriangleArea() {
        return triangleArea;
    }

    public void setTriangleArea(double triangleArea) {
        this.triangleArea = triangleArea;
    }

    public TriangleAreaOperator() {
        geoCalculator = new GeoCalculator();
    }

    @Override
    public boolean isInsideZone(GPSCoordinate point, PolygonalLine line, double maxOffset) {
        double deltaAngle;

        double segmentBearing = geoCalculator.getBearing(line.getOrigin(), line.getDestination());

        double carDestinationBearing = geoCalculator.getBearing(point, line.getDestination());
        double carOriginBearing = geoCalculator.getBearing(point, line.getOrigin());

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

        if (carDestinationBearing > segmentBearing) {
            deltaAngle = carDestinationBearing - segmentBearing;
        } else {
            deltaAngle = segmentBearing - carDestinationBearing;
        }

        if (deltaAngle > 90 && deltaAngle < 270) {
            return false;
        }

        if (rectangle == null) {
            rectangle = new IVIMSegmentGeoRectangle();
            rectangle.computeRect(line, maxOffset);
        }

        if (triangleArea == 0) {
            triangleArea = this.computeAreaTriangle(line, rectangle.getPointB());
        }

        double areaCarTriangle = computeAreaTriangle(line, point);

        return areaCarTriangle <= triangleArea;
    }

    private double computeAreaTriangle(PolygonalLine line, GPSCoordinate point) {
        return Math.abs(0.5 * ((line.getOrigin().getLatitude()) * (point.getLongitude() - line.getDestination().getLongitude())
                + point.getLatitude() * (line.getDestination().getLongitude() - line.getOrigin().getLongitude())
                + line.getDestination().getLatitude() * (line.getOrigin().getLongitude() - point.getLongitude())));
    }
}
