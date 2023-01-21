package com.orlovskiy.reqres;

import org.junit.jupiter.api.Test;

import static com.orlovskiy.reqres.Specs.request;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqresInHomeWorkTest {

    @Test
    void getUserFirstNameTest() {
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .log().status()
                .statusCode(200)
                .body("data.first_name", hasItem("Tobias"));
    }

    @Test
    void getSingleUserWithSoftAssertTest() {
        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2),
                        "data.email", is("janet.weaver@reqres.in"),
                        "data.first_name", is("Janet"),
                        "data.last_name", is("Weaver"));
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .spec(request)
        .when()
                .get("/users/23")
        .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void postCreateNewUserTest() {
        String data = "{ \"name\": \"morpheus\", " +
                "\"job\": \"leader\" }";
        given()
                .spec(request)
                .body(data)
        .when()
                .post("/users")
        .then()
                .log().body()
                .log().status()
                .statusCode(201)
                .body("name", is("morpheus"), "job", is("leader"));
    }

    @Test
    void putCreateNewUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .spec(request)
                .body(data)
        .when()
                .put("/users/2")
        .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("name", is("morpheus"), "job", is("zion resident"));
    }

    @Test
    void deleteUserTest() {
        given()
                .spec(request)
        .when()
                .log().body()
                .delete("/users/2")
        .then()
                .log().body()
                .log().status()
                .statusCode(204);
    }

    @Test
    void postRegisterTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .spec(request)
                .body(data)
        .when()
                .post("/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void postRegisterUnsuccessfulTest() {
        given()
                .spec(request)
                .body("{ \"email\": \"sydney@fife\" }")
                .when()
                .post("/login")
                .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void postSuccessfulLoginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .spec(request)
                .body(data)
        .when()
                .post("/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("token", notNullValue());
    }

    @Test
    void postUnsuccessfulLoginTest() {
        String data = "{ \"email\": \"eve.holt@reqres.in\" }";

        given()
                .spec(request)
                .body(data)
        .when()
                .post("/login")
        .then()
                .log().body()
                .log().status()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void getDelayTest() {
        given()
                .spec(request)
        .when()
                .get("/users?delay=3")
        .then()
                .time(lessThan(4000L))
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.id", hasItems(1, 2, 3, 4, 5, 6));
    }
}
