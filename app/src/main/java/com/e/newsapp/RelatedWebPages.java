package com.e.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RelatedWebPages extends AppCompatActivity {

    WebView webview;
    String detailUrl;
    private static String TAG = "RelatedWebPages";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relatedwebpages);

        webview = (WebView) findViewById(R.id.webview);

        detailUrl = getIntent().getStringExtra("detailUrl");

        webview.getSettings().setJavaScriptEnabled(true);
        //    webview.clearCache(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        if(isNetworkAvailable()) {
            loadWebView(detailUrl);
        }else {
            Toast.makeText(getApplicationContext(), "Please check Your Internet Connection", Toast.LENGTH_LONG).show();

        }
    }


    private void loadWebView(String detailUrl){
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);

            }
        });
        webview.loadUrl(detailUrl);
    }

    //To check network connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
