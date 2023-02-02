package com.orlovskiy.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersCrudModel {
    private String name, job, id, createdAt, updatedAt;
}
