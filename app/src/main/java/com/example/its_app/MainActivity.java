package com.example.its_app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import android.widget.Toast;
import android.Manifest;

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
    private List<VirtualRSU> virtualRSUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_detection);

        // Confirmar se tem permissão para aceder à localização
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Se não tiver permissão, pedir permissão
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Se tiver permissão, obter as coordenadas
            getLocation();
        }


        Call<Rsu> call = apiService.doGetRsu(8);
        call.enqueue(new Callback<Rsu>() {
            @Override
            public void onResponse(Call<Rsu> call, Response<Rsu> response) {
                if (response.isSuccessful()) {
                    Rsu rsu = response.body();

                    if (rsu == null) {
                        Log.d("RSU", "Rsu is null: " + rsu.toString());
                    } else {
                        Log.d("RSU", "RSU: " + rsu.toString());

                        JsonAdapter jsonAdapter = new JsonAdapter(rsu.getData().getITSApp().getFacilities().getIVIMap().get(0).getIvim());
                        jsonAdapter.buildIVIMStructures();
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

    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

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

                String coordinates = "Latitude: " + latitude + ", Longitude: " + longitude;
                Toast.makeText(MainActivity.this, coordinates, Toast.LENGTH_LONG).show();
            }

        };

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Obter a localização atual a cada 10 metros
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500000, 5, locationListener);
        }
    }
}