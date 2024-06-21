package com.example.its_app;

import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayController {

    private DisplayManager awarenessZoneDisplays;
    private DisplayManager detectionZoneDisplays;
    private DisplayManager relevanceZoneDisplays;

    private GridLayout awarenessZoneGrid;
    private GridLayout detectionZoneGrid;
    private GridLayout relevanceZoneGrid;

    public DisplayController() {
        // Default constructor
    }

    public void clear() {
        awarenessZoneDisplays.clear();
        detectionZoneDisplays.clear();
        relevanceZoneDisplays.clear();
    }

    public void initDisplay(GridLayout awarenessZone, GridLayout detectionZone, GridLayout relevanceZone, ImageListManager imageListIndexer) {
        this.awarenessZoneDisplays = new DisplayManager(imageListIndexer);
        this.detectionZoneDisplays = new DisplayManager(imageListIndexer);
        this.relevanceZoneDisplays = new DisplayManager(imageListIndexer);

        this.awarenessZoneGrid = awarenessZone;
        this.detectionZoneGrid = detectionZone;
        this.relevanceZoneGrid = relevanceZone;

        List<ImageView> displayControls = new ArrayList<>();
        List<TextView> textDisplayControls = new ArrayList<>();

        for (ImageView display : awarenessZoneDisplays.getDisplays()) {
            awarenessZoneGrid.addView(display);
        }

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
                displayControls.add((ImageView) detectionZone.getChildAt(i));
            } else if (detectionZone.getChildAt(i) instanceof TextView) {
                textDisplayControls.add((TextView) detectionZone.getChildAt(i));
            }
        }

        if (displayControls.size() != textDisplayControls.size()) {
            throw new IllegalArgumentException("Detection zone displays: The amount of displays is different from the amount of text displays.");
        }

        for (int i = 0; i < displayControls.size(); i++) {
            ImageView control = displayControls.get(i);
            TextView textDisplay = textDisplayControls.get(i);
            detectionZoneDisplays.addDisplay(control, textDisplay);
        }

        displayControls.clear();
        textDisplayControls.clear();

        // Bind relevanceZone Displays
        for (int i = 0; i < relevanceZone.getChildCount(); i++) {
            if (relevanceZone.getChildAt(i) instanceof ImageView) {
                displayControls.add((ImageView) relevanceZone.getChildAt(i));
            } else if (relevanceZone.getChildAt(i) instanceof TextView) {
                textDisplayControls.add((TextView) relevanceZone.getChildAt(i));
            }
        }

        if (displayControls.size() != textDisplayControls.size()) {
            throw new IllegalArgumentException("Relevance zone displays: The amount of displays is different from the amount of text displays.");
        }

        for (int i = 0; i < displayControls.size(); i++) {
            ImageView control = displayControls.get(i);
            TextView textDisplay = textDisplayControls.get(i);
            relevanceZoneDisplays.addDisplay(control, textDisplay);
        }

        displayControls.clear();
        textDisplayControls.clear();
    }

    public boolean showAwarenessZoneSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        return this.awarenessZoneDisplays.showSignal(stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public boolean removeAwarenessZoneSignal(long stationID, long iviIdentificationNumber) {
        return this.awarenessZoneDisplays.removeSignal(stationID, iviIdentificationNumber);
    }

    public boolean showDetectionZoneSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        return this.detectionZoneDisplays.showSignal(stationID,  iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public boolean removeDetectionZoneSignal(long stationID, long iviIdentificationNumber) {
        return this.detectionZoneDisplays.removeSignal(stationID, iviIdentificationNumber);
    }

    public boolean showRelevanceZoneSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        return this.relevanceZoneDisplays.showSignal(stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public boolean removeRelevanceZoneSignal(long stationID, long iviIdentificationNumber) {
        return this.relevanceZoneDisplays.removeSignal(stationID, iviIdentificationNumber);
    }
}
