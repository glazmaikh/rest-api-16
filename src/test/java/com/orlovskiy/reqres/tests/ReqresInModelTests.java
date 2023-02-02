package com.orlovskiy.reqres.tests;

import com.orlovskiy.reqres.TestBase;
import com.orlovskiy.reqres.models.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.orlovskiy.reqres.Specs.request;
import static com.orlovskiy.reqres.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class ReqresInModelTests extends TestBase {

    @Test
    void getAllUsersNameTest() {
        AllUsersDataModel response = given()
                .spec(request)
                .formParam("page", 2)
                .when()
                .get("/users")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(AllUsersDataModel.class);

        List<UserDataModel> dataModels = response.getData();
        List<String> firstNames = dataModels.stream().map(UserDataModel::getFirstName).collect(Collectors.toList());
        assertThat(firstNames).contains("Lindsay", "Michael", "Tobias", "Byron", "George", "Rachel");

        assertThat(response.getPage()).isEqualTo(2);
        assertThat(response.getPerPage()).isEqualTo(6);
        assertThat(response.getTotal()).isEqualTo(12);
        assertThat(response.getTotalPages()).isEqualTo(2);
    }

    @Test
    void getSingleUserSupportTest() {
        SingleUserDataModel response = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(SingleUserDataModel.class);

        assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        assertThat(response.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .spec(request)
        .when()
                .get("/users/23")
        .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    void postNewUserTest() {
        UsersCrudModel newUser = new UsersCrudModel();
        newUser.setName("morpheus");
        newUser.setJob("leader");

        UsersCrudModel response = given()
                .spec(request)
                .body(newUser)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .extract().as(UsersCrudModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("leader");
        assertThat(response.getCreatedAt()).isNotNull();
    }

    @Test
    void updateUserTest() {
        UsersCrudModel newUser = new UsersCrudModel();
        newUser.setName("morpheus");
        newUser.setJob("zion resident");

        UsersCrudModel response = given()
                .spec(request)
                .body(newUser)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(UsersCrudModel.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("zion resident");
        assertThat(response.getUpdatedAt()).isNotNull();
    }

    @Test
    void postRegisterSuccessTest() {
        AuthModel data = new AuthModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        given()
                .spec(request)
                .body(data)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .body("id", is(4),
                        "token", is("QpwL5tke4Pnpja7X4"))
                .log().body();
    }

    @Test
    void postLoginSuccessTest() {
        AuthModel data = new AuthModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        given()
                .spec(request)
                .body(data)
                .when()
                .post("/register")
                .then()
                .spec(responseSpec)
                .body("id", is(4),
                        "token", is("QpwL5tke4Pnpja7X4"))
                .log().body();
    }
}
