package OBUSDK.PerEncDec;

public class CodeType {
    public enum Id {
        Unselected,
        ViennaConventionChosen,
        Iso14823Chosen,
        ItisCodesChosen,
        AnyCatalogueChosen
    }

    private Id id;
    private Object contained;
    private String itisCodes;

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

    public ViennaConventionCode getViennaConvention() {
        return (id == Id.ViennaConventionChosen) ? (ViennaConventionCode) contained : null;
    }

    public void setViennaConvention(ViennaConventionCode viennaConvention) {
        this.contained = viennaConvention;
        this.id = Id.ViennaConventionChosen;
    }

    public String getItisCodes() {
        return itisCodes;
    }

    public void setItisCodes(String itisCodes) {
        this.itisCodes = itisCodes;
    }
}