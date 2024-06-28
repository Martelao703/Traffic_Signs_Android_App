package OBUSDK.JsonController;

import java.util.List;

import OBUSDK.CoordinateConverter;
import OBUSDK.GPSCoordinate;
import OBUSDK.IVIMSegment;
import OBUSDK.IVIZone;
import OBUSDK.IsoSignalConverter;
import OBUSDK.JsonData.DeltaPosition;
import OBUSDK.JsonData.GlcPart;
import OBUSDK.JsonData.IviContainer;
import OBUSDK.JsonData.PictogramCodeType;
import OBUSDK.JsonData.Segment;
import OBUSDK.JsonData.ZoneIds;

public class DataTransformer {


    private DataExtracter extracter;
    private CoordinateConverter converter;
    private IsoSignalConverter signalConverter;
    private CoordinateConverter coordConverter;
    public static final int DEFAULT_LANE_WIDTH = 10;

    public DataTransformer(DataExtracter extracter) {
        this.extracter = extracter;
        this.converter = new CoordinateConverter();
        this.signalConverter = new IsoSignalConverter();
    }

    private GPSCoordinate getAndConvertReferencePosition() {
        IviContainer glcContainer = this.extracter.getGlcContainer();
        GPSCoordinate gpsCoordinate = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());

        return converter.convertCoordinateInt2Double(gpsCoordinate);
    }

    private GPSCoordinate getReferencePosition() {
        IviContainer glcContainer = this.extracter.getGlcContainer();
        return new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());
    }

    /*
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
    */

    public Integer getZoneLaneWidthById(long zoneId) {
        GlcPart glcPart;
        glcPart = this.extracter.getGlcPartByZoneId(zoneId);

        if (glcPart != null) {
            /*
            if (glcPart.getZone().getSegment().getLaneWidth() == null) {
                return DEFAULT_LANE_WIDTH;
            } else {
                return DEFAULT_LANE_WIDTH;
                //return glcPart.Zone.Segment.LaneWidth;
            }
            */
            return glcPart.getZone().getSegment().getLaneWidth();
        }

        return null;
    }


    //TODO - double if & zone so tem 1 segment
    public IVIZone getZoneById(ZoneIds zoneId) throws Exception {
        GlcPart glcPart;
        IVIZone zone;

        glcPart = this.extracter.getGlcPartByZoneId(zoneId.getZid());

        if (glcPart != null) {
            if (this.extracter.glcPartsHaveSegment()) {
                    zone = this.processSegmentPart(glcPart.getZone().getSegment());

                    if (zone != null) {
                        return zone;
                    }
            } else {
                throw new Exception("IVI Message does not contain segments. Only segments are supported");
            }
        }
        return null;
    }

    // TODO - zone so tem 1 tipo de segment
    private IVIZone processSegmentPart(Segment segment) {
        return this.processDeltaPositions(segment);
    }


    /*
    private IVIZone processAbsolutePositions(Segment segment) {
        IVIZone iviZone = new IVIZone();
        IVIMSegment internalSegment;
        int index = 0;
        boolean isFirstDelta = true;
        GPSCoordinate endPoint = null;
        GPSCoordinate lastEndPoint = new GPSCoordinate(0, 0);

        private IVIZone processAbsolutePositions (Segment segment){
            IVIZone iviZone = new IVIZone();
            IVIMSegment internalSegment;
            int index = 0;
            boolean isFirstDelta = true;
            GPSCoordinate endPoint = null;
            GPSCoordinate lastEndPoint = new GPSCoordinate(0, 0);

            /*
            foreach (AbsolutePosition absolutePosition in segment.Line.AbsolutePositions)
            {
            if (isFirstDelta)
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

            }
            return iviZone;
        }
    }
    */


    private IVIZone processDeltaPositions(Segment segment) {
        IVIZone iviZone = new IVIZone();
        boolean isFirstDelta = true;
        GPSCoordinate endPoint = null;
        GPSCoordinate lastEndPoint = new GPSCoordinate(0, 0);

        GPSCoordinate refPosition = getReferencePosition();
        //GPSCoordinate refPosition = converter.convertCoordinateInt2Double(getReferencePosition());

        for (DeltaPosition deltaPosition : segment.getLine().getDeltaPositions().getDeltaPosition()) {
            if (isFirstDelta) {
                lastEndPoint = converter.convertToAbsolute((long) refPosition.getLatitude(), (long) refPosition.getLongitude(), deltaPosition.getDeltaLatitude(), deltaPosition.getDeltaLongitude());
                lastEndPoint = converter.convertCoordinateInt2Double(lastEndPoint);
                isFirstDelta = false;
            } else {
                endPoint = converter.convertToAbsolute((long) refPosition.getLatitude(), (long) refPosition.getLongitude(), deltaPosition.getDeltaLatitude(), deltaPosition.getDeltaLongitude());
                endPoint = converter.convertCoordinateInt2Double(endPoint);
            }

            if (endPoint != null) {
                IVIMSegment internalSegment = new IVIMSegment(lastEndPoint, endPoint, (int) segment.getLaneWidth());
                lastEndPoint = endPoint;
                iviZone.addSegment(internalSegment);
            }
        }

        return iviZone;

    }

    public int getServiceCategoryCode(PictogramCodeType pictogramCodeType) {
        return this.signalConverter.getServiceCategoryCode(pictogramCodeType);
    }

    public int getPictogramCategoryCode(PictogramCodeType pictogramCode) {
        int nature;
        int serialNumber;
        nature = pictogramCode.getPictogramCategoryCode().getNature();
        serialNumber = pictogramCode.getPictogramCategoryCode().getSerialNumber();

        return ((nature * 100) + serialNumber);
    }

    /*
    public int getPictogramCountryCode(PictogramCodeType pictogramCode) {
        ByteBuffer buffer = ByteBuffer.wrap(pictogramCode.getCountryCode());
        return buffer.getInt();
    }
    /*
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
    */

    public List<ZoneIds> getDetectionZoneIds(IviContainer iviContainer) {
        return iviContainer.getGiv().get(0).getGicPart().getDetectionZoneIds();
    }

    public List<ZoneIds> getRelevanceZoneIds(IviContainer iviContainer) {
       return iviContainer.getGiv().get(0).getGicPart().getRelevanceZoneIds();
    }

    public List<ZoneIds> getAwarenessZoneIds(IviContainer iviContainer) {

        if (hasAwarenessZone(iviContainer)) {
            return iviContainer.getGiv().get(0).getGicPart().getDriverAwarenessZoneIds();
        } else {
            return null;
        }
    }

    public boolean hasAwarenessZone(IviContainer iviContainer) {
        return iviContainer.getGiv().get(0).getGicPart().getDriverAwarenessZoneIds() != null;
    }
}
