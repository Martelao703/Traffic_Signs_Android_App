package OBUSDK.JsonController;

<<<<<<< Updated upstream
=======
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
>>>>>>> Stashed changes
import java.util.Date;

import OBUSDK.CoordinateConverter;
import OBUSDK.GPSCoordinate;
import OBUSDK.IControllerAdapter;
import OBUSDK.IVIZone;
import OBUSDK.IVIZoneEnum;
import OBUSDK.InternalIVIMMessage;
import OBUSDK.InternalIVIMMessageBuilder;
import OBUSDK.PerEncDec.IVIM;
import OBUSDK.IVIMMemoryStructures;
<<<<<<< Updated upstream
import OBUSDK.PerEncDec.Header;
import OBUSDK.PerEncDec.IviContainer;
import OBUSDK.PerEncDec.IviManagementContainer;
=======
import OBUSDK.JsonData.Header;
import OBUSDK.JsonData.IviContainer;
import OBUSDK.JsonData.IviManagementContainer;
import OBUSDK.JsonData.ZoneIds;
>>>>>>> Stashed changes
import OBUSDK.SafeByteConverter;

//public class JsonAdapter implements IControllerAdapter {
    /*private IVIM rootIVI;

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
        ArrayList<ZoneIds> detectionZoneIDs;
        ArrayList<ZoneIds> awarenessZoneIDs;
        ArrayList<ZoneIds> relevanceZoneIDs;
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

<<<<<<< Updated upstream
        int countryCode = byteConverter.countryCodeToInt32(mandatory.getServiceProviderId().getCountryCode().getA501());
=======
        int countryCode = mandatory.getServiceProvider().getCountryCode();
>>>>>>> Stashed changes

        Date timeStamp = byteConverter.toIVIDateTime((long) mandatory.getTimeStamp());
        Date validFrom = byteConverter.toIVIDateTime((long) mandatory.getValidFrom());
        Date validTo = byteConverter.toIVIDateTime((long) mandatory.getValidTo());

        builder.createMandatory(countryCode, mandatory.getServiceProviderId().getProviderIdentifier(), mandatory.getIviIdentificationNumber(), timeStamp, validFrom, validTo, 0, mandatory.getIviStatus());

        //TODO verificar se é assim
        IviContainer givContainer = extracter.GetGivContainer();
        IviContainer glcContainer = extracter.GetGlcContainer();
        GPSCoordinate refPosition = new GPSCoordinate(glcContainer.getGlc().getReferencePosition().getLatitude(), glcContainer.getGlc().getReferencePosition().getLongitude());

        refPosition = coordConverter.convertCoordinateInt2Double(refPosition);

        serviceCategoryCode = transformer.getServiceCategoryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823().getPictogramCode());
        pictogramCategoryCode = transformer.getPictogramCategoryCode(givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823().getPictogramCode());
        countryCategoryCode = givContainer.getGiv().get(0).getGicPart().getRoadSignCodes().get(0).getRsCode().getCode().getIso14823().getPictogramCode().getCountryCode();

        builder.createSignal(refPosition.getLatitude(), refPosition.getLongitude(), 0, countryCategoryCode, serviceCategoryCode, pictogramCategoryCode, 0);

        textLanguage = givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getText().getLanguage();
        //A principio n faz falta textLanguage = byteConverter.languageToInt32(givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getLanguage().getA501());
        textContent = givContainer.getGiv().get(0).getGicPart().getExtraText().get(0).getText().getTextContent();

        builder.createSignalText(0, textLanguage, textContent);

        detectionZoneIDs = transformer.getDetectionZoneIds(givContainer);
        for (ZoneIds zoneID : detectionZoneIDs) {
            try {
                zone = transformer.getZoneById(zoneID);
                laneWidth = transformer.getZoneLaneWidthById(zoneID.getZid());
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
    */
//}
