package br.com.interaje.busmap.webservices.services;

import android.app.ProgressDialog;

/**
 * Created by rayquaza on 17/05/16.
 */
public interface ApiStrans {

    void signIn(String email, String password);

    void getLines(String searchParam, ProgressDialog dialog);

}
