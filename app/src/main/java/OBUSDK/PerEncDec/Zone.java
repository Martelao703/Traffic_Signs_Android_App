package OBUSDK.PerEncDec;

public class Zone {
    public enum Id {
        Unselected,
        SegmentChosen,
        AreaChosen,
        ComputedSegmentChosen
    }

    private Id _id;
    private Object _contained;

    public Id getSelected() {
        return _id;
    }

    public Segment getSegment() {
        return (_id == Id.SegmentChosen) ? ((Segment) _contained) : null;
    }

    public void setSegment(Segment segment) {
        _contained = segment;
        _id = Id.SegmentChosen;
    }

    public ComputedSegment getComputedSegment() {
        return (_id == Id.ComputedSegmentChosen) ? ((ComputedSegment) _contained) : null;
    }

    public void setComputedSegment(ComputedSegment computedSegment) {
        _contained = computedSegment;
        _id = Id.ComputedSegmentChosen;
    }
}
