package com.example.packagedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Pickup extends AppCompatActivity {

    EditText ROLL;
    String roll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickup_layout);

        ROLL = (EditText)findViewById(R.id.roll);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Can not deliver without ID or roll number !! ", Toast.LENGTH_SHORT).show();
    }

    public void su(View view)
    {
        roll = ROLL.getText().toString();

        if(isValidRoll(roll))
        {
            Intent myIntent = getIntent();

            String phone = myIntent.getStringExtra("mobile");

            Deliverdata dd = new Deliverdata(this, phone, roll);
            dd.delivery();

            Toast.makeText(getApplicationContext(), "Package Delivered", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, MainActivity.class));
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please enter valid roll number", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidRoll(String roll)
    {
        if(roll.length()!=0)
            return true;

        return false;
    }

}
