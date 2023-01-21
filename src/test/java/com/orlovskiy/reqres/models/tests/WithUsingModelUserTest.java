package com.orlovskiy.reqres.models.tests;

import com.orlovskiy.reqres.models.UserData;
import org.junit.jupiter.api.Test;

import static com.orlovskiy.reqres.Specs.request;
import static com.orlovskiy.reqres.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithUsingModelUserTest {

    @Test
    void singleUser() {
        given()
                .spec(request)
        .when()
                .get("/users/2")
        .then()
                .spec(responseSpec)
                .log().body();
    }

    @Test
    void singleUserWithModel() {
        UserData data = given()
                    .spec(request)
                .when()
                    .get("/users/2")
                .then()
                    .spec(responseSpec)
                    .log().body()
                    .extract().as(UserData.class);
        assertEquals(2, data.getData().getId());
    }
}
