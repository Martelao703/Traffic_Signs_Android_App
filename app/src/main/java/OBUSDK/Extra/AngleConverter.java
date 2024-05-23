package OBUSDK.Extra;

public class AngleConverter {
    public static double deg2Rad(double angleDegree) {
        return angleDegree * Math.PI / 180;
    }

    public static double rad2Deg(double angleRad) {
        return angleRad * 180 / Math.PI;
    }
}
