package com.example.packagedelivery;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.regex.Pattern;

public class Detail extends AppCompatActivity {

    EditText PHONE, ORDER;
    String phone, order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        PHONE = (EditText)findViewById(R.id.mobile);
        ORDER = (EditText)findViewById(R.id.order);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void submit(View view)
    {
        phone = PHONE.getText().toString();
        order = ORDER.getText().toString();

        if(isValidPhone(phone) && isValidOrder(order))
        {
            JSONObject json_detail = new JSONObject();

            try {
                json_detail.put("phone", phone);
                json_detail.put("orderedfrom", order);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "http://172.16.130.103:3000/sec_guard/";

            final RequestQueue requestQueue = Volley.newRequestQueue(Detail.this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json_detail,

                    new Response.Listener<JSONObject>()
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(Detail.this).create();

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                alertDialog.setTitle("Product Id: " + response.get("message").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            alertDialog.setMessage("Put this Product Id on delivery");

                            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(Detail.this, Detail.class));
                                }
                            });

                            PHONE.setText("");
                            ORDER.setText("");

                            alertDialog.show();

                            requestQueue.stop();
                        }
                    },

                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // no errors
                            requestQueue.stop();
                        }
                    }
            );
            requestQueue.add(jsonObjectRequest);

        }
        else if(!isValidPhone(phone))
        {
            Toast.makeText(getApplicationContext(),"Enter valid phone number",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please fill the details",Toast.LENGTH_SHORT).show();
        }
    }

    public void  cancel(View view)
    {
        startActivity(new Intent(this, MainActivity.class));
    }

    private boolean isValidPhone(String phone)
    {
        if(phone.length()==10 && !Pattern.matches("[a-zA-Z]+", phone))
            return true;

        return false;
    }

    private boolean isValidOrder(String order)
    {
        if(order.length()!=0)
            return true;

        return false;
    }
}
