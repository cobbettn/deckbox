package com.deckbop.api.controller.response;

import java.util.List;

public class UserLoginErrorResponse {
    List<String> errorList;

    public UserLoginErrorResponse(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }
}
