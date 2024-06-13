package com.example.its_app;

import java.util.List;

public class StoredSignals {
    private String name;
    private SignalCode signalCode;

    public StoredSignals(String name, int signalCountryCode, int serviceCategoryCode, int pictogramCategoryCode) {
        this.name = name;
        this.signalCode = new SignalCode(signalCountryCode, serviceCategoryCode, pictogramCategoryCode);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SignalCode getSignalCode() {
        return signalCode;
    }

    public void setSignalCode(SignalCode signalCode) {
        this.signalCode = signalCode;
    }

    public boolean doCodesEqual(int serviceCategoryCode, int pictogramCategoryCode) {
        return this.signalCode.getServiceCategoryCode() == serviceCategoryCode && this.signalCode.getPictogramCategoryCode() == pictogramCategoryCode;
    }
}
