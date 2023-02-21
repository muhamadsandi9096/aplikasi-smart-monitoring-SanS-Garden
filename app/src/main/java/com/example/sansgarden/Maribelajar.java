package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Maribelajar extends AppCompatActivity {
    ImageButton mBackmaribelajar;
    Button mButtonViewTerung, mButtonViewTomat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maribelajar);
        mBackmaribelajar = findViewById(R.id.backmaribelajar);
        mButtonViewTerung = findViewById(R.id.viewwebButtonTerung);
        mButtonViewTomat = findViewById(R.id.viewwebButtonTomat);

        mBackmaribelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bna.class));
            }
        });

        mButtonViewTerung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WEBView.class));
                Intent i = new Intent(Maribelajar.this,WEBView.class);
                i.putExtra("url", "https://distan.bulelengkab.go.id/informasi/detail/artikel/budidaya-terong-solanum-melongena-l-11");
                //i.putExtra("url", "http://cybex.pertanian.go.id/mobile/artikel/91168/BUDIDAYA-TANAMAN-TERONG/");
                startActivity(i);

            }
        });

        mButtonViewTomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),WEBView.class));
                Intent i = new Intent(Maribelajar.this,WEBView.class);
                i.putExtra("url", "http://cybex.pertanian.go.id/mobile/artikel/81277/CARA-BUDIDAYA-TANAMAN-TOMAT-DENGAN-PANEN-SINGKAT/");
                startActivity(i);

            }
        });

    }
}