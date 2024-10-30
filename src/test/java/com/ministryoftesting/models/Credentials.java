package com.ministryoftesting.models;

public class Credentials {

    private int id;
    private String token;
    private boolean admin;

    public Credentials(String token, boolean admin) {
        this.id = id;
        this.token = token;
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public boolean getAdmin() {
        return admin;
    }

    public int getId() {
        return id;
    }

}
