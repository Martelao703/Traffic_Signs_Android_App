package OBUSDK.JsonData;

public class ISO14823Code {
    private PictogramCodeType pictogramCode;
    private String attributes;
    private String itisCodes;

    public PictogramCodeType getPictogramCode() {
        return pictogramCode;
    }

    public void setPictogramCode(PictogramCodeType pictogramCode) {
        this.pictogramCode = pictogramCode;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getItisCodes() {
        return itisCodes;
    }

    public void setItisCodes(String itisCodes) {
        this.itisCodes = itisCodes;
    }
}
