package com.example.its_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import OBUSDK.JsonController.*;
import OBUSDK.JsonData.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        TextView textView = findViewById(R.id.RSU_data);

        APIService apiService = APIClient.getClient().create(APIService.class);

        Call<Rsu> call = apiService.doGetRsu(3);
        call.enqueue(new Callback<Rsu>() {
            @Override
            public void onResponse(Call<Rsu> call, Response<Rsu> response) {
                if (response.isSuccessful()) {
                    Rsu rsu = response.body();

                    if (rsu == null) {
                        Log.d("RSU", "Rsu is null: " + rsu.toString());
                    } else {
                        Log.d("RSU", "RSU: " + rsu.toString());
                        textView.setText("RSU data: " + rsu.toString());


                    }
                } else {
                    Log.d("RSU", "Raw response (response not successful): " + response.raw().body().toString());
                }
            }


            @Override
            public void onFailure(Call<Rsu> call, Throwable t) {
                Log.d("RSU", "Failed to get RSU: " + t.getMessage());
                textView.setText("Failed to get RSU data");
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