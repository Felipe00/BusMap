package br.com.interaje.busmap.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.models.Line;
import br.com.interaje.busmap.webservices.services.ApiStrans;
import br.com.interaje.busmap.webservices.services.ApiStransResponse;
import br.com.interaje.busmap.webservices.services.impl.ApiStransImpl;

public class BusLocationActivity extends AppCompatActivity implements View.OnClickListener, ApiStransResponse {

    private TextView line1, line2, line3, result;
    private ApiStrans apiStrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_location);

        line1 = (TextView) findViewById(R.id.linha01);
        line2 = (TextView) findViewById(R.id.linha02);
        line3 = (TextView) findViewById(R.id.linha03);
        result = (TextView) findViewById(R.id.resultBusLocation);

        line1.setOnClickListener(this);
        line2.setOnClickListener(this);
        line3.setOnClickListener(this);

        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Carregando Ônibus");
        dialog.show();
        apiStrans = new ApiStransImpl(this, dialog, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linha01:
                apiStrans.getBuses("0206");
                break;

            case R.id.linha02:
                break;

            case R.id.linha03:
                break;
        }
    }

    @Override
    public void onFinishRequest(List<Line> lineList) {
        // Deixar sem nada
    }

    @Override
    public void onFinishBusesRequest(Line line) {
        Log.d("Buses: ", line.getBuses().get(0).getCode());
    }
}
