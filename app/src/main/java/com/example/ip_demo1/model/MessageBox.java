package com.example.ip_demo1.model;

public class MessageBox {
    private String Continut;
    private String Data;
    private String sender_id;

    public MessageBox() {
    }

    public MessageBox(String continut, String data, String sender_id) {
        Continut = continut;
        Data = data;
        this.sender_id = sender_id;
    }

    public String getContinut() {
        return Continut;
    }

    public void setContinut(String continut) {
        Continut = continut;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
}
