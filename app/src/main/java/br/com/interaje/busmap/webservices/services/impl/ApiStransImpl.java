package br.com.interaje.busmap.webservices.services.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.interaje.busmap.models.Line;
import br.com.interaje.busmap.models.Token;
import br.com.interaje.busmap.webservices.WebserviceStrans;
import br.com.interaje.busmap.webservices.services.ApiStrans;
import br.com.interaje.busmap.webservices.services.ApiStransResponse;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by rayquaza on 17/05/16.
 */
public class ApiStransImpl implements ApiStrans {

    private static final String TAG = "API_STRANS_IMPL";
    private Context mContext;
    private final ProgressDialog mDialog;
    private ApiStransResponse apiStransResponse;

    public ApiStransImpl(Context context, ProgressDialog dialog, ApiStransResponse response) {
        mContext = context;

        Token.key = "0248ddd3-70ed-49c5-bc24-7293eb177705";

        if (Token.key == null)
            signIn("fcostax02@gmail.com", "123456");

        mDialog = dialog;
        this.apiStransResponse = response;
    }

    @Override
    public void signIn(String email, String password) {
        StringEntity entity = WebserviceStrans.getBodyEntity(email, password);

        WebserviceStrans.initializeHeaders();

        WebserviceStrans.post(mContext, "/v1/signin", entity, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Token.key = json.getString("token");
                    Log.e(TAG, "Token gerado com sucesso");
                    mDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    mDialog.dismiss();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e(TAG, "Não foi possível fazer [SignIn] / Ocorrência: " + new String(responseBody));
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void getLines(String searchParam, final ProgressDialog dialog) {
        if (Token.key == null) {
            signIn("fcostax02@gmail.com", "123456");
        }

        final List<Line> listLines = new ArrayList<Line>();

        WebserviceStrans.addHeader("X-Auth-Token", Token.key);
        WebserviceStrans.get("/v1/linhas?busca=" + searchParam, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray jsonArray = new JSONArray(new String(responseBody));
                    for (int i = 0; i < 1; i++) {
                        JSONObject object = new JSONObject(jsonArray.get(i).toString());
                        listLines.add(new Line(object));
                    }
                    Log.e(TAG, "Linhas carregadas com sucesso");
                } catch (JSONException e) {
                    Log.e(TAG, "Não foi possível carregar a lista de Linhas");
                    e.printStackTrace();
                }
                dialog.dismiss();
                apiStransResponse.onFinishRequest(listLines);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.w(TAG, "Não foi possível acessar a API [Get/Linhas?busca]" + new String(responseBody));
                dialog.dismiss();
                apiStransResponse.onFinishRequest(listLines);
            }
        });
    }

    @Override
    public void getBuses(String codeBus) {
        if (Token.key == null) {
            signIn("fcostax02@gmail.com", "123456");
        }
        Log.d("Token ->>", Token.key);
        WebserviceStrans.addHeader("X-Auth-Token", Token.key);
        WebserviceStrans.get("/v1/veiculosLinha?busca=" + codeBus, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Log.d("-->>", new String(responseBody));
                    JSONObject object = new JSONObject(new String(responseBody));
                    Line line = new Line(object);
                    apiStransResponse.onFinishBusesRequest(line);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("-->>", new String(responseBody));
            }
        });
    }
}
