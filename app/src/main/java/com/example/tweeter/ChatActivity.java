package com.example.tweeter;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    // RecyclerView
    private RecyclerView messagesRV;
    private RecyclerView.Adapter msgAdapter;
    private RecyclerView.LayoutManager msgLayoutManager;

    // UI Components
    private TextView textIp;
    private Button btnSend;
    private EditText chatMessage;
    private EditText serverIp;

    private List<String> allMessages = new ArrayList<String>();

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
        chatMessage = (EditText) findViewById(R.id.chatMessage);

        // Show device IP
        WifiManager wifiManager = (WifiManager) getApplicationContext()
                .getSystemService(WIFI_SERVICE);
        String ipAddress = String.valueOf(wifiManager.getConnectionInfo().getIpAddress());
        textIp.setText(ipAddress);

        btnSend = (Button) findViewById(R.id.sendDirect);
        serverIp = (EditText) findViewById(R.id.serverIp);
    }

    class SocketClient extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Socket socket = null;
            StringBuffer data = new StringBuffer();
            try{
                socket = new Socket(params[0], Integer.parseInt(params[1]));
                PrintWriter pw = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream()),
                        true);
                pw.println(params[2]);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String rawData;
                while ((rawData = br.readLine()) != null){
                    data.append(rawData);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data.toString();
        }

        protected void onPostExecute(String result){
            allMessages.add(result);
            msgAdapter.notifyDataSetChanged();
        }
    }

    public void sendDirect(View v){
        SocketClient client = new SocketClient();
        client.execute(serverIp.getText().toString(), "4444", chatMessage.getText() + "");
        chatMessage.setText("");
    }
}
