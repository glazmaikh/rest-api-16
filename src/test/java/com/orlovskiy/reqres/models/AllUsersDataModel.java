package com.orlovskiy.reqres.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class AllUsersDataModel {
    public int page;

    @JsonProperty("per_page")
    public int perPage;

    public int total;

    @JsonProperty("total_pages")
    public int totalPages;
    private List<UserDataModel> data;
    private SupportModel support;

    public List<UserDataModel> getData() {
        return data;
    }

    public SupportModel getSupport() {
        return support;
    }
}
