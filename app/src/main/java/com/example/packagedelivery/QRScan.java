package com.example.packagedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.google.zxing.Result;


import java.util.concurrent.ExecutionException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScan extends AppCompatActivity implements ZXingScannerView.ResultHandler {


    ZXingScannerView zXingScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);

        zXingScannerView.setResultHandler(this);

        zXingScannerView.startCamera();

    }


    @Override
    public void handleResult(Result result) {

        String qr_detail = result.getText();
        String phone = qr_detail.substring(32, 42);
        String key = "new";

        String temp="";

        Fetchdata process = new Fetchdata(key, phone);

        try {
            temp = process.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent myIntent = new Intent(this, Display.class);
        myIntent.putExtra("value", temp);
        startActivity(myIntent);
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();

        zXingScannerView.stopCamera();
    }

}
