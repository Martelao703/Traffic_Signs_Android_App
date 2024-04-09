public class IVIMSegmentGeoRectangle {

    public static final float EARTH_RADIUS = 6371000f; // Raio da Terra em metros
    private GPSCoordinate pointA;
    private GPSCoordinate pointB;
    private GPSCoordinate pointC;
    private GPSCoordinate pointD;

    private GeoCalculator geoCalculator;

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

    //Não foram inseridos os getters e setters dos angluso do retângulo

    public IVIMSegmentGeoRectangle() {
        this.geoCalculator = new GeoCalculator();
    }

    public void ComputeRect(PolygonalLine polygonalLine, double offset) {
        this.ComputeRect(polygonalLine.getOrigin(), polygonalLine.getDestination(), offset);
    }

    public void ComputeRect(GPSCoordinate origin, GPSCoordinate destination, double offset) {

        double bearingDegree = geoCalculator.GetBearing(origin, destination);   //BearingCalculator.GeoRefBearing(origin, destination);
        double angleMinus90 = bearingDegree - 90;
        double anglePlus90 = bearingDegree + 90;

        //TODO : Apenas funciona para angulos compreendidos entre -180 ... -360
        if (angleMinus90 < -180) {
            double angleTemp = angleMinus90 + 180;
            angleMinus90 = angleTemp + 180;
        }
        if (anglePlus90 > 180) {
            double angleTemp = anglePlus90 - 180;
            anglePlus90 = angleTemp - 180;
        }

        //this.pointA = this.OriginDistanceBearing2Destination(origin, anglePlus90, offset);
        this.pointA = geoCalculator.GetPointDistanceAndBearing(origin, offset, anglePlus90);
        this.pointB = geoCalculator.GetPointDistanceAndBearing(origin, offset, angleMinus90);

        this.pointC = geoCalculator.GetPointDistanceAndBearing(destination, offset, angleMinus90);
        //bearingDegree += 180;
        this.pointD = geoCalculator.GetPointDistanceAndBearing(destination, offset, anglePlus90);


    }

    /*
    TODO : Não traduzido
    private void DeterminatePositionPoints()
{
    GPSCoordinate maxLatPoint = pointA;

    if (maxLatPoint.Latitude < pointB.Latitude)
    {
        maxLatPoint = pointB;
    } else if (maxLatPoint.Latitude < pointC.Latitude)
    {
        maxLatPoint = pointC;
    } else if (maxLatPoint.Latitude < PointD.Latitude)
    {
        maxLatPoint = pointD;
    }



}*/


/*private void GetMaxLatitude(out GPSCoordinate maxAbs, out GPSCoordinate maxRel)
{
    List<GPSCoordinate> pointsRectangle = new List<GPSCoordinate>()
    {
        pointA, pointB, PointC, PointD
    };


    GPSCoordinate maxLatPoint1 = pointA;
    GPSCoordinate maxLatPoint2 = pointA;
    GPSCoordinate maxLongPoint = pointA;

    for (int i = 1; i < pointsRectangle.Count; i++)
    {
        if (pointsRectangle[i].Latitude > maxLatPoint1.Latitude)
        {

        }
    }
}*/





/*public void DeterminatePositionRectangle()
{
    GPSCoordinate pointHigherLat1;
    GPSCoordinate pointHigherLat2;
    GPSCoordinate pointBottomLong1;
    GPSCoordinate pointBottomLong2;

    List<GPSCoordinate> pointsRectangle = new List<GPSCoordinate>()
    {
        pointA, pointB, PointC, PointD
    };

    GPSCoordinate maxLatPoint = pointA;
    GPSCoordinate maxLongPoint = pointA;
    for (int i = 1; i < pointsRectangle.Count; i++)
    {
        /*for (int j = 0; j < pointsRectangle.Count; j++)
        {
            if (pointsRectangle[i].Latitude > pointsRectangle[j].Latitude)
            {
                pointsRectangle[i].Latitude
            }
        }*/

        /*if (pointsRectangle[i].Latitude > maxLatPoint.Latitude)
        {
            maxLatPoint = pointsRectangle[i];
        }
        if (pointsRectangle[i].Longitude > maxLatPoint.Longitude)
        {
            maxLongPoint = pointsRectangle[i];
        }
    }



    if (pointA.Latitude > pointB.Latitude)
    {
        pointHigherLat1 = pointA;
    }
    else
    {
        pointHigherLat1 = pointB;
    }
    if (pointC.Latitude > pointD.Latitude)
    {
        pointHigherLat2 = pointC;
    }
    else
    {
        pointHigherLat2 = pointD;
    }

    if (pointHigherLat1.Longitude > pointHigherLat2.Longitude)
    {
        UpperRight = pointHigherLat1;
        UpperLeft = pointHigherLat2;
    } else
    {
        UpperRight = pointHigherLat2;
        UpperLeft = pointHigherLat1;
    }

    if (pointC.Longitude > pointB.Longitude)
    {
        pointBottomLong1 =
    }

    if (higherLat < higherLat2)
    {
        higherLat = higherLat2;
    }

    if (lowerLat > lowerLat2)
    {
        lowerLat = lowerLat2;
    }

    if (pointA.Longitude > pointB.Longitude)
    {
        higherLong = pointA.Longitude;
        lowerLong = pointB.Longitude;
    }
    else
    {
        higherLong = pointB.Longitude;
        lowerLong = pointA.Longitude;
    }
    if (pointC.Longitude > pointD.Longitude)
    {
        higherLong2 = pointC.Longitude;
        lowerLong2 = pointD.Longitude;
    }
    else
    {
        higherLong2 = pointD.Longitude;
        lowerLong2 = pointC.Longitude;
    }

    if (higherLong < higherLong2)
    {
        higherLong = higherLong2;
    }

    if (lowerLong > lowerLong2)
    {
        lowerLong = lowerLong2;
    }

    UpperLeft = new GPSCoordinate(higherLat, lowerLong);
    UpperRight = new GPSCoordinate(higherLat, higherLong);
    BottomLeft = new GPSCoordinate(lowerLat, lowerLong);
    BottomRight = new GPSCoordinate(lowerLat, higherLong);
}*/

    /*
    Não foi implementado tambem porque nao existem os angulos do retângulo
    @Override
    public String toString() {
        return "UpperLeft: " + this.UpperLeft +
                "\nUpperRight: " + this.UpperRight +
                "\nBottomLeft: " + this.BottomLeft +
                "\nBottomRight: " + this.BottomRight;
    }
    */
}
