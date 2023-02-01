package com.orlovskiy.reqres.models.lombok;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokResponseCreatedData {
    public String name, job, id, createdAt;
}
