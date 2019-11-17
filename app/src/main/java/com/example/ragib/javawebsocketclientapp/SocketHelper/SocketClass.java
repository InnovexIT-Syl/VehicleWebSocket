package com.example.ragib.javawebsocketclientapp.SocketHelper;

import com.example.ragib.javawebsocketclientapp.Model;

import java.net.URI;
import java.net.URISyntaxException;

import tech.gusavila92.websocketclient.WebSocketClient;


/**
 * Created by Ragib on 07-Nov-19.
 */

public class SocketClass {
    public interface SocketInterface{
        void onTextReceived(String msg);
        void onBytesReceived(byte[] bytes);
    }
    SocketInterface socketInterface;

    WebSocketClient webSocketClient;
    public SocketClass(final SocketInterface socketInterface){
        this.socketInterface=socketInterface;

        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://10.0.2.2:9002");
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient=new WebSocketClient(uri) {
            @Override
            public void onOpen() {

            }

            @Override
            public void onTextReceived(String message) {
                socketInterface.onTextReceived(message);
            }

            @Override
            public void onBinaryReceived(byte[] data) {
                socketInterface.onBytesReceived(data);
            }

            @Override
            public void onPingReceived(byte[] data) {

            }

            @Override
            public void onPongReceived(byte[] data) {

            }

            @Override
            public void onException(Exception e) {

            }

            @Override
            public void onCloseReceived() {

            }
        };
    }

    public void sendToWebsocket(String str){
        webSocketClient.send(str);
    }
    public void changeInterface(SocketInterface socketInterface){
        this.socketInterface=socketInterface;
    }

}
