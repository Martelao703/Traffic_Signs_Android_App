package com.example.its_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
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
import OBUSDK.JsonData.Rsu;
import OBUSDK.JsonData.VirtualRSU;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity3Zones extends AppCompatActivity {
    private IVIMEngine ivimEngine;
    private ImagelistIndexer imagelistIndexer;
    private DisplayController displayController;
    private GPSController gpsController;
    private List<SignalCode> signalCodes;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int RADIUS_IN_METERS = 10000000;
    private double latitude;
    private double longitude;

    APIService apiService = APIClient.getClient().create(APIService.class);

    //Lista dos RSUs dentro do raio definido
    private List<VirtualRSU> virtualRSUs = null;
    private List<Rsu> RSUsInArea = null;
    private List<JsonAdapter> jsonAdaptersBuilt = new ArrayList<>();

    //Lidar com as imagens
    //private LinearLayout imageContainer;
    private List<Drawable> signalImageList;
    private GridLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_3_zones);
        initializeIVIEngine();
        setupImageListIndexer();
        setupDisplayController();

        // Confirmar se tem permissão para aceder à localização
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Se não tiver permissão, pedir permissão
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Se tiver permissão, obter as coordenadas
            getLocation();
        }

        signalCodes = new ArrayList<>();
        signalCodes.add(new SignalCode("image_not_found",0,0,0));
        signalCodes.add(new SignalCode("image_180px_vienna_convention_road_sign_b1_v1",620,12,116));
        signalCodes.add(new SignalCode("image_180px_vienna_convention_road_sign_e12aa_v1",620,13,815));
        signalCodes.add(new SignalCode("image_180px_vienna_convention_road_sign_c14_v1_30",620,12,557));
        signalCodes.add(new SignalCode("image_180px_vienna_convention_road_sign_c14_v1_40",620,12,558));
        signalCodes.add(new SignalCode("image_180px_vienna_convention_road_sign_c14_v1_50",620,12,559));

        List<Drawable> signalImageList = new ArrayList<>();
        imageContainer = findViewById(R.id.detectionImageContainer);

        String[] imageResourceNames = {
                "image_180px_vienna_convention_road_sign_b1_v1",
                "image_180px_vienna_convention_road_sign_c14_v1_30",
                "image_180px_vienna_convention_road_sign_c14_v1_40",
                "image_180px_vienna_convention_road_sign_c14_v1_50",
                "image_180px_vienna_convention_road_sign_e12aa_v1",
                "image_not_found",
        };

        // Iterate through the resource names and convert to drawables
        for (String resourceName : imageResourceNames) {
            int resourceId = getResources().getIdentifier(resourceName, "drawable", getPackageName());
            if (resourceId != 0) {
                addImage(resourceId);
                Drawable drawable = ContextCompat.getDrawable(this, resourceId);
                if (drawable != null) {
                    signalImageList.add(drawable);
                } else {
                    Log.e("DrawableError", "Drawable not found for resource ID: " + resourceId);
                }
            } else {
                Log.e("ResourceError", "Resource not found for name: " + resourceName);
            }
        }
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
                        Log.d("RSU", "Virtual RSUs is null: " + virtualRSUs.toString());
                    } else {
                        Log.d("RSU", "Virtual RSUs: " + virtualRSUs.toString());

                        /*if (virtualRSUs.size() > 0) {
                            for (VirtualRSU rsu : virtualRSUs) {
                                Log.d("RSU", "RSU: " + rsu.toString());
                                getRSUdetailedData(rsu.getVirtualStationID());
                            }
                        }*/
                        getRSUdetailedData(8);
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
    public void getRSUdetailedData(int id) {
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

                        if (rsu.getData().getITSApp().getFacilities().getIVIMap().size() > 0) {
                            JsonAdapter jsonAdapter = new JsonAdapter(rsu.getData().getITSApp().getFacilities().getIVIMap().get(0).getIvim());
                            jsonAdapter.buildIVIMStructures();
                            jsonAdaptersBuilt.add(jsonAdapter);
                        }
                        //TODO Ver o que fazer quando nã temos IVIMs no request
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
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                getRSUDentroRaio();

                String coordinates = "Latitude: " + latitude + ", Longitude: " + longitude;
                Toast.makeText(MainActivity3Zones.this, coordinates, Toast.LENGTH_LONG).show();
            }

        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obter a localização atual a cada 10 metros
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500000, 5, locationListener);
        }
    }

    private void addImage1(int resId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        // Centraliza a imagem horizontalmente
        params.gravity = android.view.Gravity.CENTER_HORIZONTAL;
        // Adiciona margens superior e inferior
        params.setMargins(0, 16, 0, 16);

        imageContainer.addView(imageView, params);
    }

    private void addImage(int resId) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = GridLayout.LayoutParams.WRAP_CONTENT;
        params.height = GridLayout.LayoutParams.WRAP_CONTENT;

        // Adiciona margens para espaçamento
        params.setMargins(0, 10, 0,0);

        // Adiciona a imagem ao GridLayout
        imageContainer.addView(imageView, params);
    }

    private void initializeIVIEngine() {
        ivimEngine = new IVIMEngine();
        gpsController = new GPSController(ivimEngine);

        ivimEngine.setAwarenessZoneEntered(this::awarenessZoneEntered);
        ivimEngine.setAwarenessZoneLeaved(this::awarenessZoneLeaved);
        ivimEngine.setDetectionZoneEntered(this::detectionZoneEntered);
        ivimEngine.setDetectionZoneLeaved(this::detectionZoneLeaved);
        ivimEngine.setRelevanceZoneEntered(this::relevanceZoneEntered);
        ivimEngine.setRelevanceZoneLeaved(this::relevanceZoneLeaved);
    }

    private void setupImageListIndexer() {
        imagelistIndexer = new ImagelistIndexer(signalImageList, signalCodes);
    }

    private void setupDisplayController() {
        displayController = new DisplayController();
        displayController.initDisplay(findViewById(R.id.awarenessImageContainer),
                findViewById(R.id.detectionImageContainer),
                findViewById(R.id.relevanceImageContainer), imagelistIndexer);
    }

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
