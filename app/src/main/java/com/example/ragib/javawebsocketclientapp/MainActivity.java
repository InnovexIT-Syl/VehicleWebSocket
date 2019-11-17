package com.example.ragib.javawebsocketclientapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.google.protobuf.ByteString;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import protobuf.data.Vehimage;
import tech.gusavila92.websocketclient.WebSocketClient;


public class MainActivity extends AppCompatActivity {
    private WebSocketClient webSocketClient;
    private Map<String, Vehimage.regvehinfo> mRegisterVehicleDictionary ;
    private static Handler mainUIHandler;
    private static Handler bgThreadHandler;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_sounds);
        mRegisterVehicleDictionary= new HashMap<String, Vehimage.regvehinfo>();
        mainUIHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(),
                        "Random no " + (String) msg.obj + " from BGThread",
                        Toast.LENGTH_SHORT).show();
            }

        };
        createWebSocketClient();
    }

    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://10.0.2.2:9002");
            Log.i("WebSocket", "Session is building");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("Hello world!");
            }

            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                //protobuf.data.Vehimage.vehimageinfo ab = protobuf.data.Vehimage.vehimageinfo.parseFrom(ByteString.);
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            TextView textView = findViewById(R.id.animalSound);
                            textView.setText(message);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                Log.i("WebSocket", "ByteMessage received");
                try {
                    protobuf.data.Vehimage.vehinfomsgs ab = protobuf.data.Vehimage.vehinfomsgs.parseFrom(data);
                    //final String message;
                    if (ab.getTestOneofCase().getNumber()==2)
                    {
                        final protobuf.data.Vehimage.vehimageinfo vm = ab.getVehinfo();

                    }
                    else if (ab.getTestOneofCase().getNumber()==1)

                    {
                        final protobuf.data.Vehimage.regvehicleinfolistAndUserList riu = ab.getRegvehlistUser();


                        for (Vehimage.regvehinfo k : riu.getRegvehinfolList()) {
                            //mRegisterk.VehicleDictionary.put(k.getVehiclenumber(),k);
                            //k.getVehicleid()
                        }

                        for (Vehimage.userinfo k : riu.getUserlistList()) {
                            //mRegisterVehicleDictionary.put(k.getVehiclenumber(),k);
                        }

                        //message=null;
                    }
                    //else
                    //message=null;


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPingReceived(byte[] data) {}

            @Override
            public void onPongReceived(byte[] data) {}

            @Override
            public void onException(Exception e) {
                Log.i("WebSocket", "problem!"+e);
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Connection Closed ");
                System.out.println("onCloseReceived");
            }
        };


        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
    }

    public void sendMessage(View view) {
        Log.i("WebSocket", "Button was clicked");

        // Send button id string to WebSocket Server
        switch(view.getId()){
            case(R.id.dogButton):
                webSocketClient.send("1");
                break;

            case(R.id.catButton):
                webSocketClient.send("2");
                break;

            case(R.id.pigButton):
                webSocketClient.send("3");
                break;

            case(R.id.foxButton):
                webSocketClient.send("4");
                break;
        }
    }
}
