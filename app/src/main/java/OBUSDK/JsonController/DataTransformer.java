package OBUSDK.JsonController;

import java.nio.ByteBuffer;
import java.util.List;

import OBUSDK.CoordinateConverter;
import OBUSDK.GPSCoordinate;
import OBUSDK.ISO14823Code;
import OBUSDK.IVIMSegment;
import OBUSDK.IVIZone;
import OBUSDK.IVIZoneType;
import OBUSDK.IsoSignalConverter;
import OBUSDK.PerEncDec.A5;
import OBUSDK.PerEncDec.DeltaPosition;
import OBUSDK.PerEncDec.GlcPart;
import OBUSDK.PerEncDec.IviContainer;
import OBUSDK.PerEncDec.Segment;
import OBUSDK.PerEncDec.Zone;

public class DataTransformer {

    private DataExtracter extracter;
    private CoordinateConverter converter;
    private IsoSignalConverter signalConverter;
    public static final int DEFAULT_LANE_WIDTH = 10;

    public DataTransformer(DataExtracter extracter) {
        this.extracter = extracter;
        this.converter = new CoordinateConverter();
        this.signalConverter = new IsoSignalConverter();
    }

    private GPSCoordinate getAndConvertReferencePosition() {
        IviContainer glcContainer = this.extracter.GetGlcContainer();
        GPSCoordinate gpsCoordinate = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());

        return converter.convertCoordinateInt2Double(gpsCoordinate);
    }

    private GPSCoordinate getReferencePosition() {
        IviContainer glcContainer = this.extracter.GetGlcContainer();
        return new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());
    }

    public IVIZoneType getZonesById(int zoneId) throws Exception {
        List<GlcPart> glcParts;
        IVIZoneType zones = new IVIZoneType();
        IVIZone zone;

        glcParts = this.extracter.GetZonesById(zoneId);

        if (glcParts.size() > 0) {
            if (this.extracter.GlcPartsHaveSegment()) {
                for (GlcPart glcPart : glcParts) {
                    if (glcPart.getZone().getSelected() == Zone.Id.SegmentChosen) {
                        zone = this.processSegmentPart(glcPart.getZone().getSegment());

                        if (zone != null) {
                            zones.getIVIZones().add(zone);
                        }
                    }
                }
                return zones;
            } else {
                throw new Exception("IVI Message does not contain segments. Only segments are supported");
            }
        }
        return null;
    }

    public Integer getZoneLaneWidthById(long zoneId) {
        GlcPart glcPart;
        glcPart = this.extracter.GetZoneById(zoneId);

        if (glcPart != null) {
            if (glcPart.getZone().getSegment().getLaneWidth() == null) {
                return DEFAULT_LANE_WIDTH;
            } else {
                return DEFAULT_LANE_WIDTH;
                //return glcPart.Zone.Segment.LaneWidth;
            }
        }
        return DEFAULT_LANE_WIDTH;
    }

    public IVIZone getZoneById(long zoneId) throws Exception {
        GlcPart glcPart;
        IVIZone zone;

        glcPart = this.extracter.GetZoneById(zoneId);

        if (glcPart != null) {
            if (this.extracter.GlcPartsHaveSegment()) {
                if (glcPart.getZone().getSelected() == Zone.Id.SegmentChosen) {
                    zone = this.processSegmentPart(glcPart.getZone().getSegment());

                    if (zone != null) {
                        return zone;
                    }
                } else {
                    throw new Exception("IVI Message does not contain segments. Only segments are supported");
                }
            } else {
                throw new Exception("IVI Message does not contain segments. Only segments are supported");
            }
        }
        return null;
    }

    private IVIZone processSegmentPart(Segment segment) {
        switch (segment.getPolygonalLine().Selected()) {
            case DeltaPositionsChosen:
                return this.processDeltaPositions(segment);
            case AbsolutePositionsChosen:
                return this.processAbsolutePositions(segment);
            default:
                return null;
        }
    }

    private IVIZone processAbsolutePositions(Segment segment) {
        IVIZone iviZone = new IVIZone();
        IVIMSegment internalSegment;
        int index = 0;
        boolean isFirstDelta = true;
        GPSCoordinate endPoint = null;
        GPSCoordinate lastEndPoint = new GPSCoordinate(0, 0);
        
        /*    foreach (AbsolutePosition absolutePosition in segment.Line.AbsolutePositions) 
    { 
        if (isFirstDelta)
        {
            lastEndPoint = new GPSCoordinate(absolutePosition.Latitude, absolutePosition.Longitude);
            lastEndPoint = converter.ConvertCoordinateInt2Double(lastEndPoint);

            isFirstDelta = false;
        }
        else
        {
            endPoint = new GPSCoordinate(absolutePosition.Latitude, absolutePosition.Longitude);
            endPoint = converter.ConvertCoordinateInt2Double(endPoint);
        }

        if (endPoint != null)
        {
            internalSegment = new IVIMSegment(lastEndPoint, endPoint, index, Convert.ToInt32(segment.LaneWidth));
            lastEndPoint = endPoint;
            index++;

            iviZone.Segments.Add(internalSegment);
 
        }

    }*/

        return iviZone;
    }

    private IVIZone processDeltaPositions(Segment segment) {
        IVIZone iviZone = new IVIZone();
        int index = 0;
        boolean isFirstDelta = true;
        GPSCoordinate endPoint = null;
        GPSCoordinate lastEndPoint = new GPSCoordinate(0, 0);

        GPSCoordinate refPosition = getReferencePosition();

        for (DeltaPosition deltaPosition : segment.getPolygonalLine().getDeltaPositions()) {
            if (isFirstDelta) {
                lastEndPoint = converter.convertToAbsolute((long)refPosition.getLatitude(), (long)refPosition.getLongitude(), deltaPosition.getDeltaLatitude(), deltaPosition.getDeltaLongitude());
                lastEndPoint = converter.convertCoordinateInt2Double(lastEndPoint);
                isFirstDelta = false;
            } else {
                endPoint = converter.convertToAbsolute((long)refPosition.getLatitude(), (long)refPosition.getLongitude(), deltaPosition.getDeltaLatitude(), deltaPosition.getDeltaLongitude());
                endPoint = converter.convertCoordinateInt2Double(endPoint);
            }

            if (endPoint != null) {
                IVIMSegment internalSegment = new IVIMSegment(lastEndPoint, endPoint, index, (int) segment.getLaneWidth());
                lastEndPoint = endPoint;
                index++;
                iviZone.getSegments().add(internalSegment);
            }
        }

        return iviZone;
    }

    public int getServiceCategoryCode(ISO14823Code.PictogramCodeType pictogramCodeType) {
        return this.signalConverter.getServiceCategoryCode(pictogramCodeType);
    }

    public int getPictogramCategoryCode(ISO14823Code.PictogramCodeType pictogramCode) {
        int nature;
        int serialNumber;
        nature = pictogramCode.PictogramCategoryCode.Nature;
        serialNumber = pictogramCode.PictogramCategoryCode.SerialNumber;

        return ((nature * 100) + serialNumber);
    }

    public int getPictogramCountryCode(ISO14823Code.PictogramCodeType pictogramCode) {
        ByteBuffer buffer = ByteBuffer.wrap(pictogramCode.getCountryCode());
        return buffer.getInt();
    }

    // ???????????? Substituí o return type de int para A5 ????????????
    public A5 getExtraTextLanguage(IviContainer iviContainer) {
        return iviContainer.getGiv().get(0).getExtraText().get(0).getLanguage();
    }

    public String getExtraTextContent(IviContainer iviContainer) {
        String content = iviContainer.getGiv().get(0).getExtraText().get(0).getTextContent();
        //if (!content.equals("System.Collections.Generic.List`1[System.Object]")) {
        int i = content.length();
        content = content.substring(0, i - 1);
        //} else {
        //    content = "";
        //}
        return content;
    }

    public long[] getDetectionZoneIds(IviContainer iviContainer) {
        int i = 0;

        long[] zoneIds = new long[iviContainer.getGiv().get(0).getDetectionZoneIds().size()];
        for (Long zoneID : iviContainer.getGiv().get(0).getDetectionZoneIds()) {
            zoneIds[i] = zoneID;
            i++;
        }
        return zoneIds;
    }

    public long[] getRelevantZoneIds(IviContainer iviContainer) {
        int i = 0;

        long[] zoneIds = new long[iviContainer.getGiv().get(0).getRelevanceZoneIds().size()];
        for (Long zoneID : iviContainer.getGiv().get(0).getRelevanceZoneIds()) {
            zoneIds[i] = zoneID;
            i++;
        }
        return zoneIds;
    }

    public long[] getAwarenessZoneIds(IviContainer iviContainer) {
        int i = 0;

        if (iviContainer.getGiv().get(0).getDriverAwarenessZoneIds() != null) {

            long[] zoneIds = new long[iviContainer.getGiv().get(0).getDriverAwarenessZoneIds().size()];
            for (Long zoneID : iviContainer.getGiv().get(0).getDriverAwarenessZoneIds()) {
                zoneIds[i] = zoneID;
                i++;
            }
            return zoneIds;
        } else {
            return null;
        }
    }

    public boolean hasAwarenessZone(IviContainer iviContainer) {
        return iviContainer.getGiv().get(0).getDriverAwarenessZoneIds() != null;
    }
}
