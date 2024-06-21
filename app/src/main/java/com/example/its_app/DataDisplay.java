package com.example.its_app;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.TextView;

public class DataDisplay {
    private ImageView display;
    private TextView textDisplay;

    // IVI Signal identifier
    private long stationID;
    private long iviIdentificationNumber;

    // IVI Signal data
    private long signalCountryCode;
    private long serviceCategoryCode;
    private long pictogramCategoryCode;
    private long language;
    private String textContent;

    public DataDisplay(ImageView display) {
        this.display = display;
        this.textDisplay = null;
        this.clearData();
    }

    public boolean hasId(long stationID, long iviIdentificationNumber) {
        return this.stationID == stationID && this.iviIdentificationNumber == iviIdentificationNumber;
    }

    public void attachTextDisplay(TextView textDisplay) {
        this.textDisplay = textDisplay;
    }

    private void setImage(Drawable sourceImage) {
        display.setImageDrawable(sourceImage);
    }

    private void setText(String textContent) {
        textDisplay.setText(textContent);
    }

    public void setData(Drawable sourceImage, long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        setImage(sourceImage);

        if (textDisplay != null) {
            setText(textContent);
        }

        this.stationID = stationID;
        this.iviIdentificationNumber = iviIdentificationNumber;
        this.signalCountryCode = signalCountryCode;
        this.serviceCategoryCode = serviceCategoryCode;
        this.pictogramCategoryCode = pictogramCategoryCode;
        this.language = language;
    }

    public void clearData() {
        setImage(null);

        if (textDisplay != null) {
            setText("");
        }

        this.stationID = -1;
        this.iviIdentificationNumber = -1;
        this.signalCountryCode = -1;
        this.serviceCategoryCode = -1;
        this.pictogramCategoryCode = -1;
        this.language = -1;
    }

    public void copyDataTo(DataDisplay dataDisplay) {
        dataDisplay.setData(display.getDrawable(), stationID, iviIdentificationNumber, signalCountryCode, serviceCategoryCode, pictogramCategoryCode, language, textContent);
    }

    public void copyDataFrom(DataDisplay dataDisplay) {
        if (dataDisplay != null) {
            setImage(dataDisplay.display.getDrawable());

            if (textDisplay != null) {
                if (dataDisplay.textDisplay != null) {
                    setText(dataDisplay.textDisplay.getText().toString());
                } else {
                    setText("");
                }
            }

            this.stationID = dataDisplay.stationID;
            this.iviIdentificationNumber = dataDisplay.iviIdentificationNumber;
            this.signalCountryCode = dataDisplay.signalCountryCode;
            this.serviceCategoryCode = dataDisplay.serviceCategoryCode;
            this.pictogramCategoryCode = dataDisplay.pictogramCategoryCode;
            this.language = dataDisplay.language;
        } else {
            clearData();
        }
    }
}


