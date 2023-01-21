package com.orlovskiy.reqres;

import org.junit.jupiter.api.Test;

import static com.orlovskiy.reqres.Specs.request;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresInHomeWorkGroovyTest {

    @Test
    void getAllUsersMoreThan2002YearUsingGroovyTest() {
        given()
                .spec(request)
        .when()
                .get("/unknown")
        .then()
                .log().body()
                .body("data.findAll{it.year > 2002}.year", hasItems(2003, 2004, 2005));
    }

    @Test
    void getAllNamesUsingGroovyDelayTest() {
        given()
                .spec(request)
        .when()
                .get("/users?delay=3")
        .then()
                .time(lessThan(4000L))
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.findAll{it.first_name =~/[a-zA-Z]/}.first_name", hasItems("George",
                        "Janet", "Emma", "Eve", "Charles", "Tracey"));
    }

    @Test
    void getEmailUsingGroovyTest() {
        given()
                .spec(request)
        .when()
                .get("/users")
        .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()", hasItem("eve.holt@reqres.in"));
    }
}
