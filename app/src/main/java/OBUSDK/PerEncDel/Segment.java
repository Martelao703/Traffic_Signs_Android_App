package OBUSDK.PerEncDel;

import OBUSDK.PolygonalLine;

public class Segment {
    private PolygonalLine polygonalLine;
    private Integer laneWidth;

    public PolygonalLine getPolygonalLine() {
        return polygonalLine;
    }

    public void setPolygonalLine(PolygonalLine polygonalLine) {
        this.polygonalLine = polygonalLine;
    }

    public Integer getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(Integer laneWidth) {
        this.laneWidth = laneWidth;
    }
}
