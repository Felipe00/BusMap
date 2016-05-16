package br.com.interaje.busmap.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.model.Lines;
import br.com.interaje.busmap.model.Token;
import br.com.interaje.busmap.webservices.WebserviceStrans;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "STRANS_LOG";
    private EditText lineText;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineText = (EditText) findViewById(R.id.lineText);
        result = (TextView) findViewById(R.id.idResult);

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
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Token.key = json.getString("token");
                    Log.d("Token ->", Token.key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Status code: " + statusCode + "\n" + new String(responseBody));

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "Status: " + String.valueOf(statusCode));
            }
        });
    }

    public void getLines(View v) {
        String value = lineText.getText().toString();
        WebserviceStrans.addHeader("X-Auth-Token", Token.key);
        WebserviceStrans.get("/v1/linhas?busca=" + value, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray jsonArray = new JSONArray(new String(responseBody));
                    Lines lines = new Lines();
                    for (int i = 0; i < 1; i++) {
                        JSONObject object = new JSONObject(jsonArray.get(i).toString());
                        lines.setCode(object.getString("CodigoLinha"));
                        lines.setDescription(object.getString("Denomicao"));
                        lines.setLineOrigin(object.getString("Origem"));
                        lines.setLineReturn(object.getString("Retorno"));
                        lines.setCircular(object.getBoolean("Circular"));
                    }

                    String resultValue = "Nº: "+lines.getCode()
                            + "\nLetreiro: " + lines.getDescription();

                    result.setText(resultValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d(TAG, "Status code: " + statusCode + "\n" + new String(responseBody));
            }
        });
    }

}
