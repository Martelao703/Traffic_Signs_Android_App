package OBUSDK;

public class IVIMHeader {

    private long stationId;

    public IVIMHeader(long stationId) {
        this.stationId = stationId;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }
}
