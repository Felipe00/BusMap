package br.com.interaje.busmap.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.webservices.VolleyRequest;
import br.com.interaje.busmap.webservices.WebserviceStrans;
import cz.msebera.android.httpclient.entity.StringEntity;

public class VolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        /**
         * Adicionando Body
         */

        JSONObject object = new JSONObject();
        JSONObject body = new JSONObject();
        try {
            object.put("email", "fcostax02@gmail.com");
            object.put("password", "123456");
            body.put("body", object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /**
         * Adicionando o Header


        object = new JSONObject();
        JSONObject headers = new JSONObject();
        try {
            object.put("Content-Type", "application/json");
            object.put("Accept-Language", "en");
            //object.put("Date", dateNow);
            object.put("X-Api-Key", WebserviceStrans.API_KEY);
            headers.put("headers", object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
        final String url = WebserviceStrans.BASE_URL + "/v1/signin";
        final JsonObjectRequest request = new VolleyRequest(
                Request.Method.POST,
                url, body,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            final JSONObject json = new JSONObject(response.toString());

                            Log.d("--->", json.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                // Tag
                Log.e("---#>", "erro volley");
            }
        }
        );

        Volley.newRequestQueue(getApplication()).add(request);

    }
}
