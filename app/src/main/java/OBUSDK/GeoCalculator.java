package OBUSDK;

public class GeoCalculator {

    //https://nssdc.gsfc.nasa.gov/planetary/factsheet/earthfact.html
    //Equatorial radius(km)          6378.137
    //Polar radius(km)               6356.752
    //Volumetric mean radius(km)     6371.000

    /// <summary>
    /// Volumetric mean radius (in meters)
    /// </summary>

    public static final float EARTH_RADIUS = 6371000f; // Metros
    public static final float EARTH_EQUATORIAL_RADIUS = 6378137f; // Metros
    public static final float EARTH_POLAR_RADIUS = 6356752f; // Metros

    /// <summary>
    /// Calculate the georeference angle with one line
    /// </summary>
    /// <param name="polygonalLine">Line</param>
    /// <returns>Returns Angle in Degrees</returns>
    /*public static double GeoRefBearing(OBUSDK.PolygonalLine polygonalLine)
    {
        return GeoRefBearing(polygonalLine.Origin, polygonalLine.Destination);

    }*/

    /// <summary>
    /// Calculate the georeferente angle with two position
    /// </summary>
    /// <param name="origin">Origin position</param>
    /// <param name="destination">Destination Position</param>
    /// <returns></returns>getBearing
    public double getBearing(GPSCoordinate origin, GPSCoordinate destination){
        // RESULTADO MAL
        // X = cos θb * sin ∆L
        // Y = cos θa* sin θb – sin θa * cos θb * cos ∆L
        // β = atan2(X,Y),

        // Convert to Radians:

        double lat1 = Math.toRadians(origin.getLatitude());
        double long1 = Math.toRadians(origin.getLongitude());
        double lat2 = Math.toRadians(destination.getLatitude());
        double long2 = Math.toRadians(destination.getLongitude());

        //double delta = long2 - long1;
        double deltaDeg = destination.getLongitude() - origin.getLongitude();

        double delta = Math.toRadians(deltaDeg);

        double x = Math.cos(lat2) * Math.sin(delta);    //sofre arredondamento
        double y = (Math.cos(lat1) * Math.sin(lat2)) - (Math.sin(lat1) * Math.cos(lat2) * Math.cos(delta));
        double rad = Math.atan2(x, y);
        return Math.toDegrees(rad);
    }

    /// <summary>
    /// Calculate distance of given two geocoordeantes points
    /// </summary>
    /// <param name="pointA">In degree</param>
    /// <param name="pointB">In degree</param>
    /// <returns>In meters</returns>

    public double getDistance(GPSCoordinate pointA, GPSCoordinate pointB){
        double radOriginLat = Math.toRadians(pointA.getLatitude());
        double radDestinationLat = Math.toRadians(pointB.getLatitude());
        double deltaOrigin = Math.toRadians(pointB.getLatitude() - pointA.getLatitude());
        double deltaDestination = Math.toRadians(pointB.getLongitude() - pointA.getLongitude());
        double a = Math.pow(deltaOrigin / 2, 2) + Math.cos(radOriginLat) * Math.cos(radDestinationLat) * Math.pow((Math.sin(deltaDestination)) / 2, 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return this.getEarthRadius(pointA.getLatitude(), 0) * c;
        //return OBUSDK.GeoCalculator.EARTH_RADIUS * c;
    }

    public GPSCoordinate getIntersectionTwoPointsAndBearing(GPSCoordinate pointA, GPSCoordinate pointB, double bearingA, double bearingB){
        LatLonSpherical latlon = this.getIntersectionTwoPointsAndBearingLatLonSpherical(pointA, pointB, bearingA, bearingB);
        if (latlon == null){
            return null;
        }
        return new GPSCoordinate(latlon.get_lat(), latlon.get_lon());
    }

    protected LatLonSpherical getIntersectionTwoPointsAndBearingLatLonSpherical(GPSCoordinate pointA, GPSCoordinate pointB, double bearingA, double bearingB){
        double pointALat = Math.toRadians(pointA.getLatitude());
        double pointALng = Math.toRadians(pointA.getLongitude());
        double pointBLat = Math.toRadians(pointB.getLatitude());
        double pointBLng = Math.toRadians(pointB.getLongitude());

        double bearingARad = Math.toRadians(bearingA);
        double bearingBRad = Math.toRadians(bearingB);

        double deltaLat = pointBLat - pointALat;
        double deltaLng = pointBLng - pointALng;


        double angularDistance = 2 * Math.asin(Math.sqrt(Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2)
                + Math.cos(pointALat) * Math.cos(pointBLat) * Math.sin(deltaLng / 2) * Math.sin(deltaLng / 2)));
        if (Math.abs(angularDistance) < Double.MIN_VALUE) return new LatLonSpherical(pointA.getLatitude(), pointA.getLongitude()); // coincident points

        //initial/final bearings between points
        double cos0a = (Math.sin(pointBLat) - Math.sin(pointALat) * Math.cos(angularDistance)) / (Math.sin(angularDistance) * Math.cos(pointALat));
        double cos0b = (Math.sin(pointALat) - Math.sin(pointBLat) * Math.cos(angularDistance)) / (Math.sin(angularDistance) * Math.cos(pointBLat));
        double θa = Math.acos(Math.min(Math.max(cos0a, -1), 1));
        double θb = Math.acos(Math.min(Math.max(cos0b, -1), 1));

        double θ12 = Math.sin(pointBLng - pointALng) > 0 ? θa : 2 * Math.PI - θa;
        double θ21 = Math.sin(pointBLng - pointALng) > 0 ? 2 * Math.PI - θb : θb;

        double α1 = bearingARad - θ12;
        double α2 = θ21 - bearingBRad;

        if (Math.sin(α1) == 0 && Math.sin(α2) == 0) return null; // infinite intersections
        if (Math.sin(α1) * Math.sin(α2) < 0) return null; // ambiguous intersection (antipodal?)

        double cosα3 = -Math.cos(α1) * Math.cos(α2) + Math.sin(α1) * Math.sin(α2) * Math.cos(angularDistance);

        double δ13 = Math.atan2(Math.sin(angularDistance) * Math.sin(α1) * Math.sin(α2), Math.cos(α2) + Math.cos(α1) * cosα3);

        double φ3 = Math.asin(Math.min(Math.max(Math.sin(pointALat) * Math.cos(δ13) + Math.cos(pointALat) * Math.sin(δ13) * Math.cos(bearingARad), -1), 1));

        double Δλ13 = Math.atan2(Math.sin(bearingARad) * Math.sin(δ13) * Math.cos(pointALat), Math.cos(δ13) - Math.sin(pointALat) * Math.sin(φ3));
        double λ3 = pointALng + Δλ13;

        double lat = Math.toDegrees(φ3);
        double lon = Math.toDegrees(λ3);

        return new LatLonSpherical(lat, lon);

        // see www.edwilliams.org/avform.htm#Intersection
    }


    /// <summary>
    ///
    /// </summary>
    /// <param name="point"></param>
    /// <param name="distanceInMeters">In Meters</param>
    /// <param name="bearing"></param>
    /// <returns></returns>
    public GPSCoordinate getPointDistanceAndBearing(GPSCoordinate point, double distanceInMeters, double bearing)
    {
        //ref: https://www.movable-type.co.uk/scripts/latlong.html*/

        double angularDistance = distanceInMeters / getEarthRadius(point.getLatitude(), 0);
        //double angularDistance = distanceInMeters / OBUSDK.GeoCalculator.EARTH_RADIUS;
        double bearingRad = Math.toRadians(bearing);

        double latRad = Math.toRadians(point.getLatitude());
        double longRad = Math.toRadians(point.getLongitude());

        double sinpointLat = Math.sin(latRad) * Math.cos(angularDistance) + Math.cos(latRad) * Math.sin(angularDistance) * Math.cos(bearingRad);
        double pointLat = Math.asin(sinpointLat);
        double y = Math.sin(bearingRad) * Math.sin(angularDistance) * Math.cos(latRad);
        double x = Math.cos(angularDistance) - Math.sin(latRad) * sinpointLat;
        double pointLong = longRad + Math.atan2(y, x);

        double latitude = Math.toDegrees(pointLat);
        double longitude = Math.toDegrees(pointLong);

        LatLonSpherical newPoint = new LatLonSpherical(latitude, longitude);

        return new GPSCoordinate(newPoint.get_lat(), newPoint.get_lon());
    }

    // https://rechneronline.de/earth-radius/
        /*function docalc() {
	var x=parseFloat(document.getElementById("a").value.replace(",","."));
	var y=parseFloat(document.getElementById("b").value.replace(",","."));
	if(isNaN(x)||x>90||x<-90) {
		alert("Incorrect latitude");
		return false;
	}
	x*=Math.PI/180;
	var r1=6378137;
	var r2=6356752.3;
	var z=Math.sqrt((Math.pow(r1*r1*Math.cos(x),2)+Math.pow(r2*r2*Math.sin(x),2)) / (Math.pow(r1*Math.cos(x),2)+Math.pow(r2*Math.sin(x),2)));
	if(document.getElementById("s0").value==1) { se for 1, é em metros
		z*=.62137119;
		y*=.18939394;
	}
	var zz=z+y;
	z=Math.round(z)/1000;
	zz=Math.round(zz)/1000;
	document.getElementById("c").value=z; // Sea level
	document.getElementById("d").value=zz; //Earth radius at ground level:
	document.getElementById("e").value=2*z; // Earth diameter at sea level:
    }*/

    public double getEarthRadius(double latitude, double altitude){
        double latRad = Math.toRadians(latitude);
        double one = Math.pow(GeoCalculator.EARTH_EQUATORIAL_RADIUS * GeoCalculator.EARTH_EQUATORIAL_RADIUS * Math.cos(latRad), 2) + Math.pow(GeoCalculator.EARTH_POLAR_RADIUS * GeoCalculator.EARTH_POLAR_RADIUS * Math.sin(latRad), 2);
        double two = Math.pow(GeoCalculator.EARTH_EQUATORIAL_RADIUS * Math.cos(latRad), 2) + Math.pow(GeoCalculator.EARTH_POLAR_RADIUS * Math.sin(latRad), 2);

        return Math.sqrt(one / two) + altitude;
    }
}
























