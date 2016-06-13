package br.com.interaje.busmap.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.interaje.busmap.R;
import br.com.interaje.busmap.models.Line;
import br.com.interaje.busmap.webservices.services.ApiStrans;
import br.com.interaje.busmap.webservices.services.ApiStransResponse;
import br.com.interaje.busmap.webservices.services.impl.ApiStransImpl;

public class MainActivity extends AppCompatActivity implements ApiStransResponse {

    private static final String TAG = "STRANS_LOG";
    private EditText lineText;
    private TextView result;
    private ApiStrans apiStrans;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineText = (EditText) findViewById(R.id.lineText);
        result = (TextView) findViewById(R.id.idResult);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Aguarde");
        dialog.setMessage("Fazendo alguns ajustes");
        dialog.show();
        apiStrans = new ApiStransImpl(this, dialog, this);
    }

    public void getLines(View v) {
        String value = lineText.getText().toString();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Buscando por linhas");
        dialog.show();

        apiStrans.getLines(value, dialog);
    }

    @Override
    public void onFinishRequest(List<Line> lineList) {
        String content = "";
        if (lineList.size() != 0) {
            content = "Nº: " + lineList.get(0).getCode() + " - " + lineList.get(0).getDescription();
            result.setText(content);
        } else {
            content = "Nenhuma resultado encontrado ";
            result.setText(content);
        }
    }

    @Override
    public void onFinishBusesRequest(Line line) {

    }
}
