package OBUSDK.JsonData;

public class CodeType {
    private ISO14823Code iso14823;
    private ViennaConventionCode viennaConvention;
    private String itisCode;

    public ISO14823Code getIso14823() {
        return iso14823;
    }

    public void setIso14823(ISO14823Code iso14823) {
        this.iso14823 = iso14823;
    }

    public ViennaConventionCode getViennaConvention() {
        return viennaConvention;
    }

    public void setViennaConvention(ViennaConventionCode viennaConvention) {
        this.viennaConvention = viennaConvention;
    }

    public String getItisCode() {
        return itisCode;
    }

    public void setItisCode(String itisCode) {
        this.itisCode = itisCode;
    }
}