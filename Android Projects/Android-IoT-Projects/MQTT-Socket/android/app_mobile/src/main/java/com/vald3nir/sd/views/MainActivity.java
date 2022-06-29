package com.vald3nir.sd.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.vald3nir.sd.AppsConfig;
import com.vald3nir.sd.R;


public class MainActivity extends AppCompatActivity {

    private Delegate delegate;
    private RadioButton mqttRadioButton;
    private EditText log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch lightSwitch = findViewById(R.id.light_switch);
        Switch tvSwitch = findViewById(R.id.tv_switch);
        Switch pcSwitch = findViewById(R.id.pc_switch);
        Switch alarmSwitch = findViewById(R.id.alarm_switch);
        Switch airSwitch = findViewById(R.id.air_switch);

        mqttRadioButton = findViewById(R.id.mqtt_radioButton);

        addEventChangeValue(alarmSwitch, AppsConfig.TOPIC_CHANGE_ALARM);
        addEventChangeValue(airSwitch, AppsConfig.TOPIC_CHANGE_AIR);
        addEventChangeValue(lightSwitch, AppsConfig.TOPIC_CHANGE_LIGHT);
        addEventChangeValue(pcSwitch, AppsConfig.TOPIC_CHANGE_PC);
        addEventChangeValue(tvSwitch, AppsConfig.TOPIC_CHANGE_TV);

        log = findViewById(R.id.txt_log);
        findViewById(R.id.btn_clean).setOnClickListener(v -> log.setText(""));

        delegate = new Delegate();
        delegate.startServices(this);
    }

    @Override
    protected void onDestroy() {
        delegate.stopServices(this);
        super.onDestroy();
    }

    private String createMessage(boolean isOn) {
        return isOn ? getString(R.string.on) : getString(R.string.off);
    }

    private void addEventChangeValue(Switch aSwitch, String topic) {

        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (mqttRadioButton.isChecked()) {
                delegate.publishMessageMQTT(this, topic, createMessage(isChecked));

            } else {
                delegate.publishMessageSocket(this, topic, createMessage(isChecked));
            }
        });
    }

    public void writeLog(String response) {
        log.append(response + "\n");
    }
}
