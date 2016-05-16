package br.com.interaje.busmap.webservices;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by rayquaza on 05/05/16.
 */
public class WebserviceStrans {

    public static final String API_KEY = "a15b7855de7f469db46a951798e5878d";

    public static final String BASE_URL = "https://api.inthegra.strans.teresina.pi.gov.br";
    private static final String TAG = "WebserviceStrans";

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Faz uma requisição do tipo GET para API
     * @param url
     * @param params
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setURLEncodingEnabled(true);
        client.setSSLSocketFactory(getSSLFactory());
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * Faz uma requisição do tipo POST para API
     * @param context
     * @param url
     * @param entity
     * @param responseHandler
     */
    public static void post(Context context, String url, StringEntity entity, AsyncHttpResponseHandler responseHandler) {
        client.setURLEncodingEnabled(true);
        client.setSSLSocketFactory(getSSLFactory());
        client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
    }

    /**
     * Converte a String em uma entidade Chave/Valor que o LoopJ entende.
     * @param body
     * @return
     */
    public static StringEntity getBodyEntity(String body) {
        StringEntity bodyEntity = null;
        try {
            bodyEntity = new StringEntity(body.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bodyEntity;
    }

    /**
     * Adiciona cabeçalhos à requisição.
     * @param key
     * @param value
     */
    public static void addHeader(String key, String value) {
        client.addHeader(key, value);
    }

    /**
     * Criando a estrutura do Body (corpo) do Json.
     * @param email
     * @param password
     * @return
     */
    public static String getBodyFormat(String email, String password) {
        return MessageFormat.format("{\"email\":\"{1}\", \"password\":\"{2}\"}", email, password);
    }

    /**
     * Retorna a data no padrão solicitado pela API.
     * @return
     */
    public static String getDateFormat() {
        DateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        return format.format(new Date()) + " GMT";
    }

    /**
     * Cria a url para fazer uma requisição.
     * @param relativeUrl
     * @return
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    /**
     * Metodo para validar o SSL do HTTPS.
     * @return
     */
    private static MySSLSocketFactory getSSLFactory() {
        MySSLSocketFactory sf = null;
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sf;
    }

}
