package com.example.ip_demo1.model;

public class UserData {
    // Singleton instance
    private static UserData instance;



    // User data fields
    private Integer id;
    private String nume;
    private String prenume;
    private String varsta;
    private String cnp;
    private String numar_telefon;
    private String adresa_email;
    private String strada;
    private String oras;
    private String judet;
    private String tara;
    private String profesie;
    private String loc_munca;
    private  String parola;

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    // Private constructor to prevent instantiation
    private UserData() {
        // Initialize your user data here if needed
    }


    // Static method to get the singleton instance
    public static UserData getInstance() {
        if (instance == null) {
            instance = new UserData();
        }
        return instance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getVarsta() {
        return varsta;
    }

    public void setVarsta(String varsta) {
        this.varsta = varsta;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getNumar_telefon() {
        return numar_telefon;
    }

    public void setNumar_telefon(String numar_telefon) {
        this.numar_telefon = numar_telefon;
    }

    public String getAdresa_email() {
        return adresa_email;
    }

    public void setAdresa_email(String adresa_email) {
        this.adresa_email = adresa_email;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getJudet() {
        return judet;
    }

    public void setJudet(String judet) {
        this.judet = judet;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getProfesie() {
        return profesie;
    }

    public void setProfesie(String profesie) {
        this.profesie = profesie;
    }

    public String getLoc_munca() {
        return loc_munca;
    }

    public void setLoc_munca(String loc_munca) {
        this.loc_munca = loc_munca;
    }
}

