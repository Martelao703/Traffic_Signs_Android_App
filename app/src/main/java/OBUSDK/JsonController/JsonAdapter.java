package OBUSDK.JsonController;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import OBUSDK.CoordinateConverter;
import OBUSDK.GPSCoordinate;
import OBUSDK.IControllerAdapter;
import OBUSDK.IVIZone;
import OBUSDK.IVIZoneEnum;
import OBUSDK.InternalIVIMMessage;
import OBUSDK.InternalIVIMMessageBuilder;
import OBUSDK.JsonData.IVIM;
import OBUSDK.IVIMMemoryStructures;
import OBUSDK.JsonData.Header;
import OBUSDK.JsonData.IviContainer;
import OBUSDK.JsonData.IviManagementContainer;
import OBUSDK.SafeByteConverter;

public class JsonAdapter implements IControllerAdapter {
    private IVIM rootIVI;

    public JsonAdapter(IVIM rootIVI) {
        this.rootIVI = rootIVI;
    }

    public IVIMMemoryStructures buildIVIMStructures() {
        IVIMMemoryStructures ivimMemoryStructures = new IVIMMemoryStructures();
        InternalIVIMMessage message = this.buildIVIMStructure();
        ivimMemoryStructures.getInternalIVIMessages().add(message);
        return ivimMemoryStructures;
    }

    public InternalIVIMMessage buildIVIMStructure() {
        boolean iVIMessageComplete = true;
        DataExtracter extracter = new DataExtracter(this.rootIVI);
        CoordinateConverter coordConverter = new CoordinateConverter();
        DataTransformer transformer = new DataTransformer(extracter);
        int serviceCategoryCode;
        int pictogramCategoryCode;
        int countryCategoryCode;
        int textLanguage;
        String textContent;
        IVIZone zone;
        long[] detectionZoneIDs;
        long[] awarenessZoneIDs;
        long[] relevanceZoneIDs;
        Integer laneWidth;
        SafeByteConverter byteConverter = new SafeByteConverter();

        InternalIVIMMessageBuilder builder = new InternalIVIMMessageBuilder();
        Header header = extracter.getItsPduHeader();

        if (header == null) {
            return null;
        }

        builder.createHeader(header.getProtocolVersion(), header.getMessageID(), header.getStationID());


        IviManagementContainer mandatory = extracter.getMandatoryContainer();
        //TODO - mandatory.ConnectedIviStructures is an array of longs

        int countryCode = byteConverter.countryCodeToInt32(mandatory.getServiceProvider().getCountryCode().getA501());

        String timeStamp = mandatory.getTimeStamp();
        String validFrom = mandatory.getValidFrom();
        String validTo = mandatory.getValidTo();

        builder.createMandatory(countryCode, mandatory.getServiceProvider().getProviderIdentifier(), mandatory.getIviIdentificationNumber(), timeStamp, validFrom, validTo, mandatory.getIviStatus());

        IviContainer givContainer = extracter.GetGivContainer();
        IviContainer glcContainer = extracter.GetGlcContainer();
        GPSCoordinate refPosition = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());

        refPosition = coordConverter.convertCoordinateInt2Double(refPosition);

        serviceCategoryCode = transformer.getServiceCategoryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());
        pictogramCategoryCode = transformer.getPictogramCategoryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());
        countryCategoryCode = transformer.getPictogramCountryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getCode().getIso14823().getPictogramCode());

        builder.createSignal(refPosition.getLatitude(), refPosition.getLongitude(), 0, countryCategoryCode, serviceCategoryCode, pictogramCategoryCode, 0);

        //textLanguage = transformer.GetExtraTextLanguage(givContainer);
        textLanguage = byteConverter.languageToInt32(givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getLanguage().getA501());
        textContent = transformer.getExtraTextContent(givContainer);

        builder.createSignalText(0, textLanguage, textContent);

        detectionZoneIDs = transformer.getDetectionZoneIds(givContainer);
        for (long zoneID : detectionZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID);
                builder.addZone(zone, IVIZoneEnum.IVI_ZONE_DETECTION, laneWidth);
            } catch (Exception e) {
                //TODO confirmar se é assim que se trata do erro
                System.out.println("Error: " + e.getMessage());
            }
        }

        relevanceZoneIDs = transformer.getRelevantZoneIds(givContainer);

        for (long zoneID : relevanceZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID);
                builder.addZone(zone, IVIZoneEnum.IVI_ZONE_RELEVANCE, laneWidth);
            } catch (Exception e) {
                //TODO confirmar se é assim que se trata do erro
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (transformer.hasAwarenessZone(givContainer)) {
            awarenessZoneIDs = transformer.getAwarenessZoneIds(givContainer);
            for (long zoneID : awarenessZoneIDs) {
                try {
                    zone = transformer.getZoneById(zoneID);
                    laneWidth = transformer.getZoneLaneWidthById(zoneID);
                    builder.addZone(zone, IVIZoneEnum.IVI_ZONE_AWARENESS, laneWidth);
                } catch (Exception e) {
                    //TODO confirmar se é assim que se trata do erro
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        return builder.buildMessage();
    }
}
