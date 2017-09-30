package com.example.tweeter;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
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
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        textIp.setText(ipAddress);

        btnSend = (Button) findViewById(R.id.sendDirect);
        serverIp = (EditText) findViewById(R.id.serverIp);
    }

    public void sendDirect(View v){
        SocketClient client = new SocketClient();
        client.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, serverIp.getText().toString(), "4444", chatMessage.getText().toString());
    }

    private void onFinishGetRequest(String result, int type){
        String message;
        if(type == 0){
            message = "ME: " + result;
        }else{
            message = "STRANGER: " + result;
        }
        allMessages.add(message);
        msgAdapter.notifyDataSetChanged();
    }

    private class SocketClient extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Socket socket = null;
            String message = params[2];

            try {
                socket = new Socket(params[0], Integer.parseInt(params[1]));
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
                pw.println(params[2]);
            }catch(Exception e) {
                e.printStackTrace();
            }

            return message;
        }

        protected void onPostExecute(String result){
            onFinishGetRequest(result, 0);
            chatMessage.setText("");
        }
    }

    private class SocketServer extends AsyncTask<Object, Object, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            ServerSocket serverSocket = null;
            boolean listening = true;
            try{
                serverSocket = new ServerSocket(4444);
                while (listening){
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String reader = in.readLine();

                    onFinishGetRequest(reader, 1);

                    socket.close();
                }
            } catch (IOException e){
                e.printStackTrace();
                System.exit(-1);
            }
            return null;
        }
    }
}
