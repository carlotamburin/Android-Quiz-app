package com.example.seminarskirad;

public class PitanjaRezultat {

    private String Email;
    private int score;

    public PitanjaRezultat() {

    }

    public PitanjaRezultat( String Email, int score) {

        this.Email = Email;
        this.score = score;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
