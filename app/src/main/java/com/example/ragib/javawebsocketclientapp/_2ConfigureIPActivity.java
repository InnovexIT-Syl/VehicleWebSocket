package com.example.ragib.javawebsocketclientapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;

import static android.content.Context.MODE_PRIVATE;

public class _2ConfigureIPActivity extends AppCompatActivity implements SocketClass.SocketInterface{
    EditText ipET,portET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__2_configure_ip);
        setUp();
        attachWebSocketToThisActivity();
    }
    private void attachWebSocketToThisActivity(){
        Model.socketClass.changeInterface(this);
    }

    private void setUp() {
        getSupportActionBar().setTitle("Configure End Points");//setting up title name
        //getting references of the views
        ipET=findViewById(R.id.editText2);
        portET=findViewById(R.id.editText);
        //setting the ip and port from memory (if any is stored)
        SharedPreferences sharedPreferences=getSharedPreferences(Model.SHARED_PREF,MODE_PRIVATE);
        String ip=sharedPreferences.getString(Model.IP_KEY,"");
        String port=sharedPreferences.getString(Model.PORT_KEY,"");
        if(!ip.equals(""))
            ipET.setText(ip);
        if(!port.equals(""))
            portET.setText(port);

    }

    public void onClickSetConfiguration(View view){
        SharedPreferences.Editor shEditor=getSharedPreferences(Model.SHARED_PREF,MODE_PRIVATE).edit();
        String changedIp=ipET.getText().toString();
        String changedPort=portET.getText().toString();
        shEditor.putString(Model.PORT_KEY,changedPort);
        shEditor.putString(Model.IP_KEY,changedIp);
        shEditor.commit();
        finish();
    }

    @Override
    public void onTextReceived(String msg) {

    }

    @Override
    public void onBytesReceived(byte[] bytes) {

    }
}
