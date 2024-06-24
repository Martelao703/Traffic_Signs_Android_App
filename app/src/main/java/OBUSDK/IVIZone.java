package OBUSDK;

import java.util.ArrayList;
import java.util.List;

public class IVIZone {
    private List<IVIMSegment> segments;

    public IVIZone() {
        this.segments = new ArrayList<>();
    }

    public List<IVIMSegment> getSegments() {
        return segments;
    }

    public void setSegments(List<IVIMSegment> segments) {
        this.segments = segments;
    }

    public void addSegment(IVIMSegment segment) {
        this.segments.add(segment);
    }
}
