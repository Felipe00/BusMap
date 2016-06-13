package br.com.interaje.busmap.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rayquaza on 17/05/16.
 */
public class Bus extends Location {

    private Long id;
    private String code;
    private String lastUpdate;

    public Bus(JSONObject object) throws JSONException {
        this.code = object.getString("CodigoVeiculo");
        this.setLat(object.getDouble("Lat"));
        this.setLng(object.getDouble("Long"));
        this.lastUpdate = object.getString("Hora");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
