package com.example.ragib.javawebsocketclientapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ragib.javawebsocketclientapp.SearchTableView.ListAdapter;
import com.example.ragib.javawebsocketclientapp.SearchTableView.ListItem;
import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;
/*
Notes: This activity will get data using Model.vehicleDataQueue.get(Model.currentVehimageinfo)
The further processing by the user is not yet implemented. When it will be implemented you can use websocket.send to send
the data to server.
 */

public class _4ScreenTwoActivity extends AppCompatActivity implements ListAdapter.OnClickRecycleView,SocketClass.SocketInterface{
    Button okayBt;
    EditText editText1,editText2; //change these names according to the context
    ImageView imageView;
    PhotoViewAttacher photoViewAttacher; //for zoomable imageview
    RelativeLayout processingRelativeLayout; //this layout contains the 'processing..' layout. It will be
    // visible when processing, invisible otherwise.
    List<String> lookUpTableNumbers;//this is the table that needs to initialize in setup accordingly to the lookuptabel
    RecyclerView recyclerView;
    List<ListItem> listItems=new ArrayList<>();
    ListAdapter listAdapter;
    ListAdapter.OnClickRecycleView onClickRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__4_screen_two);
        setUp();//setup views

        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listItems=new ArrayList<>();
                listAdapter=new ListAdapter(listItems,onClickRecycleView);
                synchronized (recyclerView) {
                    recyclerView.setAdapter(listAdapter);
                }
//                Log.d("---","starting"+s.toString());
                int c=0;
                for(int i=0;i<lookUpTableNumbers.size() && c<5;i++){
                    String str=lookUpTableNumbers.get(i);
                    if(str.toLowerCase().contains(s.toString().toLowerCase())){
                        c+=1;
                        listItems.add(new ListItem(str));
                        listAdapter=new ListAdapter(listItems,onClickRecycleView);
                        synchronized (recyclerView) {
                            recyclerView.setAdapter(listAdapter);
                        }
                        Log.d("---","found "+s);
                    }
                }
                if(s.toString().equals("")){
                    listItems=new ArrayList<>();
                    listAdapter=new ListAdapter(listItems,onClickRecycleView);
                    synchronized (recyclerView) {
                        recyclerView.setAdapter(listAdapter);
                    }
                }
                if(s.toString().length()>0 && c==0){
                    listItems=new ArrayList<>();
                    listItems.add(new ListItem("Press to create new profile..."));
                    listAdapter=new ListAdapter(listItems,onClickRecycleView);
                    synchronized (recyclerView) {
                        recyclerView.setAdapter(listAdapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        attachWebSocketToThisActivity();
    }
    private void attachWebSocketToThisActivity(){
        Model.socketClass.changeInterface(this);
    }

    private void setUp() { //setup views


        onClickRecycleView=this;
        //Initialize the unique lookUpTableNumbers from Model.lookUpTable
        lookUpTableNumbers=new ArrayList<>();
        lookUpTableNumbers.add("Vha-01");
        lookUpTableNumbers.add("There are goose");
        //setting up recycleview
        recyclerView=findViewById(R.id.recycleView);

//        listItems.add(new ListItem("hello"));
        listAdapter=new ListAdapter(listItems,onClickRecycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(listAdapter);

        //
        okayBt=findViewById(R.id.okBtn);
        editText1=findViewById(R.id.vehicleMake);
        editText2=findViewById(R.id.vehicleClass);
        imageView=findViewById(R.id.imageView2);
        photoViewAttacher=new PhotoViewAttacher(imageView);

        /*
        //get the image bytes from the protobuf.data.Vehimage.vehinfomsgs
        //which can be accessed by:
        //                            protobuf.data.Vehimage.vehinfomsgs ab=Model.vehicleDataQueue.get(Model.currentVehimageinfo)
        // get bytes from ab
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);
        synchronized (photoViewAttacher){
            photoViewAttacher.notify();
        }
        */

        processingRelativeLayout=findViewById(R.id.default_relative_layout);
        //when ever processing data: processingLinearLayout.setVisibility(View.VISIBLE);
        //when finished processing data: processingLinearLayout.setVisibility(View.GONE);
    }

    public void onClickOkay(View view){
        startActivity(new Intent(getApplicationContext(), _1SignInActivity.class));
    }

    @Override
    public void onClickView(final int position) {

        if(listItems.get(position).getName().equals("Press to create new profile...")){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"pressed to create profile",Toast.LENGTH_SHORT).show();
                }
            });
            startActivity(new Intent(getApplicationContext(),_5CreateNewProfileActivity.class));
        }
        else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),position+" is pressed",Toast.LENGTH_SHORT).show();
                }
            });
            editText1.setText(listItems.get(position).getName());
        }
        listItems = new ArrayList<>();
        listAdapter = new ListAdapter(listItems, onClickRecycleView);
        synchronized (recyclerView) {
            recyclerView.setAdapter(listAdapter);
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

        }

        private void start(){
            if(thread==null){
                thread=new Thread(this,"");
                thread.start();
            }
        }
    }
}
