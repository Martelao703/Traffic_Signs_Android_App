package com.example.its_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import OBUSDK.JsonController.*;
import OBUSDK.JsonData.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.Manifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private LocationManager locationManager;
    private LocationListener locationListener;

    //Alterar o raio quando quisermos alterar a distância máxima para a qual queremos encontrar RSUs
    private static final int RADIUS_IN_METERS = 10000000;
    private double latitude;
    private double longitude;

    //Variável para aceder à API
    APIService apiService = APIClient.getClient().create(APIService.class);

    //Lista dos RSUs dentro do raio definido
    private List<VirtualRSU> virtualRSUs = null;
    private List<Rsu> RSUsInArea = null;
    private List<JsonAdapter> jsonAdaptersBuilt = new ArrayList<>();

    //Lidar com as imagens
    //private LinearLayout imageContainer;
    private GridLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_detection);

        // Confirmar se tem permissão para aceder à localização
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Se não tiver permissão, pedir permissão
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Se tiver permissão, obter as coordenadas
            getLocation();
        }


        imageContainer = findViewById(R.id.imageContainer);
        int imageResource = getResources().getIdentifier("image_180px_vienna_convention_road_sign_b1_v1", "drawable", getPackageName());
        addImage(imageResource);
        imageResource = getResources().getIdentifier("image_180px_vienna_convention_road_sign_c14_v1_30", "drawable", getPackageName());
        addImage(imageResource);
        imageResource = getResources().getIdentifier("image_180px_vienna_convention_road_sign_c14_v1_40", "drawable", getPackageName());
        addImage(imageResource);
        imageResource = getResources().getIdentifier("image_180px_vienna_convention_road_sign_c14_v1_50", "drawable", getPackageName());
        addImage(imageResource);
        imageResource = getResources().getIdentifier("image_180px_vienna_convention_road_sign_e12aa_v1", "drawable", getPackageName());
        addImage(imageResource);
        imageResource = getResources().getIdentifier("image_not_found", "drawable", getPackageName());
        addImage(imageResource);

        /*TextView textView = findViewById(R.id.RSU_data);
        textView.setText("RSU data: " + rsu.toString());*/

        /*
        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        */
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

                        if (virtualRSUs.size() > 0) {
                            for (VirtualRSU rsu : virtualRSUs) {
                                Log.d("RSU", "RSU: " + rsu.toString());
                                getRSUdetailedData(rsu.getVirtualStationID());
                            }
                        }
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
                Toast.makeText(MainActivity.this, coordinates, Toast.LENGTH_LONG).show();
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
}