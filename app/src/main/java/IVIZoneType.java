import java.util.ArrayList;
import java.util.List;

public class IVIZoneType {

    private List<IVIZone> ivIZones;

    public IVIZoneType() {
        this.ivIZones = new ArrayList<IVIZone>();
    }

    public List<IVIZone> getIvIZones() {
        return ivIZones;
    }

    public void setIvIZones(List<IVIZone> ivIZones) {
        this.ivIZones = ivIZones;
    }
}
