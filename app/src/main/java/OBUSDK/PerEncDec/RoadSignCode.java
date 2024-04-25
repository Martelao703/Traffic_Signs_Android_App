package OBUSDK.PerEncDec;

import OBUSDK.ISO14823Code;

public class RoadSignCode {
    private Long layoutComponentId;
    private CodeType code;

    public Long getLayoutComponentId() {
        return layoutComponentId;
    }

    public void setLayoutComponentId(Long layoutComponentId) {
        this.layoutComponentId = layoutComponentId;
    }

    public CodeType getCode() {
        return code;
    }

    public void setCode(CodeType code) {
        this.code = code;
    }

    public static class CodeType {
        public enum Id {
            Unselected,
            ViennaConventionChosen,
            Iso14823Chosen,
            ItisCodesChosen,
            AnyCatalogueChosen
        }

        private Id id;
        private Object contained;

        public Id getSelected() {
            return id;
        }

        public void setSelected(Id id) {
            this.id = id;
        }

        public ISO14823Code getIso14823() {
            return (id == Id.Iso14823Chosen) ? (ISO14823Code) contained : null;
        }

        public void setIso14823(ISO14823Code iso14823) {
            this.contained = iso14823;
            this.id = Id.Iso14823Chosen;
        }
    }
}
