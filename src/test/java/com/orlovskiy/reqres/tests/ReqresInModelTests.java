package com.orlovskiy.reqres.tests;

import com.orlovskiy.reqres.TestBase;
import com.orlovskiy.reqres.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Collectors;

import static com.orlovskiy.reqres.Specs.request;
import static com.orlovskiy.reqres.Specs.responseSpec;
import static com.orlovskiy.reqres.helpers.CustomApiListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@Tag("api")
public class ReqresInModelTests extends TestBase {

    @ValueSource(strings = {"Lindsay", "Michael", "Tobias", "Byron", "George", "Rachel"})
    @ParameterizedTest(name = "check FirstName {arguments} from all users list")
    void getAllUsersNameTest(String arg) {
        step("get all users test", () -> {
            AllUsersDataModel response = given()
                    .spec(request)
                    .filter(withCustomTemplates())
                    .formParam("page", 2)
                    .when()
                    .get("/users")
                    .then()
                    .spec(responseSpec)
                    .log().body()
                    .extract().as(AllUsersDataModel.class);
            step("get all users first names test", () -> {
                List<UserDataModel> dataModels = response.getData();
                List<String> firstNames = dataModels.stream().map(UserDataModel::getFirstName).collect(Collectors.toList());
                assertThat(firstNames).contains(arg);
            });
            step("get all counts test", () -> {
                assertThat(response.getPage()).isEqualTo(2);
                assertThat(response.getPerPage()).isEqualTo(6);
                assertThat(response.getTotal()).isEqualTo(12);
                assertThat(response.getTotalPages()).isEqualTo(2);
            });
        });
    }

    @Test
    @DisplayName("get support from user test")
    void getSingleUserSupportTest() {
        step("get support for single user test", () -> {
            SingleUserDataModel response = given()
                    .spec(request)
                    .filter(withCustomTemplates())
                    .when()
                    .get("/users/2")
                    .then()
                    .spec(responseSpec)
                    .log().body()
                    .extract().as(SingleUserDataModel.class);

            assertThat(response.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
            assertThat(response.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");
        });
    }

    @Test
    @DisplayName("get user with incorrect url test")
    void getSingleUserNotFoundTest() {
        step("get single user not found test", () -> {
            given()
                    .spec(request)
                    .filter(withCustomTemplates())
                    .when()
                    .get("/users/23")
                    .then()
                    .statusCode(404)
                    .log().body();
        });
    }

    @Test
    @DisplayName("post new user test")
    void postNewUserTest() {
        UsersCrudModel newUser = new UsersCrudModel();
        step("set data from model", () -> {
            newUser.setName("morpheus");
            newUser.setJob("leader");
        });
        step("get data new user test", () -> {
            UsersCrudModel response = given()
                    .spec(request)
                    .filter(withCustomTemplates())
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
        });
    }

    @Test
    @DisplayName("update user data test")
    void updateUserTest() {
        UsersCrudModel newUser = new UsersCrudModel();
        step("set data from user", () -> {
            newUser.setName("morpheus");
            newUser.setJob("zion resident");
        });
        step("get data from update user test", () -> {
            UsersCrudModel response = given()
                    .spec(request)
                    .filter(withCustomTemplates())
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
        });
    }

    @Test
    @DisplayName("success register user test")
    void postRegisterSuccessTest() {
        AuthModel data = new AuthModel();
        step("set data from model", () -> {
            data.setEmail("eve.holt@reqres.in");
            data.setPassword("pistol");
        });
        step("get success register user data test", () -> {
            given()
                    .spec(request)
                    .filter(withCustomTemplates())
                    .body(data)
                    .when()
                    .post("/register")
                    .then()
                    .spec(responseSpec)
                    .body("id", is(4),
                            "token", is("QpwL5tke4Pnpja7X4"))
                    .log().body();
        });
    }

    @Test
    @DisplayName("success login test")
    void postLoginSuccessTest() {
        AuthModel data = new AuthModel();
        step("set data from model", () -> {
            data.setEmail("eve.holt@reqres.in");
            data.setPassword("pistol");
        });
        step("get success login user data test", () -> {
            given()
                    .spec(request)
                    .filter(withCustomTemplates())
                    .body(data)
                    .when()
                    .post("/register")
                    .then()
                    .spec(responseSpec)
                    .body("id", is(4),
                            "token", is("QpwL5tke4Pnpja7X4"))
                    .log().body();
        });
    }
}
