package com.example.ip_demo1.model;

import android.util.Log;

import com.example.ip_demo1.controller.SelectedChatJ;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;



public final class WebSocketEcho extends WebSocketListener {
    private WebSocket webSocket;

    private SelectedChatJ selectedChatJactivity;

    public WebSocketEcho(SelectedChatJ selectedChatJactivity) {
        this.selectedChatJactivity = selectedChatJactivity;
    }

    public void setWebSocket(WebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public void sendMessage(String message) {
        webSocket.send(message);
        //selectedChatJactivity.output("sending message");
    }

    public void closeConnection() {
        webSocket.close(1000, "Goodbye !");
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // WebSocket connection opened
        Log.d("MyTag","onOpen");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.d("MyTag","MESSAGE: " + text);
        selectedChatJactivity.output(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        // Received binary message
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        Log.d("MyTag","CLOSE: " + code + " " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
        Log.d("MyTag","exception",t);
    }
}