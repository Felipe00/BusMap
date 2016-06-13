package br.com.interaje.busmap.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rayquaza on 17/05/16.
 */
public class BusStop extends Location {

    private Long id;
    private String code;
    private String description;
    private String address;

    public BusStop(){}

    public BusStop (JSONObject object) throws JSONException {
        this.setCode(object.getString("CodigoParada"));
        this.setDescription(object.getString("Denomicao"));
        this.setAddress(object.getString("Endereco"));
        this.setLat(object.getDouble("Lat"));
        this.setLng(object.getDouble("Long"));
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
