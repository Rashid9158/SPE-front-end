package com.example.packagedelivery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class Display extends AppCompatActivity {

    TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_layout);

        TV = (TextView)findViewById(R.id.detail);

        Intent intent = getIntent();
        String value = intent.getStringExtra("value");
        TV.setText(value);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Search.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("key", "new");
        startActivity(intent);
    }

    public void go(View view){

        String detail = TV.getText().toString();

        if(detail.equals("Delivery Not Found")) {

            Toast.makeText(getApplicationContext(), "Nothing to deliver", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        else {

            String phone = detail.substring(14,24);

            String url = "http://172.16.130.103:3000/sec_guard/" + phone + "/";

            final RequestQueue requestQueue = Volley.newRequestQueue(Display.this);

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,

                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            requestQueue.stop();
                            // no response
                        }
                    },

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            requestQueue.stop();
                            // no errors
                        }
                    }
            );
            requestQueue.add(stringRequest);

            Intent myIntent = new Intent(this, QRScan.class);
            myIntent.putExtra("from", "bar");
            myIntent.putExtra("mobile", phone);
            startActivity(myIntent);
        }

    }


    public void ret(View view){

        Intent myIntent = new Intent(this, Search.class);
        myIntent.putExtra("key", "new");
        startActivity(myIntent);
    }
}
