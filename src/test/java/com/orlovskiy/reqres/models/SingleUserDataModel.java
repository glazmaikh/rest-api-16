package com.orlovskiy.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class SingleUserDataModel {
    private UserDataModel data;
    private SupportModel support;

    public UserDataModel getData() {
        return data;
    }

    public SupportModel getSupport() {
        return support;
    }
}
