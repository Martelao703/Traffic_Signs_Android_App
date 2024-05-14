package com.example.its_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_page);

        OBUSDK.JsonController.RequestHandler teste = new OBUSDK.JsonController.RequestHandler();

        OBUSDK.PerEncDec.RSU rsu = teste.doGetRSU(1);

        TextView textView = findViewById(R.id.RSU_data);
        textView.setText("RSU data: " + rsu.toString());
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