package com.example.its_app;


import android.graphics.drawable.Drawable;
import java.util.List;

public class ImagelistIndexer {

    private List<Drawable> signalImageList;
    private List<SignalCode> signalCodes;

    public ImagelistIndexer(List<Drawable> signalImageList, List<SignalCode> signalCodes) {
        this.signalImageList = signalImageList;
        this.signalCodes = signalCodes;
    }

    public int getIndexByCode(long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode) {
        for (int i = 0; i < signalCodes.size(); i++) {
            SignalCode signalCode = signalCodes.get(i);
            // if (signalCountryCode == signalCode.getSignalCountryCode() && serviceCategoryCode == signalCode.getServiceCategoryCode() && pictogramCategoryCode == signalCode.getPictogramCategoryCode())
            if (serviceCategoryCode == signalCode.getServiceCategoryCode() && pictogramCategoryCode == signalCode.getPictogramCategoryCode()) {
                return i;
            }
        }

        // Returns index zero if signal image not found.
        return 0;
    }

    public Drawable getImageByIndex(int index) {
        return this.signalImageList.get(index);
    }
}