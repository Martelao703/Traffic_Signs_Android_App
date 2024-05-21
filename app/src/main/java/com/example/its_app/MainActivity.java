package com.example.its_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import OBUSDK.JsonController.*;
import OBUSDK.PerEncDec.*;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        TextView textView = findViewById(R.id.RSU_data);

        APIService apiService = APIClient.getClient().create(APIService.class);

        Call<ResponseBody> call = apiService.doGetRsu(3);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String rawJson = response.body().string();
                        System.out.println("Raw JSON response: " + rawJson);
                        Log.d("RSU", "Raw JSON response: " + rawJson);

                        Gson gson = new Gson();
                        Rsu responseTest = gson.fromJson(rawJson, Rsu.class);

                        if (responseTest == null) {
                            System.out.println("Deserialized response is null: " + responseTest);
                            Log.d("RSU", "Deserialized response is null: " + responseTest);
                        } else {
                            System.out.println("Deserialized RSU response: " + responseTest);
                            Log.d("RSU", "Deserialized RSU response: " + responseTest);
                            textView.setText("RSU data: " + responseTest);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("RSU", "Error reading raw JSON response: " + e.getMessage());
                    }
                } else {
                    System.out.println("Raw response if not successful: " + response.raw().toString());
                    Log.d("RSU", "Raw response if not successful: " + response.raw().toString());
                }

                /*if (response.isSuccessful()) {
                    ResponseBody responseTest = response.body();
                    if (responseTest == null) {
                        System.out.println("Response if null: " + responseTest);
                        Log.d("RSU", "Response if null: " + responseTest);

                        System.out.println("Raw response if null: " + response.raw().body().toString());
                        Log.d("RSU", "Raw response if null: " + response.raw().body().toString());
                    } else {
                        System.out.println("RSU response if not null: " + responseTest);
                        Log.d("RSU", "RSU response if not null: " + responseTest);

                        System.out.println("Raw response if not null: " + response.raw().body().toString());
                        Log.d("RSU", "Raw response if not null: " + response.raw().body().toString());
                        textView.setText("RSU data: " + responseTest);
                    }
                } else {
                    System.out.println("Raw response if not suc: " + response.raw().body().toString());
                    Log.d("RSU", "Raw response if not suc: " + response.raw().body().toString());
                }*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                System.out.println("Failed to get RSU date: " + t.getMessage());
                Log.d("RSU", "Failed to get RSU date: " + t.getMessage());
                textView.setText("Failed to get RSU date: ");
                call.cancel();
            }
        });

        System.out.println("RSU data: " + call.toString());

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
}