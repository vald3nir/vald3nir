package vald3nir.android_snmp.agent.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import vald3nir.android_snmp.R;

public class AgentActivity extends AppCompatActivity {

    private TextView log;
    private AgentDelegate delegate;


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        delegate = new AgentDelegate(this);

        this.log = findViewById(R.id.log);
        TextView ipTextView = findViewById(R.id.ipTextview);
        TextView port = findViewById(R.id.portaTextview);

        delegate.fillAddressView(ipTextView, port);
    }


    public void clearLog(View view) {
        log.setText("");
    }


    public void writeLog(final String s) {
        runOnUiThread(() -> log.append(s + "\n"));
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            delegate.listenManagers();
            delegate.startServiceTrap();
        }, 1000);
    }

}
