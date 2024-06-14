package com.example.its_app;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisplayManager {
    private ImagelistIndexer imageListIndexer;
    private Map<String, ImageView> imageDisplays;
    private Map<String, TextView> textDisplays;

    public DisplayManager(ImagelistIndexer imageListIndexer) {
        this.imageListIndexer = imageListIndexer;
        this.imageDisplays = new HashMap<>();
        this.textDisplays = new HashMap<>();
    }

    public void addDisplay(ImageView imageView, TextView textView) {
        String key = generateKey(imageView);
        imageDisplays.put(key, imageView);
        if (textView != null) {
            textDisplays.put(key, textView);
        }
    }

    public boolean showSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        String key = generateKey(stationID, iviIdentificationNumber);
        ImageView imageView = imageDisplays.get(key);
        TextView textView = textDisplays.get(key);

        if (imageView != null) {
            // Use ImageListIndexer to get drawable ID
            int drawableId = imageListIndexer.getDrawableId(pictogramCategoryCode);
            imageView.setImageResource(drawableId);
        }

        if (textView != null) {
            textView.setText(textContent);
        }

        return imageView != null;
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


