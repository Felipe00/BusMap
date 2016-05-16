package br.com.interaje.busmap.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.webservices.WebserviceStrans;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "STRANS_LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        body:
                {
                "email": "user@strans.teresina.pi.gov.br",
                "password": "dEfGh369"
                }
        headers:
                {
                Content-Type: "application/json",
                Accept-Language: "en",
                Date: "Wed, 13 Apr 2016 12:07:37 GMT",
                X-Api-Key: "AbdEfGh369K1"
                }
        */

        /**
         * Adicionando Headers
         */

        WebserviceStrans.addHeader("Content-Type", "application/json");
        WebserviceStrans.addHeader("Accept-Language", "en");
        WebserviceStrans.addHeader("Date", WebserviceStrans.getDateFormat());
        WebserviceStrans.addHeader("X-Api-Key", WebserviceStrans.API_KEY);

        /**
         * Adicionando Body
         */

        String body = WebserviceStrans.getBodyFormat("fcostax02@gmail.com", "123456");
        StringEntity entity = WebserviceStrans.getBodyEntity(body);

        /**
         * Fazendo uma Requisição
         */

        WebserviceStrans.post(this, "/v1/signin", entity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d(TAG, "Status code: " + statusCode + "\n" + new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String string = new String(responseBody);
                Log.e(TAG, "Status: " + String.valueOf(statusCode));
            }
        });
    }

}
