package com.orlovskiy.demowebshop.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {
    public static RequestSpecification request = with()
            .baseUri("https://demowebshop.tricentis.com/")
            .log().all()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8");

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();
}
