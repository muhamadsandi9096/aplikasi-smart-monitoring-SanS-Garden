package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class WEBView extends AppCompatActivity {

    WebView webviewku;
    WebSettings websettingku;
    ImageButton mBackwebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mBackwebview = findViewById(R.id.backwebview);

        Bundle extras = getIntent().getExtras();
        String url= extras.getString("url");

        webviewku = (WebView) findViewById(R.id.webview1);
        websettingku = webviewku.getSettings();
        webviewku.setWebViewClient(new WebViewClient());
        webviewku.loadUrl(url);

        mBackwebview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Maribelajar.class));
            }
        });
    }
}