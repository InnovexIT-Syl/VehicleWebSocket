package com.example.ragib.javawebsocketclientapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;

public class _5CreateNewProfileActivity extends AppCompatActivity implements SocketClass.SocketInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__5_create_new_profile);
        setUp();
        attachWebSocketToThisActivity();
    }
    private void attachWebSocketToThisActivity(){
        Model.socketClass.changeInterface(this);
    }

    private void setUp() { //setup views
    }

    @Override
    public void onTextReceived(String msg) {

    }

    @Override
    public void onBytesReceived(byte[] bytes) {

    }

    public void onClickSendServer(View view) {
        finish();
    }
}
