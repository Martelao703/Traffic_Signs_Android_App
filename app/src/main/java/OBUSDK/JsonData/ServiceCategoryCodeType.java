package OBUSDK.JsonData;


public class ServiceCategoryCodeType {
    private Integer trafficSignPictogram;
    private Integer publicFacilitiesPictogram;
    private Integer ambientOrRoadConditionPictogram;

    public enum TrafficSignPictogramType {
        DangerWarning,
        Regulatory,
        Informative
    }

    public enum PublicFacilitiesPictogramType {
        PublicFacilities
    }

    public enum AmbientOrRoadConditionPictogramType {
        AmbientCondition,
        RoadCondition
    }

    public Integer getSelected() {
        if (trafficSignPictogram != null) {
            return 0;
        } else if (publicFacilitiesPictogram != null) {
            return 1;
        } else if (ambientOrRoadConditionPictogram != null) {
            return 2;
        } else {
            return null;
        }
    }

    public TrafficSignPictogramType getTrafficSignPictogram() {
        switch (trafficSignPictogram) {
            case 0:
                return TrafficSignPictogramType.Regulatory;
            case 1:
                return TrafficSignPictogramType.DangerWarning;
            case 2:
                return TrafficSignPictogramType.Informative;
            default:
                return null;
        }
    }

    public void setTrafficSignPictogram(int trafficSignPictogram) {
        this.trafficSignPictogram = trafficSignPictogram;
    }

    public PublicFacilitiesPictogramType getPublicFacilitiesPictogram() {
        return (publicFacilitiesPictogram == 0) ? PublicFacilitiesPictogramType.PublicFacilities : null;
    }

    public void setPublicFacilitiesPictogram(int publicFacilitiesPictogram) {
        this.publicFacilitiesPictogram = publicFacilitiesPictogram;
    }

    public AmbientOrRoadConditionPictogramType getAmbientOrRoadConditionPictogram() {
        switch (ambientOrRoadConditionPictogram) {
            case 0:
                return AmbientOrRoadConditionPictogramType.AmbientCondition;
            case 1:
                return AmbientOrRoadConditionPictogramType.RoadCondition;
            default:
                return null;
        }
    }

    public void setAmbientOrRoadConditionPictogram(int ambientOrRoadConditionPictogram) {
        this.ambientOrRoadConditionPictogram = ambientOrRoadConditionPictogram;
    }
}
