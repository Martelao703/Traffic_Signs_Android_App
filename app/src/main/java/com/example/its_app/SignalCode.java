package com.example.its_app;

public class SignalCode {

    private int signalCountryCode;
    private int serviceCategoryCode;
    private int pictogramCategoryCode;

    public SignalCode(int signalCountryCode, int serviceCategoryCode, int pictogramCategoryCode) {
        this.signalCountryCode = signalCountryCode;
        this.serviceCategoryCode = serviceCategoryCode;
        this.pictogramCategoryCode = pictogramCategoryCode;
    }

    public int getSignalCountryCode() {
        return signalCountryCode;
    }

    public int getServiceCategoryCode() {
        return serviceCategoryCode;
    }

    public int getPictogramCategoryCode() {
        return pictogramCategoryCode;
    }

}
