package com.orlovskiy.demowebshop.model;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;

    public String firstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String lastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String email() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String password() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String gender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
