package com.example.its_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import OBUSDK.GPSController;
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
    private static final int RADIUS_IN_METERS = 1500;
    private static final double threshold = RADIUS_IN_METERS * 0.75;
    private Location previousLocation;

    private double latitude;
    private double longitude;
    private double bearing;

    APIService apiService = APIClient.getClient().create(APIService.class);
    private List<VirtualRSU> virtualRSUs;
    private List<GPSLocation> simulatedCoordinates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_3_zones);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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

    private void initCoordinates() {
        simulatedCoordinates = new ArrayList<>();
        simulatedCoordinates.add(new GPSLocation(39.734679157542466, -8.821018864937866, -129));
        simulatedCoordinates.add(new GPSLocation(39.73458169711753, -8.821183820792282, -129));
        simulatedCoordinates.add(new GPSLocation(39.734447108685124, -8.821397136094504, -129));
        simulatedCoordinates.add(new GPSLocation(39.73430994165984, -8.821621771099501, -129));
        simulatedCoordinates.add(new GPSLocation(39.73427384502887, -8.821680109145575, -129));
        simulatedCoordinates.add(new GPSLocation(39.73424585421858, -8.821714643544576, -129));
        simulatedCoordinates.add(new GPSLocation(39.734237087887514, -8.821743477291486, -129));
        simulatedCoordinates.add(new GPSLocation(39.734194079209516, -8.821806305091949, -129));
        simulatedCoordinates.add(new GPSLocation(39.73415205800571, -8.821874138297241, -129));
        simulatedCoordinates.add(new GPSLocation(39.734123465859014, -8.821921132898172, -129));
        simulatedCoordinates.add(new GPSLocation(39.734093271919534, -8.821991484941643, -87));
        simulatedCoordinates.add(new GPSLocation(39.73409636592532, -8.822060551823776, -87));
        simulatedCoordinates.add(new GPSLocation(39.73409688159295, -8.822127607049149, -87));
        simulatedCoordinates.add(new GPSLocation(39.73411441428964, -8.822293233455818, -85));
        simulatedCoordinates.add(new GPSLocation(39.73414741700073, -8.822719034136933, -85));
        simulatedCoordinates.add(new GPSLocation(39.73416443401747, -8.822967809023064, -85));
        simulatedCoordinates.add(new GPSLocation(39.73416804368715, -8.82307675530474, -85));
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
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    latitude = 39.734123465859014;
                    longitude = -8.821921132898172;
                    bearing = -129;

                    if (previousLocation == null || location.distanceTo(previousLocation) >= threshold) {
                        previousLocation = location;
                        getRSUDentroRaio();
                    } else {
                        gpsController.updateGPSLocation(latitude, longitude, bearing);
                    }

                    String coordinates = "Latitude: " + latitude + ", Longitude: " + longitude;
                    Toast.makeText(MainActivity3Zones.this, coordinates, Toast.LENGTH_LONG).show();
                }
            }
        };
        fusedLocationClient.getCurrentLocation(0, null)
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = 39.734123465859014;
                            longitude = -8.821921132898172;
                            bearing = -129;

                            if (previousLocation == null || location.distanceTo(previousLocation) >= threshold) {
                                previousLocation = location;
                                getRSUDentroRaio();
                            } else {
                                gpsController.updateGPSLocation(latitude, longitude, bearing);
                            }

                            String coordinates = "Latitude: " + latitude + ", Longitude: " + longitude;
                            Toast.makeText(MainActivity3Zones.this, coordinates, Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
