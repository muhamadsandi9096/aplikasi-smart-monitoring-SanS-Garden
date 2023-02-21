package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ArtikelView extends AppCompatActivity {
    WebView Artikelviewku;
    WebSettings Artikelsettingku;
    ImageButton mBackArtikelview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel_view);

        mBackArtikelview = findViewById(R.id.backArtikelview);

        Bundle extras = getIntent().getExtras();
        String url = "";
        String link= extras.getString("link");

        try {
            url= URLEncoder.encode(link,"UTF-8");
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        Artikelviewku = (WebView) findViewById(R.id.artikelview1);
        Artikelsettingku = Artikelviewku.getSettings();
        Artikelviewku.getSettings().setJavaScriptEnabled(true);
        Artikelviewku.getSettings().setLoadWithOverviewMode(true);
        Artikelviewku.getSettings().setUseWideViewPort(true);
        Artikelviewku.setWebViewClient(new WebViewClient());
        Artikelviewku.loadUrl("http://docs.google.com/viewerng/viewer?embedded=true&url="+url);
        //Artikelviewku.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url="+url);
        Toast.makeText(this, "'Sedang Loading...' Harap Mengaktifkan dan Memeriksa Jaringan Internet Anda!", Toast.LENGTH_LONG).show();

        mBackArtikelview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReferensiArtikel.class));
            }
        });
    }
}