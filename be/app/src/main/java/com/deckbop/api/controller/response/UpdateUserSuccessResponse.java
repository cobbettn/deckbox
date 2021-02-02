package com.deckbop.api.controller.response;

public class UpdateUserSuccessResponse {

    Long user_id;
    String userName;
    String password;
    String email;

    public UpdateUserSuccessResponse(Long user_id, String userName, String password, String email) {
        this.user_id = user_id;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
