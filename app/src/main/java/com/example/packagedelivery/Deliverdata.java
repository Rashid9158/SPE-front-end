package com.example.packagedelivery;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class Deliverdata {

    Context ctx;
    String phone, qr_detail;

    public Deliverdata(Context ctx, String phone, String qr_detail)
    {
        this.ctx = ctx;
        this.phone = phone;
        this.qr_detail = qr_detail;
    }

    public void delivery()
    {

        JSONObject json_detail = new JSONObject();

        try {
            json_detail.put("deliveredto", this.qr_detail);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url = "http://192.168.0.101:3000/sec_guard/search/" + this.phone + "/";

        final RequestQueue requestQueue = Volley.newRequestQueue(this.ctx);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, json_detail,

                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        requestQueue.stop();
                        // no response
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        requestQueue.stop();
                        // no errors
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

}
