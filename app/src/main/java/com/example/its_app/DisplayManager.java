package com.example.its_app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OBUSDK.JsonData.Data;

public class DisplayManager {
    private List<DataDisplay>  dataDisplays;
    private int currentDisplayIndex;
    private Context context;

    public DisplayManager(Context context) {
        this.dataDisplays = new ArrayList<>();
        this.currentDisplayIndex = 0;
        this.context = context;
    }

    public List<DataDisplay> getDataDisplays() {
        return dataDisplays;
    }

    public void addDisplay(ImageView display, TextView textDisplay) {
        DataDisplay dataDisplay = new DataDisplay(display);
        if (textDisplay != null) {
            dataDisplay.attachTextDisplay(textDisplay);
        }
        dataDisplays.add(dataDisplay);
    }

    public boolean showSignal(long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        if (currentDisplayIndex >= dataDisplays.size()) {
            return false;
        }
        DataDisplay dataDisplay = dataDisplays.get(currentDisplayIndex);
        currentDisplayIndex++;
        ImageListManager imageListManager = ImageListManager.getInstance();

        int drawableId = imageListManager.getDrawableId(pictogramCategoryCode);
        Drawable sourceImage = ContextCompat.getDrawable(context, drawableId);
        dataDisplay.setData(sourceImage, stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);

        return true;
    }

    public boolean removeSignal(long stationID, long iviIdentificationNumber) {
        int dataDisplayIndex = -1;
        boolean signalFound = false;

        for (DataDisplay dataDisplay : dataDisplays) {
            if (dataDisplay.hasId(stationID, iviIdentificationNumber)) {
                dataDisplayIndex = dataDisplays.indexOf(dataDisplay);
                signalFound = true;
                currentDisplayIndex--;
                break;
            }
        }

        if (signalFound) {
            dataDisplays.get(dataDisplayIndex).clearData();

            for (int i = dataDisplayIndex; i < dataDisplays.size() - 1; i++) {
                if (i + 1 <= dataDisplays.size() - 1) {
                    dataDisplays.get(i).copyDataFrom(dataDisplays.get(i + 1));
                }
                else {
                    dataDisplays.get(i).clearData();
                }
            }
        }

        return signalFound;
    }

    public void clear() {
        for (DataDisplay dataDisplay : dataDisplays) {
            dataDisplay.clearData();
        }
        currentDisplayIndex = 0;
    }
}


