package OBUSDK;

import android.location.Location;

public class GPSController {
    private IVIMEngine engine;
    private double currentLatitude = -1;
    private double currentLongitude = -1;
    private double currentBearing = -1000;
    private boolean hasPreviousBearing = false;

    public GPSController(IVIMEngine engine) {
        this.engine = engine;
        this.hasPreviousBearing = false;
    }

    public void updateGPSLocation(GPSLocation gpsLocation) {
        updateGPSLocation(gpsLocation.getLatitude(), gpsLocation.getLongitude(), gpsLocation.getBearing());
    }

    public void updateGPSLocation(double latitude, double longitude, double bearing) {
        boolean updated = false;

        if (latitude != this.currentLatitude) {
            this.currentLatitude = latitude;
            updated = true;
        }

        if (longitude != this.currentLongitude) {
            this.currentLongitude = longitude;
            updated = true;
        }

        if (bearing != this.currentBearing) {
            // If invalid bearing received
            if (bearing < -180) {
                if (this.hasPreviousBearing) {
                    System.out.println("Last bearing used: " + this.currentBearing);
                    updated = true;
                }
            } else {
                this.currentBearing = bearing;
                updated = true;
                this.hasPreviousBearing = true;
            }
        }

        if (updated) {
            engine.processGPSLocation(new GPSLocation(this.currentLatitude, this.currentLongitude, this.currentBearing));
        }
    }
}
