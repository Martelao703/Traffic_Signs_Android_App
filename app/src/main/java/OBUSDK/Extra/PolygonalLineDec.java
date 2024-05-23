package OBUSDK.Extra;

import OBUSDK.JsonData.DeltaPositions;

public class PolygonalLineDec {

    public enum Id{
        Unselected,
        DeltaPositionsChosen,
        AbsolutePositionsChosen
    }

    private Id _id;
    private Object _contained;
    /*public Id Selected() {
        return _id;
    }*/

    public DeltaPositions getDeltaPositions() {
        return (_id == Id.DeltaPositionsChosen) ? (DeltaPositions) _contained : null;
    }

    public void setDeltaPositions(DeltaPositions value) {
        _contained = value;
        _id = Id.DeltaPositionsChosen;
    }
}
