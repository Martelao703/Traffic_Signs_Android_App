package OBUSDK;

public class IVIMDataEventArgs {
    private IVISignal signal;
    private GPSLocation vehicleLocation;

    // IVI Signal identifier
    private long stationID;
    private long iviIdentificationNumber;

    // IVI Signal data
    private int signalCountryCode;
    private int serviceCategoryCode;
    private int pictogramCategoryCode;
    private int language;
    private String textContent;

    public IVIMDataEventArgs(InternalIVIMMessage ivimMessage, GPSLocation vehicleLocation) {
        this.signal = ivimMessage.getIviSignal();
        this.vehicleLocation = vehicleLocation;

        this.stationID = ivimMessage.getHeader().getStationId();
        this.iviIdentificationNumber = ivimMessage.getMandatory().getIviIdentificationNumber();
    }

    public IVISignal getSignal() {
        return signal;
    }

    public GPSLocation getVehicleLocation() {
        return vehicleLocation;
    }

    public long getStationID() {
        return stationID;
    }

    public long getIviIdentificationNumber() {
        return iviIdentificationNumber;
    }
}
