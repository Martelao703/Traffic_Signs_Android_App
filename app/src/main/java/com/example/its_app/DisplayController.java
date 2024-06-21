package com.example.its_app;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayController {

    private DisplayManager awarenessZoneDisplays;
    private DisplayManager detectionZoneDisplays;
    private DisplayManager relevanceZoneDisplays;

    private Context context;

    public DisplayController(Context context) {
        this.context = context;
    }

    public void clear() {
        awarenessZoneDisplays.clear();
        detectionZoneDisplays.clear();
        relevanceZoneDisplays.clear();
    }

    public void initDisplay(GridLayout awarenessZone, GridLayout detectionZone, GridLayout relevanceZone) {
        this.awarenessZoneDisplays = new DisplayManager(context);
        this.detectionZoneDisplays = new DisplayManager(context);
        this.relevanceZoneDisplays = new DisplayManager(context);

        // Bind awarenessZone Displays
        for (int i = 0; i < awarenessZone.getChildCount(); i++) {
            if (awarenessZone.getChildAt(i) instanceof ImageView) {
                ImageView display = (ImageView) awarenessZone.getChildAt(i);
                awarenessZoneDisplays.addDisplay(display, null);
            }
        }

        // Bind detectionZone Displays
        for (int i = 0; i < detectionZone.getChildCount(); i++) {
            if (detectionZone.getChildAt(i) instanceof ImageView) {
                ImageView display = (ImageView) detectionZone.getChildAt(i);
                detectionZoneDisplays.addDisplay(display, null);
            }
        }

        // Bind relevanceZone Displays
        for (int i = 0; i < relevanceZone.getChildCount(); i++) {
            if (relevanceZone.getChildAt(i) instanceof ImageView) {
                ImageView display = (ImageView) relevanceZone.getChildAt(i);
                relevanceZoneDisplays.addDisplay(display, null);
            }
        }
    }

    public boolean showAwarenessZoneSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        return awarenessZoneDisplays.showSignal(stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public boolean removeAwarenessZoneSignal(long stationID, long iviIdentificationNumber) {
        return awarenessZoneDisplays.removeSignal(stationID, iviIdentificationNumber);
    }

    public boolean showDetectionZoneSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        return detectionZoneDisplays.showSignal(stationID,  iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public boolean removeDetectionZoneSignal(long stationID, long iviIdentificationNumber) {
        return detectionZoneDisplays.removeSignal(stationID, iviIdentificationNumber);
    }

    public boolean showRelevanceZoneSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        return relevanceZoneDisplays.showSignal(stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public boolean removeRelevanceZoneSignal(long stationID, long iviIdentificationNumber) {
        return relevanceZoneDisplays.removeSignal(stationID, iviIdentificationNumber);
    }
}