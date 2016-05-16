package br.com.interaje.busmap.webservices;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by rayquaza on 15/05/16.
 */
public class VolleyRequest extends JsonObjectRequest {

    public VolleyRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map getHeaders() throws AuthFailureError {
        DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        String dateNow = format.format(new Date()) + " GMT";

        Map<String, String> object = new HashMap<String, String>();
        Map<String, Map<String, String>> headers = new HashMap<String, Map<String, String>>();
        try {
            object.put("Content-Type", "application/json");
            object.put("Accept-Language", "en");
            object.put("Date", dateNow);
            object.put("X-Api-Key", WebserviceStrans.API_KEY);
            headers.put("headers", object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return headers;
    }

}
