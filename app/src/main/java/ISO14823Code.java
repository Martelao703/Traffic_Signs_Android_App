import java.io.Serializable;

public class ISO14823Code {
    public static class PictogramCodeType {
        public static class ServiceCategoryCodeType {
            public enum Id {
                Unselected,
                TrafficSignPictogramChosen,
                PublicFacilitiesPictogramChosen,
                AmbientOrRoadConditionPictogramChosen
            }

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

            private Id _id;
            private Object _contained;

            public Id getSelected() {
                return _id;
            }

            public TrafficSignPictogramType getTrafficSignPictogram() {
                return (_id == Id.TrafficSignPictogramChosen) ? ((TrafficSignPictogramType) _contained) : null;
            }

            public void setTrafficSignPictogram(TrafficSignPictogramType value) {
                _contained = value;
                _id = (value != null) ? Id.TrafficSignPictogramChosen : Id.Unselected;
            }

            public PublicFacilitiesPictogramType getPublicFacilitiesPictogram() {
                return (_id == Id.PublicFacilitiesPictogramChosen) ? ((PublicFacilitiesPictogramType) _contained) : null;
            }

            public void setPublicFacilitiesPictogram(PublicFacilitiesPictogramType value) {
                _contained = value;
                _id = (value != null) ? Id.PublicFacilitiesPictogramChosen : Id.Unselected;
            }

            public AmbientOrRoadConditionPictogramType getAmbientOrRoadConditionPictogram() {
                return (_id == Id.AmbientOrRoadConditionPictogramChosen) ? ((AmbientOrRoadConditionPictogramType) _contained) : null;
            }

            public void setAmbientOrRoadConditionPictogram(AmbientOrRoadConditionPictogramType value) {
                _contained = value;
                _id = (value != null) ? Id.AmbientOrRoadConditionPictogramChosen : Id.Unselected;
            }
        }

        public class PictogramCategoryCodeType implements Serializable {
            public int Nature;
            public int SerialNumber;

            @Override
            public boolean equals(Object right) {
                if (right == null) {
                    return false;
                }

                if (this == right) {
                    return true;
                }

                if (getClass() != right.getClass()) {
                    return false;
                }

                PictogramCategoryCodeType pictogramCategoryCodeType = (PictogramCategoryCodeType) right;
                return Nature == pictogramCategoryCodeType.Nature && SerialNumber == pictogramCategoryCodeType.SerialNumber;
            }
        }

        public byte[] CountryCode;
        public ServiceCategoryCodeType ServiceCategoryCode;
        public PictogramCategoryCodeType PictogramCategoryCode;
    }

    public PictogramCodeType PictogramCode;
    //private ISO14823Attributes Attributes;
}
