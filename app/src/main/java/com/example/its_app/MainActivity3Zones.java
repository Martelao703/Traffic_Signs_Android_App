package com.example.its_app;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OBUSDK.GPSController;
import OBUSDK.IVIMDataEventArgs;
import OBUSDK.IVIMEngine;
import OBUSDK.JsonController.APIClient;
import OBUSDK.JsonController.APIService;
import OBUSDK.JsonController.JsonAdapter;
import OBUSDK.JsonController.JsonController;
import OBUSDK.JsonData.IVIM;
import OBUSDK.JsonData.Rsu;
import OBUSDK.JsonData.VirtualRSU;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity3Zones extends AppCompatActivity {
    private IVIMEngine ivimEngine;
    private GPSController gpsController;
    private JsonController ivimController = new JsonController();
    private DisplayController displayController;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int RADIUS_IN_METERS = 1500;
    private static final double threshold = RADIUS_IN_METERS * 0.75;
    private Location previousLocation;

    private double latitude;
    private double longitude;
    private double bearing;

    APIService apiService = APIClient.getClient().create(APIService.class);
    private List<VirtualRSU> virtualRSUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_3_zones);

        initializeIVIEngine();
        setupDisplayController();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }

    private void initializeIVIEngine() {
        ivimEngine = new IVIMEngine();
        gpsController = new GPSController(ivimEngine);

        ivimEngine.setIVIController(ivimController);
        ivimEngine.setAwarenessZoneEntered(this::awarenessZoneEntered);
        ivimEngine.setAwarenessZoneLeaved(this::awarenessZoneLeaved);
        ivimEngine.setDetectionZoneEntered(this::detectionZoneEntered);
        ivimEngine.setDetectionZoneLeaved(this::detectionZoneLeaved);
        ivimEngine.setRelevanceZoneEntered(this::relevanceZoneEntered);
        ivimEngine.setRelevanceZoneLeaved(this::relevanceZoneLeaved);
    }

    private void setupDisplayController() {
        displayController = new DisplayController(this);
        displayController.initDisplay(
                findViewById(R.id.awarenessImageContainer),
                findViewById(R.id.detectionImageContainer),
                findViewById(R.id.relevanceImageContainer));
    }


    //Obter a lista de RSUs dentro do raio definido
    public void getRSUDentroRaio(Runnable onSuccess) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("latitude", latitude);
        requestBody.put("longitude", longitude);
        requestBody.put("radius", RADIUS_IN_METERS);

        Call<List<VirtualRSU>> callNearestRSU = apiService.doGetRsuByDistance(requestBody);

        callNearestRSU.enqueue(new Callback<List<VirtualRSU>>() {

            @Override
            public void onResponse(Call<List<VirtualRSU>> call, Response<List<VirtualRSU>> response) {
                if (response.isSuccessful()) {
                    virtualRSUs = response.body();

                    if (virtualRSUs == null) {
                        Log.d("RSU", "Virtual RSUs is null: " + virtualRSUs.toString());
                    } else {
                        Log.d("RSU", "Virtual RSUs: " + virtualRSUs.toString());
                        onSuccess.run();
                    }
                } else {
                    Log.d("API", "Response not successful: " + response.raw().body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<VirtualRSU>> call, Throwable t) {
                Log.d("RSU", "Failed to get RSU: " + t.getMessage());
                call.cancel();
            }
        });
    }

    //Obter os detalhes de um RSU específico após obter a lista de RSUs dentro do raio definido
    public void getRSUdetailedData(int id, Runnable onSuccess) {
        Call<Rsu> call = apiService.doGetRsu(id);
        call.enqueue(new Callback<Rsu>() {
            @Override
            public void onResponse(Call<Rsu> call, Response<Rsu> response) {
                if (response.isSuccessful()) {
                    Rsu rsu = response.body();

                    if (rsu == null) {
                        Log.d("RSU", "Rsu is null: " + rsu.toString());
                    } else {
                        Log.d("RSU", "RSU: " + rsu.toString());
                        Log.d("IVIMap", "IVIMap size" + rsu.getData().getITSApp().getFacilities().getIVIMap().size());

                        if (!rsu.getData().getITSApp().getFacilities().getIVIMap().isEmpty()) {
                            for (int i = 0; i < rsu.getData().getITSApp().getFacilities().getIVIMap().size(); i++) {
                                IVIM ivim = rsu.getData().getITSApp().getFacilities().getIVIMap().get(i).getIvim();
                                ivimEngine.run(ivim);
                            }

                            // Execute the onSuccess callback after processing is done
                            onSuccess.run();
                        }
                    }
                } else {
                    Log.d("RSU", "Raw response (response not successful): " + response.raw().body().toString());
                }
            }

            @Override
            public void onFailure(Call<Rsu> call, Throwable t) {
                Log.d("RSU", "Failed to get RSU: " + t.getMessage());
                call.cancel();
            }
        });
    }

    //Obter a resposta do utilizador relativamente à permissão de aceder à localização
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Obter a localização atual
    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //latitude = location.getLatitude();
                //longitude = location.getLongitude();
                //bearing = location.getBearing(); ss

                latitude = 39.734123465859014;
                longitude = -8.821921132898172;
                bearing = -129;

                if (previousLocation == null || location.distanceTo(previousLocation) >= threshold) {
                    previousLocation = location;
                    // Fetch and process RSUs only if significant movement is detected
                    getRSUDentroRaio(() -> {
                        if (virtualRSUs != null) {
                            for (VirtualRSU virtualRSU : virtualRSUs) {
                                getRSUdetailedData(virtualRSU.getVirtualStationID(), () -> {
                                    // Update GPS location after processing each RSU
                                    //gpsController.updateGPSLocation(latitude, longitude, bearing);
                                });
                            }
                        } else {
                            Log.e("RSU", "No virtual RSUs available.");
                        }
                    });
                }
                // Directly update GPS location if movement is within the threshold
                gpsController.updateGPSLocation(latitude, longitude, bearing);

                String coordinates = "Latitude: " + latitude + ", Longitude: " + longitude;
                Toast.makeText(MainActivity3Zones.this, coordinates, Toast.LENGTH_LONG).show();
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obter a localização atual a cada 10 metros
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500000, 5, locationListener);
        }
    }

    // IVIM Event Handlers
    public void awarenessZoneEntered(Object sender, IVIMDataEventArgs e) {
        displayController.showAwarenessZoneSignal(e.getStationID(), e.getIviIdentificationNumber(),
                e.getSignal().getIviDisplay().getIso14823().getCountryCode(),
                e.getSignal().getIviDisplay().getIso14823().getServiceCategoryCode(),
                e.getSignal().getIviDisplay().getIso14823().getPictogramCategoryCode(),
                e.getSignal().getIviDisplay().getIviText().getLanguage(),
                e.getSignal().getIviDisplay().getIviText().getTextContent());
    }

    public void awarenessZoneLeaved(Object sender, IVIMDataEventArgs e) {
        displayController.removeAwarenessZoneSignal(e.getStationID(), e.getIviIdentificationNumber());
    }

    public void detectionZoneEntered(Object sender, IVIMDataEventArgs e) {
        displayController.showDetectionZoneSignal(e.getStationID(), e.getIviIdentificationNumber(),
                e.getSignal().getIviDisplay().getIso14823().getCountryCode(),
                e.getSignal().getIviDisplay().getIso14823().getServiceCategoryCode(),
                e.getSignal().getIviDisplay().getIso14823().getPictogramCategoryCode(),
                e.getSignal().getIviDisplay().getIviText().getLanguage(),
                e.getSignal().getIviDisplay().getIviText().getTextContent());
    }

    public void detectionZoneLeaved(Object sender, IVIMDataEventArgs e) {
        displayController.removeDetectionZoneSignal(e.getStationID(), e.getIviIdentificationNumber());
    }

    public void relevanceZoneEntered(Object sender, IVIMDataEventArgs e) {
        displayController.showRelevanceZoneSignal(e.getStationID(), e.getIviIdentificationNumber(),
                e.getSignal().getIviDisplay().getIso14823().getCountryCode(),
                e.getSignal().getIviDisplay().getIso14823().getServiceCategoryCode(),
                e.getSignal().getIviDisplay().getIso14823().getPictogramCategoryCode(),
                e.getSignal().getIviDisplay().getIviText().getLanguage(),
                e.getSignal().getIviDisplay().getIviText().getTextContent());
    }

    public void relevanceZoneLeaved(Object sender, IVIMDataEventArgs e) {
        displayController.removeRelevanceZoneSignal(e.getStationID(), e.getIviIdentificationNumber());
    }
}
