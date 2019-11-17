package com.example.ragib.javawebsocketclientapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import protobuf.data.Vehimage;

/*
Notes: The app will start from this activity. For now MainActivity is of no work. The communication part of MainActivity will be
implemented in _3ScreenOneActivity, which will get message from the server.
Initialize websocket (it is not implemented for now) in this activiy as public static, so the same websocket can be used at all activities by just calling
_1SignInActivity.websocket.
 */
public class _1SignInActivity extends AppCompatActivity implements SocketClass.SocketInterface{
    EditText nameET,passwordET;
    Button configureBt,signInBt;
    private Map<String, Vehimage.regvehinfo> mRegisterVehicleDictionary ;
    private List dummRegDictionary;
    int flag = 0;
    SocketClass.SocketInterface socketInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__1_sign_in);
        mRegisterVehicleDictionary= new HashMap<>();
        dummRegDictionary= new ArrayList();

        someDummyRegData();

        setUp();
        createSocket();
        new NetworkThread().start();
        /*initializing Model.socketClass only once in this activity
        the attached interface will receive message from the class
        */

    }

    private void someDummyRegData() {
        dummRegDictionary.add("KJHFGFH");
        dummRegDictionary.add("HJGFFKHJ");
        dummRegDictionary.add("JT23TYU");
        dummRegDictionary.add("KJHGH87");
        dummRegDictionary.add("KJVHGK7");
        dummRegDictionary.add("KJHGFDS6");
        dummRegDictionary.add("567JHGHJ");
    }

    private void createSocket() {
        //you can use Model.socketClass.sendToWebsocket(msg) to send message to server
        //you are receiving the received data from websocket by using the interface functions onTextReceived(msg),onBytesReceived(bytes)
        //this is very convenient to use for multiple activity
        Model.socketClass=new SocketClass(socketInterface);
    }

    private void attachWebSocketToThisActivity(){//it is already attached when createSocket() was called
        Model.socketClass.changeInterface(this);
    }

    public void testStart(){
        Intent intent=new Intent(getApplicationContext(),_4ScreenTwoActivity.class);
        startActivity(intent);
    }

    private void setUp() { //setup view

        socketInterface=this;//SocketInterface attached to the activity
        // getting the references of the views
        nameET=findViewById(R.id.editTextName);
        passwordET=findViewById(R.id.editTextPassword);
        configureBt=findViewById(R.id.configureBtn);
        signInBt=findViewById(R.id.buttonSignUp);

        // using share preference to load previously set ip and port. saving the data in Model.java variables
        SharedPreferences sharedPreferences=getSharedPreferences(Model.SHARED_PREF,MODE_PRIVATE);

        Model.CURRENT_IP=sharedPreferences.getString(Model.IP_KEY,""); //getting the previously set IP
        Model.CURRENT_PORT=sharedPreferences.getString(Model.PORT_KEY,"");//getting the previously set Port

    }

    public void onClickConfigure(View view){
        startActivity(new Intent(getApplicationContext(),_2ConfigureIPActivity.class));
    }

    public void onClickSignIn(View view){
        String name=nameET.getText().toString();
        String password=passwordET.getText().toString();
        if(Model.DEBUG_MODE){//will only run in when Model.DEBUG_MODE is true
            //String NetworkThread. This thread will verify the user and start next activity.
            new NetworkThread().start();
        }
        else if((!name.equals("") && !password.equals(""))){
            //String NetworkThread. This thread will verify the user and start next activity.
            new NetworkThread().start();

        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"Don't leave the input field empty",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onTextReceived(String msg) {

    }

    @Override
    public void onBytesReceived(byte[] bytes) {
//        Log.i("WebSocket", "ByteMessage received");
//        try {
//            protobuf.data.Vehimage.vehinfomsgs ab =
//                    protobuf.data.Vehimage.vehinfomsgs.parseFrom(bytes);
//            final String message;
//            if (ab.getTestOneofCase().getNumber()==2)
//            {
//                final protobuf.data.Vehimage.vehimageinfo vm = ab.getVehinfo();
//                for (Vehimage.regvehinfo k : mRegisterVehicleDictionary.values()){
//                    if (vm.getVehiclenumber().equals(k.getVehiclenumber())){
//
//                    }
//                }
//
//            }
//            else if (ab.getTestOneofCase().getNumber()==1)
//
//            {
//                final protobuf.data.Vehimage.regvehicleinfolistAndUserList riu = ab.getRegvehlistUser();
//
//
//                for (Vehimage.regvehinfo k : riu.getRegvehinfolList()) {
//                    mRegisterVehicleDictionary.put(k.getVehiclenumber(),k);
//                    k.getVehicleid()
//                }
//
//                for (Vehimage.userinfo k : riu.getUserlistList()) {
//                    mRegisterVehicleDictionary.put(k.getVehiclenumber(),k);
//                }
//
//                message=null;
//            }
//            else
//            message=null;
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    class NetworkThread implements Runnable{
        Thread thread;
        private NetworkThread(){

        }

        @Override
        public void run() {
            //do networking processing here...
            Intent intent = getIntent();
            String ocrNumber = intent.getStringExtra("ocr");

            for(int i=0; i<dummRegDictionary.size();i++){
                if (dummRegDictionary.get(i).equals(ocrNumber)){
                    flag=1;
                }
            }
            if (flag==0)
                testStart();
            Model.socketClass=new SocketClass(socketInterface);
            //after verification start next activity
//            startActivity(new Intent(getApplicationContext(),_3ScreenOneActivity.class));
        }

        private void start(){
            if(thread==null){
                thread=new Thread(this,"");
                thread.start();
            }
        }
    }
}
