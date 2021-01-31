package com.deckbop.api.controller.response;

import java.util.List;

public class UserRegisterErrorResponse {
    List<String> errorList;

    public UserRegisterErrorResponse(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
