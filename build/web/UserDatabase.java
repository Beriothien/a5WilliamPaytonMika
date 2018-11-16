package com.gmail.paytongreenc;

import java.io.Serializable;
import java.util.List;
import javax.faces.model.SelectItem;

public class UserDatabase implements Serializable {

    private int id;
    private String name;
    private String lastname;
    private String password;
    private String email;
    private String phoneNumber;
    private String city;
    private String gender;
    private List<SelectItem> languages;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<SelectItem> getLanguages() {
        return languages;
    }

    public void setLanguages(List<SelectItem> languages) {
        this.languages = languages;
    }
 


}
