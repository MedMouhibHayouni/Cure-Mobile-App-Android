package com.example.cure.consultation;

public class Contact {

    private String name;
    private String phn;
    private String spec;
    private String photo;
    private String mail;

    public Contact() {

    }

    public Contact(String name, String phn,String spec, String photo) {
        this.name = name;
        this.phn = phn;
        this.spec=spec;
        this.photo = photo;
        this.mail=mail;
    }

    public String getName() {
        return name;
    }

    public String getPhn() {
        return phn;
    }

    public String getSpec(){return spec;}

    public String getPhoto() { return photo; }

    public String getMail() {
        return mail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhn(String phn) {
        this.phn = phn;
    }

    public void setSpec(String spec){ this.spec=spec;}

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setMail(String mail) { this.mail = mail; }
}
