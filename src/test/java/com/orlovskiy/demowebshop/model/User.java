package com.orlovskiy.demowebshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public @Data class User {
    private String firstName, lastName, email, password, gender;
}
