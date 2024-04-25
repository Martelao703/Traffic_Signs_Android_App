package OBUSDK.PerEncDec;

import OBUSDK.PolygonalLine;

public class Segment {
    private PolygonalLineDec polygonalLineDec;
    private Integer laneWidth;

    public PolygonalLineDec getPolygonalLine() {
        return polygonalLineDec;
    }

    public void setPolygonalLine(PolygonalLineDec polygonalLineDec) {
        this.polygonalLineDec = polygonalLineDec;
    }

    public Integer getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(Integer laneWidth) {
        this.laneWidth = laneWidth;
    }
}
