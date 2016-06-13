package br.com.interaje.busmap.webservices.services;

import java.util.List;

import br.com.interaje.busmap.models.Line;

/**
 * Created by rayquaza on 17/05/16.
 */
public interface ApiStransResponse {

    void onFinishRequest(List<Line> lineList);

    void onFinishBusesRequest(Line line);
}
