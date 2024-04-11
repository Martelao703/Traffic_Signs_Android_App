package OBUSDK;

public interface IGeoOperator {
    boolean isInsideZone(GPSCoordinate point, PolygonalLine segment, double maxOffset);
}