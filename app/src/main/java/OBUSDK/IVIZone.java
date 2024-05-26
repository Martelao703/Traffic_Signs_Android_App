package OBUSDK;

import OBUSDK.JsonData.Segment;

public class IVIZone {

    private IVIMSegment segment;

    public IVIZone(IVIMSegment segments) {
        this.segment = segments;
    }

    public IVIZone() {
    }

    public IVIMSegment getSegment() {
        return segment;
    }

    public void setSegment(IVIMSegment segment) {
        this.segment = segment;
    }
}
