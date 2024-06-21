package com.example.its_app;

import android.graphics.drawable.Drawable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayManager {
    private List<DataDisplay>  dataDisplays;
    private GridLayout displayGrid;
    private List<ImageView> imageList;

    public DisplayManager(GridLayout displayGrid, List<ImageView> imageList) {
        this.displayGrid = displayGrid;
        this.imageList = imageList;
    }

    public void addDisplay(ImageView imageView, TextView textView) {
        String key = generateKey(imageView);
        imageDisplays.put(key, imageView);
        if (textView != null) {
            textDisplays.put(key, textView);
        }
    }

    public boolean showSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        DataDisplay dataDisplay = new DataDisplay(imageList);
    }

    public boolean removeSignal(long stationID, long iviIdentificationNumber) {
        String key = generateKey(stationID, iviIdentificationNumber);
        ImageView imageView = imageDisplays.remove(key);
        TextView textView = textDisplays.remove(key);

        if (imageView != null) {
            imageView.setImageResource(0); // Clear the image
        }

        if (textView != null) {
            textView.setText(""); // Clear the text
        }

        return imageView != null;
    }

    public void clear() {
        for (ImageView imageView : imageDisplays.values()) {
            imageView.setImageResource(0); // Clear the image
        }
        for (TextView textView : textDisplays.values()) {
            textView.setText(""); // Clear the text
        }
        imageDisplays.clear();
        textDisplays.clear();
    }

    private String generateKey(long stationID, long iviIdentificationNumber) {
        return stationID + "-" + iviIdentificationNumber;
    }

    private String generateKey(ImageView imageView) {
        // Generate a unique key based on the view's position or identifier
        return String.valueOf(imageView.hashCode());
    }
}


