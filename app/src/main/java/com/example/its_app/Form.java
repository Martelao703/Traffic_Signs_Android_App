/*
package com.example.its_app;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import OBUSDK.*;

public class MainActivity extends AppCompatActivity {

    private IVIMEngine ivimEngine;
    private GPSController gpsController;
    private WayPointIterator wayPointIterator;
    private List<SignalCode> signalCodes;
    private ImagelistIndexer imagelistIndexer;
    private DisplayController displayController;

    private long currentHammerId;

    private TextView toolStripTextBox1;
    private TextView toolStripTextBox2;
    private TextView toolStripTextBox3;
    private TextView toolStripLabel4;
    private Button toolStripButton9;

    private long layoutId = 1;
    private long signalCountryCode = 620;
    private long serviceCategoryCode = 12;
    private long pictogramCategoryCode = 117;
    private long layoutComponentId = 1;

    // Create Signal Text
    private long textLayoutComponentId = 2;
    private long language = 10;
    private String textContent = "Dar prioridade";

    // GPS Coordinates
    private GPSCoordinate refPosition = new GPSCoordinate(39734130, -8821880);
    private GPSCoordinate detectionZonePoint1 = new GPSCoordinate(39734130, -8821880);
    private GPSCoordinate detectionZonePoint2 = new GPSCoordinate(39734240, -8821700);
    private GPSCoordinate detectionZonePoint3 = new GPSCoordinate(39734360, -8821520);

    // Awareness Zone
    private GPSCoordinate awarenessZonePoint1 = new GPSCoordinate(39734360, -8821520);
    private GPSCoordinate awarenessZonePoint2 = new GPSCoordinate(39734470, -8821340);
    private GPSCoordinate awarenessZonePoint3 = new GPSCoordinate(39734610, -8821120);

    // Relevance Zone
    private GPSCoordinate relevanceZonePoint1 = new GPSCoordinate(39734130, -8821880);
    private GPSCoordinate relevanceZonePoint2 = new GPSCoordinate(39734080, -8821980);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeComponents();
        initializeIVIEngine();
        initializeWaypointIterator();
        loadSignalCodes();
        setupImageListIndexer();
        setupDisplayController();
        loadTCPConfig();
        disableToolStripButton();
    }

    private void initializeComponents() {
        toolStripTextBox1 = findViewById(R.id.toolStripTextBox1);
        toolStripTextBox2 = findViewById(R.id.toolStripTextBox2);
        toolStripTextBox3 = findViewById(R.id.toolStripTextBox3);
        toolStripLabel4 = findViewById(R.id.toolStripLabel4);
        toolStripButton9 = findViewById(R.id.toolStripButton9);
    }

    private void initializeIVIEngine() {
        ivimEngine = new IVIMEngine();
        gpsController = new GPSController(ivimEngine);

        ivimEngine.setAwarenessZoneEnteredListener(this::awarenessZoneEntered);
        ivimEngine.setAwarenessZoneLeavedListener(this::awarenessZoneLeaved);
        ivimEngine.setDetectionZoneEnteredListener(this::detectionZoneEntered);
        ivimEngine.setDetectionZoneLeavedListener(this::detectionZoneLeaved);
        ivimEngine.setRelevanceZoneEnteredListener(this::relevanceZoneEntered);
        ivimEngine.setRelevanceZoneLeavedListener(this::relevanceZoneLeaved);
    }

    private void initializeWaypointIterator() {
        wayPointIterator = new WayPointIterator();
        wayPointIterator.loadFromCSV(getFilesDir() + "/WayPoints.csv");
    }

    private void loadSignalCodes() {
        signalCodes = new ArrayList<>();

        // Image Not Found position zero
        SignalCode signalCode = new SignalCode();
        signalCode.setData(0, 0, 0);
        signalCodes.add(signalCode);

        // Add other signal codes
        signalCode = new SignalCode();
        signalCode.setData(620, 12, 116);
        signalCodes.add(signalCode);

        signalCode = new SignalCode();
        signalCode.setData(620, 13, 815);
        signalCodes.add(signalCode);

        signalCode = new SignalCode();
        signalCode.setData(620, 12, 557);
        signalCodes.add(signalCode);

        signalCode = new SignalCode();
        signalCode.setData(620, 12, 558);
        signalCodes.add(signalCode);

        signalCode = new SignalCode();
        signalCode.setData(620, 12, 559);
        signalCodes.add(signalCode);
    }

    private void setupImageListIndexer() {
        imagelistIndexer = new ImagelistIndexer(this, signalCodes);
    }

    private void setupDisplayController() {
        displayController = new DisplayController();
        displayController.initDisplay(findViewById(R.id.awarenessZoneGroupBox),
                findViewById(R.id.detectionZoneGroupBox),
                findViewById(R.id.relevanceZoneGroupBox), imagelistIndexer);
    }

    private void loadTCPConfig() {
        tcpConfigFile = getFilesDir() + "/TCPConfig.txt";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(tcpConfigFile));
            ip = reader.readLine();
            port = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disableToolStripButton() {
        toolStripButton9.setEnabled(false);
    }

    private void updateCoordinatesDisplay() {
        toolStripTextBox1.setText(String.valueOf(ivimEngine.getCurrentGPSLocation().getLatitude()));
        toolStripTextBox2.setText(String.valueOf(ivimEngine.getCurrentGPSLocation().getLongitude()));
        toolStripTextBox3.setText(String.valueOf(ivimEngine.getCurrentGPSLocation().getBearing()));
    }

    private void updateWayPointDisplay() {
        toolStripLabel4.setText("WayPoint: " + wayPointIterator.getCurrentWayPointPosition() + "/" + wayPointIterator.getWayPointCount());
    }

    public void awarenessZoneEntered(Object sender, IVIMDataEventArgs e) {
        displayController.showAwarenessZoneSignal(e.getStationID(), e.getIviIdentificationNumber(),
                e.getSignal().getIviDisplay().getIso14823().getCountryCode(),
                e.getSignal().getIviDisplay().getIso14823().getServiceCategoryCode(),
                e.getSignal().getIviDisplay().getIso14823().getPictogramCategoryCode(),
                e.getSignal().getIviDisplay().getIviText().getLanguage(),
                e.getSignal().getIviDisplay().getIviText().getTextContent());

        invalidate();
    }

    public void awarenessZoneLeaved(Object sender, IVIMDataEventArgs e) {
        displayController.removeAwarenessZoneSignal(e.getStationID(), e.getIviIdentificationNumber());
        invalidate();
    }

    public void detectionZoneEntered(Object sender, IVIMDataEventArgs e) {
        displayController.showDetectionZoneSignal(e.getStationID(), e.getIviIdentificationNumber(),
                e.getSignal().getIviDisplay().getIso14823().getCountryCode(),
                e.getSignal().getIviDisplay().getIso14823().getServiceCategoryCode(),
                e.getSignal().getIviDisplay().getIso14823().getPictogramCategoryCode(),
                e.getSignal().getIviDisplay().getIviText().getLanguage(),
                e.getSignal().getIviDisplay().getIviText().getTextContent());

        invalidate();
    }

    public void detectionZoneLeaved(Object sender, IVIMDataEventArgs e) {
        displayController.removeDetectionZoneSignal(e.getStationID(), e.getIviIdentificationNumber());
        invalidate();
    }

    public void relevanceZoneEntered(Object sender, IVIMDataEventArgs e) {
        displayController.showRelevanceZoneSignal(e.getStationID(), e.getIviIdentificationNumber(),
                e.getSignal().getIviDisplay().getIso14823().getCountryCode(),
                e.getSignal().getIviDisplay().getIso14823().getServiceCategoryCode(),
                e.getSignal().getIviDisplay().getIso14823().getPictogramCategoryCode(),
                e.getSignal().getIviDisplay().getIviText().getLanguage(),
                e.getSignal().getIviDisplay().getIviText().getTextContent());

        invalidate();
    }

    public void relevanceZoneLeaved(Object sender, IVIMDataEventArgs e) {
        displayController.removeRelevanceZoneSignal(e.getStationID(), e.getIviIdentificationNumber());
        invalidate();
    }

    private void invalidate() {
        runOnUiThread(() -> {
            updateCoordinatesDisplay();
            updateWayPointDisplay();
        });
    }
}

*/