package OBUSDK;

import java.util.ArrayList;
import java.util.List;

public class ZonesAdapter {
    private List<ZoneAdapter> zones;

    public ZonesAdapter() {
        this.zones = new ArrayList<ZoneAdapter>();
    }

    public List<ZoneAdapter> getZones() {
        return zones;
    }

    public ZoneAdapter addZone(){
        ZoneAdapter newZone = new ZoneAdapter();
        this.zones.add(newZone);

        return newZone;
    }
}
