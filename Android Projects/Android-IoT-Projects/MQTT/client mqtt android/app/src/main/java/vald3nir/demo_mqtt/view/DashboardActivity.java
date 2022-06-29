package vald3nir.demo_mqtt.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;

import vald3nir.demo_mqtt.R;

public class DashboardActivity extends AppCompatActivity {

    private TextView distanciaTextview, logTextview, statusLedAmarelo, statusLedVerde;
    private DashboardDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        delegate = new DashboardDelegate(this);
        initViews();
    }

    private void initViews() {

        distanciaTextview = findViewById(R.id.distancia_textview);
        logTextview = findViewById(R.id.log_textview);
        statusLedAmarelo = findViewById(R.id.status_led_amarelo);
        statusLedVerde = findViewById(R.id.status_led_verde);


        ((Switch) findViewById(R.id.switch_led_amarelo)).setOnCheckedChangeListener((compoundButton, status) -> {
            delegate.mudarValorLedAmarelo(status);
            statusLedAmarelo.setText(status ? "On" : "Off");
        });

        ((Switch) findViewById(R.id.switch_led_verde)).setOnCheckedChangeListener((compoundButton, status) -> {
            delegate.mudarValorLedVerde(status);
            statusLedVerde.setText(status ? "On" : "Off");
        });
    }

    public void escreverLog(String texto) {
        logTextview.append(texto + "\n");
    }

    public void atualizarDistancia(String distancia) {
        distanciaTextview.setText(distancia);
    }
}
