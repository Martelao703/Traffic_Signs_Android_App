package OBUSDK;

import java.util.ArrayList;
import java.util.List;

public class IVIZoneType {
    private List<IVIZone> iVIZones;

    public IVIZoneType() {
        this.iVIZones = new ArrayList<>();
    }

    public List<IVIZone> getIVIZones() {
        return iVIZones;
    }

    public void setIVIZones(List<IVIZone> iVIZones) {
        this.iVIZones = iVIZones;
    }
}