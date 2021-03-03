package com.example.seminarskirad;

public class Pitanja {
    String pitanje;
    String odgovor1;
    String odgovor2;
    String odgovor3;
    String odgovor4;
    String tocanOdgovor;

    public Pitanja() {

    }

    public String getOdgovor4() {
        return odgovor4;
    }

    public void setOdgovor4(String odgovor4) {
        this.odgovor4 = odgovor4;
    }

    public Pitanja(String pitanje, String tocanOdgovor, String odgovor1, String odgovor2, String odgovor3, String odgovor4) {
        this.pitanje = pitanje;
        this.tocanOdgovor = tocanOdgovor;
        this.odgovor1 = odgovor1;
        this.odgovor2 = odgovor2;
        this.odgovor3 = odgovor3;
        this.odgovor4 = odgovor4;

    }

    public String getPitanje() {
        return pitanje;
    }

    public void setPitanje(String pitanje) {
        this.pitanje = pitanje;
    }

    public String getOdgovor1() {
        return odgovor1;
    }

    public void setOdgovor1(String odgovor1) {
        this.odgovor1 = odgovor1;
    }

    public String getOdgovor2() {
        return odgovor2;
    }

    public void setOdgovor2(String odgovor2) {
        this.odgovor2 = odgovor2;
    }

    public String getOdgovor3() {
        return odgovor3;
    }

    public void setOdgovor3(String odgovor3) {
        this.odgovor3 = odgovor3;
    }

    public String getTocanOdgovor() {
        return tocanOdgovor;
    }

    public void setTocanOdgovor(String tocanOdgovor) {
        this.tocanOdgovor = tocanOdgovor;
    }


}
