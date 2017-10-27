package com.projects.personal.denis.testprojectcontactlist.models;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Contact {
    @Id
    private long id;
    private String first_name;
    private String second_name;
    private String address;
    private String phone_number;

    public Contact(){
    }

    public Contact( String first_name, String second_name, String address, String phone_number) {
        this.first_name = first_name;
        this.second_name = second_name;
        this.address = address;
        this.phone_number = phone_number;
    }

    public Contact(long id, String first_name, String second_name, String address, String phone_number) {
        this.id = id;
        this.first_name = first_name;
        this.second_name = second_name;
        this.address = address;
        this.phone_number = phone_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
