package com.example.ragib.javawebsocketclientapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;
/*
Notes: For now _4ScreenTwoActivity is not called. You can call it when alert is found. Then you can get the 'to be processed'
vehiimageinfo from Model.vehicleDataQueue and sent it as parameter to the nextActivity function.
 */

public class _3ScreenOneActivity extends AppCompatActivity implements SocketClass.SocketInterface{
    TextView messageTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__3_screen_one);
        setUp();

        //testStart();//this is for testing, please ignore this function.

        //implement the receiving portion here
        //after connection is made, store the look up table data in Model.lookUpTable, vehicle number as key, object as value
        //store the received image in Model.vehicleDataQueue with protobuf.data.Vehimage.vehimageinfo as key and protobuf.data.Vehimage.vehinfomsgs as value
        //whenever alert is detected call nextActivity(info) function to transfer to the verification screen, where info is the current alerts vehimageinfo

        attachWebSocketToThisActivity();
    }
    private void attachWebSocketToThisActivity(){
        Model.socketClass.changeInterface(this);
    }

    private void setUp() { //setup views
        getSupportActionBar().setTitle("Message Activity");

        messageTV=findViewById(R.id.textView);
    }

    private void nextActivity(protobuf.data.Vehimage.vehimageinfo info){
        /*
        call this function when alert is found, get the to be processed info from Model.vehicleDataQueue and sent this as the
        input parameter info.
         */
        Model.currentVehimageinfo=info;
        Intent intent=new Intent(getApplicationContext(),_4ScreenTwoActivity.class);
        startActivity(intent);
    }

    public void testStart(){
        Intent intent=new Intent(getApplicationContext(),_1SignInActivity.class);
        startActivity(intent);
    }

    @Override
    public void onTextReceived(String msg) {

    }

    @Override
    public void onBytesReceived(byte[] bytes) {

    }
}
