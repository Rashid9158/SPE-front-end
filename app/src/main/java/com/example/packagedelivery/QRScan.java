package com.example.packagedelivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.google.zxing.Result;



import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

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

        Intent intent = getIntent();
        String from = intent.getStringExtra("from");

        if (from.equals("qr")) {

            if (qr_detail != null && qr_detail.length() > 41 && isValidPhone(qr_detail.substring(32, 42))) {

                String phone = qr_detail.substring(32, 42);

                String key = "new";

                String temp = "";

                Fetchdata process = new Fetchdata(key, phone);

                try {
                    temp = process.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (temp.equals("Delivery Not Found")) {
                    Toast.makeText(getApplicationContext(), "No pending delivery ! Opening old records", Toast.LENGTH_LONG).show();

                    key = "old";
                    process = new Fetchdata(key, phone);

                    try {
                        temp = process.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Intent myIntent = new Intent(this, Record.class);
                    myIntent.putExtra("value", temp);
                    startActivity(myIntent);
                    finish();

                } else {

                    Intent myIntent = new Intent(this, Display.class);
                    myIntent.putExtra("value", temp);
                    startActivity(myIntent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Not a valid QR code", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        else
        {

            String phone = intent.getStringExtra("mobile");

            Deliverdata dd = new Deliverdata(this, phone, qr_detail);
            dd.delivery();

            Toast.makeText(getApplicationContext(), "Package Delivered", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        zXingScannerView.stopCamera();
    }

    @Override
    public void onBackPressed() {

        Intent myIntent = getIntent();
        String from = myIntent.getStringExtra("from");
        String phone = myIntent.getStringExtra("mobile");

        if(from.equals("bar"))
        {
            Intent intent = new Intent(this, Pickup.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("mobile", phone);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private boolean isValidPhone(String phone)
    {
        if(phone.length()==10 && !Pattern.matches("[a-zA-Z]+", phone))
            return true;

        return false;
    }

}
