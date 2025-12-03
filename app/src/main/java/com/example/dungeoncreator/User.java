package com.example.dungeoncreator;

public class User
{
    private int id;
    private String fname;
    private String lname;
    private String username;

    public User() {
        id = 0;
        fname = "";
        lname = "";
        username = "";
    }

    public User(int i, String f, String l, String u) {
        id = i;
        fname = f;
        lname = l;
        username = u;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }
}
