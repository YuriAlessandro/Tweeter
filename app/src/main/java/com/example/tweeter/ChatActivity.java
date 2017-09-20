package com.example.tweeter;

import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    // RecyclerView
    private RecyclerView messagesRV;
    private RecyclerView.Adapter msgAdapter;
    private RecyclerView.LayoutManager msgLayoutManager;

    // UI Components
    private TextView textIp;
    private Button btnSend;

    private List<String> allMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Some RecyclerView configurations
        messagesRV = (RecyclerView) findViewById(R.id.messages);
        messagesRV.setHasFixedSize(true);

        // Configure Layout Manager
        msgLayoutManager = new LinearLayoutManager(this);
        messagesRV.setLayoutManager(msgLayoutManager);

        // Configure Recycler View's Adapter
        msgAdapter = new MessageAdapter(allMessages);
        messagesRV.setAdapter(msgAdapter);

        textIp = (TextView) findViewById(R.id.deviceIp);

        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(WIFI_SERVICE);
        String ipAddress = String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
        textIp.setText(ipAddress);

        btnSend = (Button) findViewById(R.id.sendDirect);
    }
}
