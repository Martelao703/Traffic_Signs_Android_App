package com.example.its_app;

import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OBUSDK.GPSController;
import OBUSDK.GPSCoordinate;
import OBUSDK.GPSLocation;
import OBUSDK.IVIMDataEventArgs;
import OBUSDK.IVIMEngine;
import OBUSDK.JsonController.APIClient;
import OBUSDK.JsonController.APIService;
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
    private final JsonController ivimController = new JsonController();
    private DisplayController displayController;

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int RADIUS_IN_METERS = 1000;
    private static final double threshold = RADIUS_IN_METERS * 0.75;
    private Location previousCallLocation;

    private double latitude;
    private double longitude;
    private double bearing;

    APIService apiService = APIClient.getClient().create(APIService.class);
    Location testPinLocation = new Location("gps");
    Location testPinLocation2 = new Location("gps");
    private List<VirtualRSU> virtualRSUs;
    private boolean apiCallFlag = false;
    private ImageButton imgBtnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_3_zones);
        imgBtnAbout = findViewById(R.id.imgBtnAbout);

        imgBtnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3Zones.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setTestLocations();
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
    public void getRSUDentroRaio() {
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
                        Log.d("RSU", "VirtualRSUs is null: " + virtualRSUs.toString());
                    } else {
                        getRSUsDetailedData(virtualRSUs, () -> gpsController.updateGPSLocation(latitude, longitude, bearing));
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
    public void getRSUdetailedData(int id, final Runnable callback) {
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
                        }
                    }
                } else {
                    Log.d("RSU", "Raw response (response not successful): " + response.raw().body().toString());
                }
                callback.run();
            }

            @Override
            public void onFailure(Call<Rsu> call, Throwable t) {
                Log.d("RSU", "Failed to get RSU: " + t.getMessage());
                call.cancel();
                callback.run();
            }
        });
    }

    public void getRSUsDetailedData(List<VirtualRSU> virtualRSUs, Runnable callback) {
        final int[] remainingCalls = {virtualRSUs.size()};

        for (VirtualRSU virtualRSU : virtualRSUs) {
            getRSUdetailedData(virtualRSU.getVirtualStationID(), () -> {
                remainingCalls[0]--;
                if (remainingCalls[0] == 0) {
                    callback.run();
                }
            });
        }
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
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    bearing = getBearing(location);

                    if (previousCallLocation == null || location.distanceTo(previousCallLocation) >= threshold) {
                        getRSUDentroRaio();
                        previousCallLocation = location;
                        apiCallFlag = true;
                    } else {
                        gpsController.updateGPSLocation(latitude, longitude, bearing);
                        apiCallFlag = false;
                    }
                }
            }
        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = new LocationRequest.Builder(1500)
                    .setIntervalMillis(1500)
                    .setPriority(PRIORITY_HIGH_ACCURACY)
                    .setMinUpdateDistanceMeters(1)
                    .build();

            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
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

    public double getBearing(Location location) {
        if (testPinLocation2.distanceTo(location) <= 53) {
            return  150;
        } else if (testPinLocation.distanceTo(location) >= 78 && testPinLocation.distanceTo(location) <= 550) {
            return  -129;
        } else {
            return -87;
        }
    }

    public void setTestLocations() {
        testPinLocation.setLatitude(39.73416274775048);
        testPinLocation.setLongitude(-8.82285464425065);
        testPinLocation2.setLatitude(39.733616916903074);
        testPinLocation2.setLongitude(-8.821500120452285);
    }
}
