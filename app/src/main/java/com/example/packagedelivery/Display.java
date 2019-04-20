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

    static TextView TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_layout);

        TV = (TextView)findViewById(R.id.detail);
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

        if(detail.equals("Delivery Not Found"))
            Toast.makeText(getApplicationContext(), "Nothing to deliver", Toast.LENGTH_SHORT).show();
        else {

            String phone = detail.substring(14,24);
            System.out.println(phone);

            String url = "http://192.168.0.101:3000/sec_guard/" + phone + "/";

            final RequestQueue requestQueue = Volley.newRequestQueue(Display.this);

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,

                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(getApplicationContext(), "Package Delivered", Toast.LENGTH_SHORT).show();
                            requestQueue.stop();
                        }
                    },

                    new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // no errors in this project
                            requestQueue.stop();
                        }
                    }
            );
            requestQueue.add(stringRequest);
        }
        startActivity(new Intent(this, MainActivity.class));
    }


    public void ret(View view){

        Intent myIntent = new Intent(this, Search.class);
        myIntent.putExtra("key", "new");
        startActivity(myIntent);
    }
}
