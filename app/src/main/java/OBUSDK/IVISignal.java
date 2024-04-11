package OBUSDK;

public class IVISignal {
    private double refPosLatitude;
    private double refPosLongitude;
    private IVIDisplay IVIDisplay;

    public IVISignal() {
        this.IVIDisplay = new IVIDisplay();
    }

    public double getRefPosLatitude() {
        return refPosLatitude;
    }

    public void setRefPosLatitude(double refPosLatitude) {
        this.refPosLatitude = refPosLatitude;
    }

    public double getRefPosLongitude() {
        return refPosLongitude;
    }

    public void setRefPosLongitude(double refPosLongitude) {
        this.refPosLongitude = refPosLongitude;
    }

    public IVIDisplay getIviDisplay() {
        return IVIDisplay;
    }

    public void setIviDisplay(IVIDisplay IVIDisplay) {
        this.IVIDisplay = IVIDisplay;
    }
}