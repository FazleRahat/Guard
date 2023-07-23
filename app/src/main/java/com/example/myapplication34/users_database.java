package com.example.myapplication34;


public class users_database {

    String Name,Email,Password,ID;
    Integer Latt,Long;


    public users_database() {

    }

    public users_database(String name, String email, String password, String id) {
        Name = name;
        Email = email;
        Password = password;
        ID= id;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String id) {ID = id;}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
