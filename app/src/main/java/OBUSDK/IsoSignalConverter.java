package OBUSDK;

import OBUSDK.PerEncDec.*;

public class IsoSignalConverter {
    public int getServiceCategoryCode(PictogramCodeType pictogramCode) {
        switch (pictogramCode.getServiceCategoryCode().getSelected()) {
            case TrafficSignPictogramChosen:
                return getTrafficSignPictogramCode(pictogramCode.getServiceCategoryCode().getTrafficSignPictogram());
                //return getTrafficSignPictogramCode(pictogramCode.getServiceCategoryCode().getTrafficSignPictogram().getValue());
            case PublicFacilitiesPictogramChosen:
                return getPublicFacilitiesPictogramCode(pictogramCode.getServiceCategoryCode().getPublicFacilitiesPictogram());
                //return getPublicFacilitiesPictogramCode(pictogramCode.getServiceCategoryCode().getPublicFacilitiesPictogram().getValue());
            case AmbientOrRoadConditionPictogramChosen:
                return getAmbientOrRoadConditionPictogramCode(pictogramCode.getServiceCategoryCode().getAmbientOrRoadConditionPictogram());
                //return getAmbientOrRoadConditionPictogramCode(pictogramCode.getServiceCategoryCode().getAmbientOrRoadConditionPictogram().getValue());
            default:
                return 0;
        }
    }

    private int getTrafficSignPictogramCode(ServiceCategoryCodeType.TrafficSignPictogramType type) {
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

    private int getPublicFacilitiesPictogramCode(ServiceCategoryCodeType.PublicFacilitiesPictogramType type) {
        switch (type) {
            case PublicFacilities:
                return 21;
            default:
                return 0;
        }
    }

    private int getAmbientOrRoadConditionPictogramCode(ServiceCategoryCodeType.AmbientOrRoadConditionPictogramType type) {
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
