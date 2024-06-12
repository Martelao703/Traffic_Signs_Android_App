package com.example.its_app;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DisplayController {
    private DisplayManager awarenessZoneDisplays;
    private DisplayManager detectionZoneDisplays;
    private DisplayManager relevanceZoneDisplays;

    private ViewGroup awarenessZoneGroupBox;
    private ViewGroup detectionZoneGroupBox;
    private ViewGroup relevanceZoneGroupBox;

    public void clear() {
        this.awarenessZoneDisplays.clear();
        this.detectionZoneDisplays.clear();
        this.relevanceZoneDisplays.clear();
    }

    public DisplayController() {
    }

    public void initDisplay(ViewGroup awarenessZone, ViewGroup detectionZone, ViewGroup relevanceZone, ImagelistIndexer imagelistIndexer) {
        this.awarenessZoneDisplays = new DisplayManager(imagelistIndexer);
        this.detectionZoneDisplays = new DisplayManager(imagelistIndexer);
        this.relevanceZoneDisplays = new DisplayManager(imagelistIndexer);

        this.awarenessZoneGroupBox = awarenessZone;
        this.detectionZoneGroupBox = detectionZone;
        this.relevanceZoneGroupBox = relevanceZone;

        List<View> displayControls = new ArrayList<>();
        List<View> textDisplayControls = new ArrayList<>();

        // Bind awarenessZones Displays
        for (int i = 0; i < awarenessZone.getChildCount(); i++) {
            View view = awarenessZone.getChildAt(i);
            if (view instanceof ImageView) {
                awarenessZoneDisplays.addDisplay((ImageView) view, null);
            }
        }

        // Bind detectionZones Displays
        for (int i = 0; i < detectionZone.getChildCount(); i++) {
            View view = detectionZone.getChildAt(i);
            if (view instanceof ImageView) {
                displayControls.add(view);
            } else if (view instanceof TextView) {
                textDisplayControls.add(view);
            }
        }

        if (displayControls.size() != textDisplayControls.size()) {
            throw new RuntimeException("Detection zone displays: The amount of displays is different from the amount of text displays.");
        }

        for (int i = 0; i < displayControls.size(); i++) {
            detectionZoneDisplays.addDisplay((ImageView) displayControls.get(i), (TextView) textDisplayControls.get(i));
        }

        displayControls.clear();
        textDisplayControls.clear();

        // Bind relevanceZones Displays
        for (int i = 0; i < relevanceZone.getChildCount(); i++) {
            View view = relevanceZone.getChildAt(i);
            if (view instanceof ImageView) {
                displayControls.add(view);
            } else if (view instanceof TextView) {
                textDisplayControls.add(view);
            }
        }

        if (displayControls.size() != textDisplayControls.size()) {
            throw new RuntimeException("Relevance zone displays: The amount of displays is different from the amount of text displays.");
        }

        for (int i = 0; i < displayControls.size(); i++) {
            relevanceZoneDisplays.addDisplay((ImageView) displayControls.get(i), (TextView) textDisplayControls.get(i));
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
        return this.detectionZoneDisplays.showSignal(stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
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