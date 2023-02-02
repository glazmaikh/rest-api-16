package com.orlovskiy.reqres.tests;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresInExtendedTest {

//    @Test
//    void loginWithBadPracticeTest() {
//        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // todo move to model
//
//        given()
//                .log().uri()
//                .log().headers()
//                .log().body()
//                .contentType(JSON)
//                .body(data)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .log().body()
//                .log().status()
//                .statusCode(200)
//                .body("token", is("QpwL5tke4Pnpja7X4"));
//    }
//
//    @Test
//    void loginWithLombokModelTest() {
//        LoginBodyLombokModel data = new LoginBodyLombokModel();
//        data.setEmail("eve.holt@reqres.in");
//        data.setPassword("cityslicka");
//
//        LoginResponseLombokModel response = given()
//                .log().uri()
//                .log().headers()
//                .log().body()
//                .contentType(JSON)
//                .body(data)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .log().body()
//                .log().status()
//                .statusCode(200)
//                .extract().as(LoginResponseLombokModel.class);
//
//        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
//    }
//
//    @Test
//    void loginWithAllureTest() {
//        LoginBodyLombokModel data = new LoginBodyLombokModel();
//        data.setEmail("eve.holt@reqres.in");
//        data.setPassword("cityslicka");
//
//        LoginResponseLombokModel response = given()
//                .log().uri()
//                .log().headers()
//                .log().body()
//                .filter(new AllureRestAssured())
//                .contentType(JSON)
//                .body(data)
//                .when()
//                .post("https://reqres.in/api/login")
//                .then()
//                .log().body()
//                .log().status()
//                .statusCode(200)
//                .extract().as(LoginResponseLombokModel.class);
//
//        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
//    }
}
