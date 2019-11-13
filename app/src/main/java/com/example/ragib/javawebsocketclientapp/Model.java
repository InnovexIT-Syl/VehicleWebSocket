package com.example.ragib.javawebsocketclientapp;

import com.example.ragib.javawebsocketclientapp.SocketHelper.SocketClass;

import java.util.LinkedHashMap;
import java.util.List;

import tech.gusavila92.websocketclient.WebSocketClient;

/**
 * Created by Ragib on 06-Nov-19.
 */

public class Model {
    public static boolean DEBUG_MODE=true;//Set true to ease testing. Not necessary.
    public static String SHARED_PREF="MySharedPref121";//Shared Preference Name
    public static String IP_KEY="CurrentIP";
    public static String PORT_KEY="CurrentPort";
    public static String CURRENT_IP="localhost";//this is temporary. Sign in activity loads the previously set IP from memory
    public static String CURRENT_PORT="8080";//this is temporary. Sign in activity loads the previously set PORT from memory
    public static LinkedHashMap<protobuf.data.Vehimage.vehimageinfo,protobuf.data.Vehimage.vehinfomsgs> vehicleDataQueue=new LinkedHashMap<>();
    public static protobuf.data.Vehimage.vehimageinfo currentVehimageinfo;
    public static String currentVehicleNumber="";
    public static LinkedHashMap<String,Object> lookUpTable=new LinkedHashMap();
    public static SocketClass socketClass;

    public Model(){
    }
}
