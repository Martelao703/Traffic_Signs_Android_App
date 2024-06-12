package com.example.its_app;

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

    public boolean hasId(long stationID, long iviIdentificationNumber) {
        return this.stationID == stationID && this.iviIdentificationNumber == iviIdentificationNumber;
    }

    public DataDisplay(ImageView display) {
        this.display = display;
        this.textDisplay = null;
        this.clearData();
    }

    public void attachTextDisplay(TextView textDisplay) {
        this.textDisplay = textDisplay;
    }

    private void setImageThreadSafe(final android.graphics.drawable.Drawable sourceImage) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            display.setImageDrawable(sourceImage);
        } else {
            new Handler(Looper.getMainLooper()).post(() -> display.setImageDrawable(sourceImage));
        }
    }

    private void setTextThreadSafe(final String textContent) {
        if (textDisplay != null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                textDisplay.setText(textContent);
            } else {
                new Handler(Looper.getMainLooper()).post(() -> textDisplay.setText(textContent));
            }
        }
    }

    public void setData(android.graphics.drawable.Drawable sourceImage, long stationID, long iviIdentificationNumber, long signalCountryCode, long serviceCategoryCode, long pictogramCategoryCode, long language, String textContent) {
        setImageThreadSafe(sourceImage);

        if (this.textDisplay != null) {
            setTextThreadSafe(textContent);
        }

        this.stationID = stationID;
        this.iviIdentificationNumber = iviIdentificationNumber;
        this.signalCountryCode = signalCountryCode;
        this.serviceCategoryCode = serviceCategoryCode;
        this.pictogramCategoryCode = pictogramCategoryCode;
        this.language = language;
    }

    public void clearData() {
        setImageThreadSafe(null);

        if (this.textDisplay != null) {
            setTextThreadSafe("");
        }

        this.stationID = -1;
        this.iviIdentificationNumber = -1;
        this.signalCountryCode = -1;
        this.serviceCategoryCode = -1;
        this.pictogramCategoryCode = -1;
        this.language = -1;
    }

    public void copyDataTo(DataDisplay dataDisplay) {
        dataDisplay.setData(this.display.getDrawable(), this.stationID, this.iviIdentificationNumber, this.signalCountryCode, this.serviceCategoryCode, this.pictogramCategoryCode, this.language, this.textContent);
    }

    public void copyDataFrom(DataDisplay dataDisplay) {
        if (dataDisplay != null) {
            setImageThreadSafe(dataDisplay.display.getDrawable());

            if (this.textDisplay != null) {
                if (dataDisplay.textDisplay != null) {
                    setTextThreadSafe(dataDisplay.textDisplay.getText().toString());
                } else {
                    setTextThreadSafe("");
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


