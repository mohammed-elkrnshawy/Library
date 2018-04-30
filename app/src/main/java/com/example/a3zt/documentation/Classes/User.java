package com.example.a3zt.documentation.Classes;

import java.io.Serializable;

public class User implements Serializable {
    private String ID;
    private String Username;
    private String Password;
    private String Email;
    private String Uuid;
    private int Type;

    public User(String ID, String username, String password, String email,int type) {
        this.ID = ID;
        Username = username;
        Password = password;
        Email = email;
        Type=type;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
