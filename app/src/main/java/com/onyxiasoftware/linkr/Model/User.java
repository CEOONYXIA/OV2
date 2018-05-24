package com.onyxiasoftware.linkr.Model;

/**
 * Created by Ramon on 4/18/2018.
 */

public class User {

    private String Name, Password, Phone;

    public User(){

    }

    public User(String name, String password) {
        this.Name = name;
        this.Password = password;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
