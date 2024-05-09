package OBUSDK.PerEncDec;

public class PictogramCodeType {
    private byte[] countryCode;
    private ServiceCategoryCodeType serviceCategoryCode;
    private PictogramCategoryCodeType pictogramCategoryCode;

    public byte[] getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(byte[] countryCode) {
        this.countryCode = countryCode;
    }

    public ServiceCategoryCodeType getServiceCategoryCode() {
        return serviceCategoryCode;
    }

    public void setServiceCategoryCode(ServiceCategoryCodeType serviceCategoryCode) {
        this.serviceCategoryCode = serviceCategoryCode;
    }

    public PictogramCategoryCodeType getPictogramCategoryCode() {
        return pictogramCategoryCode;
    }

    public void setPictogramCategoryCode(PictogramCategoryCodeType pictogramCategoryCode) {
        this.pictogramCategoryCode = pictogramCategoryCode;
    }
}
