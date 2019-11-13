package com.example.ragib.javawebsocketclientapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;

/*
Notes: The app will start from this activity. For now MainActivity is of no work. The communication part of MainActivity will be
implemented in _3ScreenOneActivity, which will get message from the server.
Initialize websocket (it is not implemented for now) in this activiy as public static, so the same websocket can be used at all activities by just calling
_1SignInActivity.websocket.
 */
public class _1SignInActivity extends AppCompatActivity implements SocketClass.SocketInterface{
    EditText nameET,passwordET;
    Button configureBt,signInBt;

    SocketClass.SocketInterface socketInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__1_sign_in);
        setUp();
        testStart();

        createSocket();
        /*initializing Model.socketClass only once in this activity
        the attached interface will receive message from the class
        */

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
        getSupportActionBar().setTitle("Sign In"); //setting title

        socketInterface=this;//SocketInterface attached to the activity
        // getting the references of the views
        nameET=findViewById(R.id.editText2);
        passwordET=findViewById(R.id.editText);
        configureBt=findViewById(R.id.configureTextView);
        signInBt=findViewById(R.id.button3);

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

    }

    class NetworkThread implements Runnable{
        Thread thread;
        private NetworkThread(){

        }

        @Override
        public void run() {
            //do networking processing here...
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
