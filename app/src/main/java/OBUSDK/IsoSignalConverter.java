package OBUSDK;

public class IsoSignalConverter {

    public int getServiceCategoryCode(ISO14823Code.PictogramCodeType pictogramCode) {
        switch (pictogramCode.ServiceCategoryCode.getSelected()) {
            case TrafficSignPictogramChosen:
                return getTrafficSignPictogramCode(pictogramCode.ServiceCategoryCode.getTrafficSignPictogram());
                //return getTrafficSignPictogramCode(pictogramCode.getServiceCategoryCode().getTrafficSignPictogram().getValue());
            case PublicFacilitiesPictogramChosen:
                return getPublicFacilitiesPictogramCode(pictogramCode.ServiceCategoryCode.getPublicFacilitiesPictogram());
                //return getPublicFacilitiesPictogramCode(pictogramCode.getServiceCategoryCode().getPublicFacilitiesPictogram().getValue());
            case AmbientOrRoadConditionPictogramChosen:
                return getAmbientOrRoadConditionPictogramCode(pictogramCode.ServiceCategoryCode.getAmbientOrRoadConditionPictogram());
                //return getAmbientOrRoadConditionPictogramCode(pictogramCode.getServiceCategoryCode().getAmbientOrRoadConditionPictogram().getValue());
            default:
                return 0;
        }
    }

    private int getTrafficSignPictogramCode(ISO14823Code.PictogramCodeType.ServiceCategoryCodeType.TrafficSignPictogramType type) {
        switch (type) {
            case DangerWarning:
                return 11;
            case Regulatory:
                return 12;
            case Informative:
                return 13;
            default:
                return 0;
        }
    }

    private int getPublicFacilitiesPictogramCode(ISO14823Code.PictogramCodeType.ServiceCategoryCodeType.PublicFacilitiesPictogramType type) {
        switch (type) {
            case PublicFacilities:
                return 21;
            default:
                return 0;
        }
    }

    private int getAmbientOrRoadConditionPictogramCode(ISO14823Code.PictogramCodeType.ServiceCategoryCodeType.AmbientOrRoadConditionPictogramType type) {
        switch (type) {
            case AmbientCondition:
                return 31;
            case RoadCondition:
                return 32;
            default:
                return 0;
        }
    }
}
