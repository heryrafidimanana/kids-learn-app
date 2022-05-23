package com.main.kids.model;

public class UserModel {

    private String username;
    private String email;
    private String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public UserModel(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
