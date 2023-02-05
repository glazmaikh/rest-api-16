package com.orlovskiy.demowebshop.client;

import com.orlovskiy.demowebshop.model.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static com.orlovskiy.demowebshop.specs.Specs.request;
import static com.orlovskiy.demowebshop.specs.Specs.responseSpec;
import static io.restassured.RestAssured.given;

public class RegisterClient {
    public Response register() {
        // @formatter:off
        return given()
                .log().all()
                .when()
                .get("/register")
                .then()
                .log().all()
                .extract()
                .response();
        // @formatter:on
    }

    public Response createNewUser(String token, Map<String, String> cookies, User user) {
        // @formatter:off
        return given()
                .log().uri()
                .log().cookies()
                .log().headers()
                .cookies(cookies)
                .contentType(ContentType.URLENC)
                .formParam(
                        "__RequestVerificationToken",
                        token
                )
                .formParam(
                        "Gender",
                        user.gender()
                )
                .formParam(
                        "FirstName",
                        user.firstName()
                )
                .formParam(
                        "LastName",
                        user.lastName()
                )
                .formParam(
                        "Email",
                        user.email()
                )
                .formParam(
                        "Password",
                        user.password()
                )
                .formParam(
                        "ConfirmPassword",
                        user.password()
                )
                .formParam(
                        "register-button",
                        "Register"
                )
                .when()
                .post("/register")
                .then()
                .log().headers()
                .log().cookies()
                .log().body()
                .extract()
                .response();
        // @formatter:on
    }
}
