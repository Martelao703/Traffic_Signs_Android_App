package com.example.its_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import OBUSDK.JsonController.*;
import OBUSDK.PerEncDec.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        TextView textView = findViewById(R.id.RSU_data);

        APIService apiService = APIClient.getClient().create(APIService.class);

        Call<String> call = apiService.doGetRsu(1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String jsonResponse = response.body();
                if (jsonResponse == null) {
                    System.out.println("RSU response is null");
                    Log.d("RSU", "RSU response is null");
                    textView.setText("RSU data: null");
                } else {
                    // Log the raw JSON response
                    System.out.println("RSU JSON Response: " + jsonResponse);
                    Log.d("RSU", "RSU JSON Response: " + jsonResponse);

                    // Update the UI with the JSON response
                    textView.setText("RSU data: " + jsonResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Failed to get RSU data: " + t.getMessage());
                Log.d("RSU", "Failed to get RSU data: " + t.getMessage());
                textView.setText("Failed to get RSU data");
            }
        });

        /*call.enqueue(new Callback<RSU>() {
            @Override
            public void onResponse(Call<RSU> call, Response<RSU> response) {
                RSU rsu = response.body();
                if (rsu == null) {
                    System.out.println("RSU is null");
                    Log.d("RSU", "RSU is null");
                }
                else {
                    System.out.println("RSU ITSApp: " + rsu.getITSApp().isEnabled());
                    System.out.println("RSU toString(): " + rsu.toString());
                    Log.d("RSU", "RSU ITSApp: " + rsu.getITSApp().isEnabled());
                    textView.setText("RSU data: " + rsu.toString());
                }
            }

            @Override
            public void onFailure(Call<RSU> call, Throwable t) {
                call.cancel();
            }
        });
        */
        /*TextView textView = findViewById(R.id.RSU_data);
        textView.setText("RSU data: " + rsu.toString());
                System.out.println("RSU onFailure: " + t.getMessage());
                Log.d("RSU", "RSU onFailure: " + t.getMessage());
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
        System.out.println("RSU data: " + call.toString());
    }
}