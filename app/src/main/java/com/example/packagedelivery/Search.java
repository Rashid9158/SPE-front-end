package com.example.packagedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Search extends AppCompatActivity{

    EditText PHONE;
    static String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        PHONE = (EditText)findViewById(R.id.getPhone);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void ok(View view) {

        phone = PHONE.getText().toString();

        if(isValidPhone(phone)) {

            Intent intent = getIntent();
            String key = intent.getStringExtra("key");

            if (key.equals("new"))
                startActivity(new Intent(this, Display.class));
            else
                startActivity(new Intent(this, Record.class));

            Fetchdata process = new Fetchdata(key);
            process.execute();
        }
        else
            Toast.makeText(getApplicationContext(),"Enter valid phone number",Toast.LENGTH_SHORT).show();


    }

    public void back(View view) {

        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean isValidPhone(String phone)
    {
        if(phone.length()==10 && !Pattern.matches("[a-zA-Z]+", phone))
            return true;

        return false;
    }
}
