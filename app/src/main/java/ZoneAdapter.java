import java.util.ArrayList;
import java.util.List;

public class ZoneAdapter {

    private List<GPSCoordinate> GPSCoordinates;

    public ZoneAdapter() {
        this.GPSCoordinates = new ArrayList<GPSCoordinate>();
    }

    public List<GPSCoordinate> getGPSCoordinates() {
        return GPSCoordinates;
    }

    public void AddCoordinate(GPSCoordinate coordinate) {
        this.GPSCoordinates.add(coordinate);
    }

    public void AddCoordinate(double latitude, double longitude) {
        this.GPSCoordinates.add(new GPSCoordinate(latitude, longitude));
    }

    public void ClearCoordinates() {
        this.GPSCoordinates.clear();
    }
}
