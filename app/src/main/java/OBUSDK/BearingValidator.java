package OBUSDK;

public class BearingValidator {
    private int validAngleOffset = 85;

    public boolean isBearingValid(GPSLocation vehicleLocation, IVIMSegment segment) {
        return isBearingValid(vehicleLocation.getBearing(), segment.getBearing());
    }

    public boolean isBearingValid(double vehicleBearing, double segmentBearing) {
        double negativeAngleLimit = 0;
        double positiveAngleLimit = 0;
        double negativeAngleLimit2 = 0;
        double positiveAngleLimit2 = 0;

        negativeAngleLimit = segmentBearing - validAngleOffset;
        positiveAngleLimit = segmentBearing + validAngleOffset;

        if (negativeAngleLimit < -180) {
            negativeAngleLimit2 = negativeAngleLimit + 360;
            negativeAngleLimit = -180;
            positiveAngleLimit2 = 180;
        }

        if (positiveAngleLimit > 180) {
            positiveAngleLimit2 = positiveAngleLimit - 360;
            positiveAngleLimit = 180;
            negativeAngleLimit2 = -180;
        }

        if (vehicleBearing <= positiveAngleLimit && vehicleBearing >= negativeAngleLimit) {
            return true;
        }

        if (vehicleBearing <= positiveAngleLimit2 && vehicleBearing >= negativeAngleLimit2) {
            return true;
        }

        return false;
    }
}
