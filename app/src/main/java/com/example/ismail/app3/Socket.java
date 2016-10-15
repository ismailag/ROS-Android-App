package com.example.ismail.app3;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.IOAcknowledge;
import io.socket.IOCallback;
import io.socket.SocketIOException;

public class Socket implements IOCallback{
    @Override
    public void onMessage(JSONObject json, IOAcknowledge ack) {
        try {
            System.out.println("Server said:" + json.toString(2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(String data, IOAcknowledge ack) {
        System.out.println("Server said: " + data);
    }

    @Override
    public void onError(SocketIOException socketIOException) {
        System.out.println("an Error occured");
        socketIOException.printStackTrace();
    }

    @Override
    public void onDisconnect() {
        System.out.println("Connection terminated.");
    }

    @Override
    public void onConnect() {
        System.out.println("Connection established");

    }

    @Override
    public void on(String event, IOAcknowledge ack, Object... args) {
        System.out.println("Server triggered event '" + event + "'");
    }
}
