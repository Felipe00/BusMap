package br.com.interaje.busmap.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rayquaza on 16/05/16.
 */
public class Line {

    private Long id;
    private String code;
    private String description;
    private String lineOrigin;
    private String lineReturn;
    private Boolean circular;

    public Line() {

    }

    public Line (JSONObject object) throws JSONException {
        this.code = object.getString("CodigoLinha");
        this.description = object.getString("Denomicao");
        this.lineOrigin = object.getString("Origem");
        this.lineReturn = object.getString("Retorno");
        this.circular = object.getBoolean("Circular");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLineOrigin() {
        return lineOrigin;
    }

    public void setLineOrigin(String lineOrigin) {
        this.lineOrigin = lineOrigin;
    }

    public String getLineReturn() {
        return lineReturn;
    }

    public void setLineReturn(String lineReturn) {
        this.lineReturn = lineReturn;
    }

    public Boolean getCircular() {
        return circular;
    }

    public void setCircular(Boolean circular) {
        this.circular = circular;
    }
}
