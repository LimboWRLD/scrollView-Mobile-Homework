package com.example.scrollview;

public class Kontakt
{
    private String ime;

    private String prezime;

    private String tel;

    private String skype;

    public String getIme() {
        return ime;
    }

    public Kontakt(String ime, String prezime, String tel, String skype) {
        this.ime = ime;
        this.prezime = prezime;
        this.tel = tel;
        this.skype = skype;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Override
    public String toString() {
        return "Kontakt{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", tel='" + tel + '\'' +
                ", skype='" + skype + '\'' +
                '}';
    }
}
