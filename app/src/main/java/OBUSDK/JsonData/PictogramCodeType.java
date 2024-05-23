package OBUSDK.JsonData;

public class PictogramCodeType {
    private int countryCode;
    private ServiceCategoryCodeType serviceCategoryCode;
    private PictogramCategoryCodeType pictogramCategoryCode;

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
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
