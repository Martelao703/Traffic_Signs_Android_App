package OBUSDK.JsonData;

public class Segment {
    private Line line;
    private int laneWidth;

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public int getLaneWidth() {
        return laneWidth;
    }

    public void setLaneWidth(int laneWidth) {
        this.laneWidth = laneWidth;
    }
}
