package com.example.packagedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Record extends AppCompatActivity {

    TextView RT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);

        RT = (TextView)findViewById(R.id.data);

        Intent intent = getIntent();
        String value = intent.getStringExtra("value");
        RT.setText(value);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Search.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("key", "old");
        startActivity(intent);
    }

    public void ba(View view){

        Intent myIntent = new Intent(this, Search.class);
        myIntent.putExtra("key", "old");
        startActivity(myIntent);
    }
}
