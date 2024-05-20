package OBUSDK.PerEncDec;

public class CodeType {
    private ISO14823Code iso14823;
    private ViennaConventionCode viennaConvention;
    private Object itisCode;

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

    public Object getItisCode() {
        return itisCode;
    }

    public void setItisCode(Object itisCode) {
        this.itisCode = itisCode;
    }
}