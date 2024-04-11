package OBUSDK;

public class ISO14823 {

    private int countryCode;
    private int serviceCategoryCode;
    private int pictogramCategoryCode;
    private int layoutComponentId;

    public ISO14823(int countryCode, int serviceCategoryCode, int pictogramCategoryCode, int layoutComponentId) {
        this.countryCode = countryCode;
        this.serviceCategoryCode = serviceCategoryCode;
        this.pictogramCategoryCode = pictogramCategoryCode;
        this.layoutComponentId = layoutComponentId;
    }

    public ISO14823() {
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public int getServiceCategoryCode() {
        return serviceCategoryCode;
    }

    public void setServiceCategoryCode(int serviceCategoryCode) {
        this.serviceCategoryCode = serviceCategoryCode;
    }

    public int getPictogramCategoryCode() {
        return pictogramCategoryCode;
    }

    public void setPictogramCategoryCode(int pictogramCategoryCode) {
        this.pictogramCategoryCode = pictogramCategoryCode;
    }

    public int getLayoutComponentId() {
        return layoutComponentId;
    }

    public void setLayoutComponentId(int layoutComponentId) {
        this.layoutComponentId = layoutComponentId;
    }
}
