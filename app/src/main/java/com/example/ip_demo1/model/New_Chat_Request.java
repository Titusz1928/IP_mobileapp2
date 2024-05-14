package com.example.ip_demo1.model;

public class New_Chat_Request {
    private Integer idUser1;
    private Integer idUser2;

    public New_Chat_Request() {
    }

    public New_Chat_Request(Integer idUser1, Integer idUser2) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
    }

    @Override
    public String toString() {
        return "New_Chat_Request{" +
                "idUser1=" + idUser1 +
                ", idUser2=" + idUser2 +
                '}';
    }

    public Integer getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(Integer idUser1) {
        this.idUser1 = idUser1;
    }

    public Integer getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(Integer idUser2) {
        this.idUser2 = idUser2;
    }
}
