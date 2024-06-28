package OBUSDK.JsonController;

import java.util.List;

import OBUSDK.CoordinateConverter;
import OBUSDK.GPSCoordinate;
import OBUSDK.IControllerAdapter;
import OBUSDK.IVIMSegment;
import OBUSDK.IVIZone;
import OBUSDK.IVIZoneEnum;
import OBUSDK.InternalIVIMMessage;
import OBUSDK.InternalIVIMMessageBuilder;
import OBUSDK.JsonData.IVIM;
import OBUSDK.IVIMMemoryStructures;
import OBUSDK.JsonData.Header;
import OBUSDK.JsonData.IviContainer;
import OBUSDK.JsonData.IviManagementContainer;
import OBUSDK.JsonData.ZoneIds;
import OBUSDK.SafeByteConverter;
import OBUSDK.ZoneAdapter;

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
        List<ZoneIds> detectionZoneIDs;
        List<ZoneIds> awarenessZoneIDs;
        List<ZoneIds> relevanceZoneIDs;
        Integer laneWidth;
        SafeByteConverter byteConverter = new SafeByteConverter();

        InternalIVIMMessageBuilder builder = new InternalIVIMMessageBuilder();
        Header header = extracter.getHeader();

        if (header == null) {
            return null;
        }

        builder.createHeader(header.getProtocolVersion(), header.getMessageID(), header.getStationID());


        IviManagementContainer mandatory = extracter.getMandatoryContainer();

        int countryCode = mandatory.getServiceProvider().getCountryCode();

        String timeStamp = mandatory.getTimeStamp();
        String validFrom = mandatory.getValidFrom();
        String validTo = mandatory.getValidTo();

        builder.createMandatory(countryCode, mandatory.getServiceProvider().getProviderIdentifier(), mandatory.getIviIdentificationNumber(), timeStamp, validFrom, validTo, mandatory.getIviStatus());

        IviContainer givContainer = extracter.getGivContainer();
        IviContainer glcContainer = extracter.getGlcContainer();
        GPSCoordinate refPosition = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());

        refPosition = coordConverter.convertCoordinateInt2Double(refPosition);

        //TODO Confirmar o que fazemos quando no rsu vem vienaConvention ao invés de iso14823
        if (givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823() != null) {
            serviceCategoryCode = transformer.getServiceCategoryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823().getPictogramCode());
            pictogramCategoryCode = transformer.getPictogramCategoryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823().getPictogramCode());
            countryCategoryCode = givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823().getPictogramCode().getCountryCode();
            builder.createSignal(refPosition.getLatitude(), refPosition.getLongitude(), 0, countryCategoryCode, serviceCategoryCode, pictogramCategoryCode, 0);
        } else {
            builder.createSignalNoIso(refPosition.getLatitude(), refPosition.getLongitude(), 0);
        }

        textLanguage = givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getText().getLanguage();
        //A principio n faz falta textLanguage = byteConverter.languageToInt32(givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getLanguage().getA501());
        textContent = givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getText().getTextContent();

        builder.createSignalText(0, textLanguage, textContent);

        detectionZoneIDs = transformer.getDetectionZoneIds(givContainer);

        for (ZoneIds zoneID : detectionZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID.getZid());
                //ZoneAdapter detectionZone = builder.getDetectionZones().addZone();

                /*List<IVIMSegment> segments = zone.getSegments();
                int index = 0;
                int lastIndex = segments.size() - 1;
                for (IVIMSegment segment : segments) {
                    detectionZone.addCoordinate(segment.getOrigin());
                    if (index == lastIndex) {
                        detectionZone.addCoordinate(segment.getDestination());
                    }
                    index++;
                }*/

                builder.addZone(zone, IVIZoneEnum.IVI_ZONE_DETECTION, laneWidth);
            } catch (Exception e) {
                //TODO confirmar se é assim que se trata do erro
                System.out.println("Error: " + e.getMessage());
            }
        }

        relevanceZoneIDs = transformer.getRelevanceZoneIds(givContainer);

        for (ZoneIds zoneID : relevanceZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID.getZid());
                builder.addZone(zone, IVIZoneEnum.IVI_ZONE_RELEVANCE, laneWidth);
            } catch (Exception e) {
                //TODO confirmar se é assim que se trata do erro
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (transformer.hasAwarenessZone(givContainer)) {
            awarenessZoneIDs = transformer.getAwarenessZoneIds(givContainer);
            for (ZoneIds zoneID : awarenessZoneIDs) {
                try {
                    zone = transformer.getZoneById(zoneID);
                    laneWidth = transformer.getZoneLaneWidthById(zoneID.getZid());
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
