package com.example.packagedelivery;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void create(View view) {
        startActivity(new Intent(this, Detail.class));
    }

    public void search(View view) {

        Intent myIntent = new Intent(this, Search.class);
        myIntent.putExtra("key", "new");
        startActivity(myIntent);
    }

    public void  scan(View view) {

        Intent myIntent = new Intent(this, QRScan.class);
        myIntent.putExtra("from", "qr");
        startActivity(myIntent);
    }

    public void find(View view) {

        Intent myIntent = new Intent(this, Search.class);
        myIntent.putExtra("key", "old");
        startActivity(myIntent);
    }
}
