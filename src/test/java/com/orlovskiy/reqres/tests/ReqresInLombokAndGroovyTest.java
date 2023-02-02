package com.orlovskiy.reqres.tests;

import com.orlovskiy.demowebshop.TestData;
import com.orlovskiy.reqres.models.lombok.LombokCreatedRequestData;
import com.orlovskiy.reqres.models.lombok.LombokResponseCreatedData;
import com.orlovskiy.reqres.models.lombok.LombokUserData;
import org.junit.jupiter.api.Test;

import static com.orlovskiy.reqres.Specs.request;
import static com.orlovskiy.reqres.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class ReqresInLombokAndGroovyTest {
    TestData testData = new TestData();

    @Test
    void getSingleUserWithLombok() {
        LombokUserData data = given()
                .spec(request)
        .when()
                .get("/users/2")
        .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(LombokUserData.class);
        assertThat(data.getData().getId()).isEqualTo(2);
        assertThat(data.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(data.getData().getFirstName()).isEqualTo("Janet");
        assertThat(data.getData().getLastName()).isEqualTo("Weaver");
    }

    @Test
    void setCreateWithLombok() {
        LombokCreatedRequestData data = new LombokCreatedRequestData();
        data.setName(testData.firstName);
        data.setJob("leader");

        LombokResponseCreatedData response = given()
                .spec(request)
                .body(data)
        .when()
                .post("/api/users")
        .then()
                .statusCode(201)
                .log().body()
                .extract().as(LombokResponseCreatedData.class);
        assertThat(response.getName()).isEqualTo(testData.firstName);
        assertThat(response.getJob()).isEqualTo("leader");
    }

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
                .time(lessThan(7000L))
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
