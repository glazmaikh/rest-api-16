package com.orlovskiy.reqres.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokUserData {
    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data  = data;
    }
}
