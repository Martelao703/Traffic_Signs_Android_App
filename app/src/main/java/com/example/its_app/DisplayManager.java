package com.example.its_app;

import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
    private List<DataDisplay> displays;
    private int currentDisplayIndex;
    private ImagelistIndexer imagelistIndexer;

    public DisplayManager(ImagelistIndexer imagelistIndexer) {
        this.displays = new ArrayList<>();
        this.currentDisplayIndex = 0;
        this.imagelistIndexer = imagelistIndexer;
    }

    public void addDisplay(ImageView display, TextView textDisplay) {
        DataDisplay dataDisplay = new DataDisplay(display);
        if (textDisplay != null) {
            dataDisplay.attachTextDisplay(textDisplay);
        }
        this.displays.add(dataDisplay);
    }

    public boolean showSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        if (currentDisplayIndex >= this.displays.size()) {
            return false;
        } else {
            DataDisplay dataDisplay = this.displays.get(this.currentDisplayIndex);
            this.currentDisplayIndex++;
            int imageIndex = this.imagelistIndexer.getIndexByCode(signalCountryCode, serviceCategoryCode, pictogramCategoryCode);

            android.graphics.drawable.Drawable sourceImage = this.imagelistIndexer.getImageByIndex(imageIndex);
            dataDisplay.setData(sourceImage, stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);

            return true;
        }
    }

    public void clear() {
        for (DataDisplay display : this.displays) {
            display.clearData();
        }
        this.currentDisplayIndex = 0;
    }

    public boolean removeSignal(long stationID, long iviIdentificationNumber) {
        int dataDisplayIndex = -1;
        boolean signalFound = false;

        for (DataDisplay dataDisplay : this.displays) {
            if (dataDisplay.hasId(stationID, iviIdentificationNumber)) {
                dataDisplayIndex = this.displays.indexOf(dataDisplay);
                signalFound = true;
                this.currentDisplayIndex--;
                break;
            }
        }

        if (dataDisplayIndex > -1) {
            this.displays.get(dataDisplayIndex).clearData();

            for (int i = dataDisplayIndex; i < this.displays.size() - 1; i++) {
                if (i + 1 <= this.displays.size() - 1) {
                    this.displays.get(i).copyDataFrom(this.displays.get(i + 1));
                } else {
                    this.displays.get(i).clearData();
                }
            }
        }

        return signalFound;
    }
}


