package com.example.rummenigged.rocketchat;

import android.app.Application;
import android.util.Log;

import com.rocketchat.common.listener.ConnectListener;
import com.rocketchat.core.RocketChatAPI;

public class App extends Application implements ConnectListener{

    private static final String TAG = App.class.getName();
    public static RocketChatAPI client;

    private static String serverUrl = "ws://192.168.111.58:3000";
    
    @Override
    public void onCreate() {
        super.onCreate();
        client = new RocketChatAPI(serverUrl);
        client.setReconnectionStrategy(null);
        client.connect(this);
    }

    @Override
    public void onConnect(String sessionID) {
        Log.d(TAG, "onConnect: ");
    }

    @Override
    public void onDisconnect(boolean closedByServer) {
        Log.d(TAG, "onDisconnect: ");
    }

    @Override
    public void onConnectError(Exception websocketException) {
        Log.d(TAG, "onConnectError: " + websocketException.getMessage());
    }

}
