package com.example.packagedelivery;


import android.os.AsyncTask;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Fetchdata extends AsyncTask<Void, Void, String> {

    String data = "";
    String singleparsed = "";
    String key;
    String phone;

    public Fetchdata(String key, String phone) {
        this.key=key;
        this.phone=phone;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            URL url;

            if(this.key.equals("new"))
                url = new URL("http://192.168.0.101:3000/sec_guard/" + this.phone + "/");
            else
                url = new URL("http://192.168.0.101:3000/sec_guard/search/" + this.phone + "/");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            int status = httpURLConnection.getResponseCode();

            if(status>=400)
                singleparsed = "Delivery " + httpURLConnection.getResponseMessage();
            else {

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                if(this.key.equals("new")) {

                    data = bufferedReader.readLine();

                    JSONObject JO = new JSONObject(data);

                    singleparsed = "Phone number :" + JO.get("phone")  + "\n\n" + "Product Id :" + JO.get("productid") + "\n\n" + "Ordered from : " + JO.get("orderedfrom") + "\n\n" + "Quantity :" + JO.get("quantity");
                }
                else{

                    String line="";
                    while(line!=null)
                    {
                        line = bufferedReader.readLine();
                        data = data+line;
                    }

                    System.out.println(data);

                    JSONArray JA = new JSONArray(data);

                    if(JA.length()==0)
                        singleparsed = "No delivery records for this number";
                    else {

                        for (int i = JA.length()-1; i>=0 ; i--) {
                            JSONObject JO = (JSONObject) JA.get(i);

                            singleparsed = singleparsed + "Phone number :" + JO.get("phone") + "\n\n" + "Product Id :" + JO.get("productid") + "\n\n" + "Ordered from : " + JO.get("orderedfrom") + "\n\n" + "Quantity :" + JO.get("quantity") + "\n\n" + "Delivery date :" + JO.get("datetime") + "\n\n\n\n";

                        }
                    }
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return singleparsed;
    }

}