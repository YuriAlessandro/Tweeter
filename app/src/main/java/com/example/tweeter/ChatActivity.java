package com.example.tweeter;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity {
    private TextView textIp;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textIp = (TextView) findViewById(R.id.deviceIp);

        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(WIFI_SERVICE);
        String ipAddress = String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
        textIp.setText(ipAddress);

        btnSend = (Button) findViewById(R.id.sendDirect);
    }
}
