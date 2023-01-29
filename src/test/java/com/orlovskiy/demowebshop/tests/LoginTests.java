package com.orlovskiy.demowebshop.tests;

import com.orlovskiy.demowebshop.TestBase;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.orlovskiy.demowebshop.specs.Specs.request;
import static com.orlovskiy.demowebshop.specs.Specs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class LoginTests extends TestBase {
    private String authorizationCookie;

    @Test
    void loginTest() {
        step("Fill login form and get request to login/Get authorization cookie", () -> {
            authorizationCookie = given()
                    .spec(request)
                    .formParam("Email", login)
                    .formParam("Password", password)
            .when()
                    .post("/login")
            .then()
                    .statusCode(302)
                    .extract()
                    .cookie("NOPCOMMERCE.AUTH");
        });
        step("Check correct redirect after login", () -> {
            Response response = given()
                    .spec(request)
                    .cookie("NOPCOMMERCE.AUTH", authorizationCookie)
                    .when()
                    .get("")
                    .then()
                    .spec(responseSpec)
                    .body("html.head.title", is("Demo Web Shop"))
                    .extract()
                    .response();

            String str = response.asString();
            Document doc = Jsoup.parse(str);

            String text = Objects.requireNonNull(doc.select("a:contains(tester667@gmail.com)")
                    .first()).text();
            assertThat(text).isEqualTo("tester667@gmail.com");
        });
    }

    @Test
    void loginWithOutEmailTest() {
        step("Set only password and post request to login", () -> {
            Response response =
                    given()
                            .spec(request)
                            .formParam("Password", password)
                            .when()
                            .post("/login")
                            .then()
                            .spec(responseSpec)
                            .extract()
                            .response();

            String str = response.asString();
            Document doc = Jsoup.parse(str);

            String errorText = Objects.requireNonNull(doc.select("li:contains(No customer account found)")
                    .first()).text();
            assertThat(errorText).isEqualTo("No customer account found");
        });
    }

    @Test
    void loginWithOutPasswordTest() {
        step("Set only login and post request to login", () -> {
            Response response =
                    given()
                            .spec(request)
                            .formParam("Email", login)
                            .when()
                            .post("/login")
                            .then()
                            .spec(responseSpec)
                            .extract()
                            .response();

            String str = response.asString();
            Document doc = Jsoup.parse(str);

            String text = Objects.requireNonNull(doc.select("li:contains(The credentials provided are incorrect)")
                    .first()).text();
            assertThat(text).isEqualTo("The credentials provided are incorrect");
        });
    }

    @Test
    void loginWithOutLoginAndPasswordTest() {
        step("Not set login, password and post request to login", () -> {
            Response response =
                    given()
                            .spec(request)
                            .when()
                            .post("/login")
                            .then()
                            .spec(responseSpec)
                            .extract()
                            .response();

            String str = response.asString();
            Document doc = Jsoup.parse(str);

            String errorText = Objects.requireNonNull(doc.select("li:contains(No customer account found)")
                    .first()).text();
            assertThat(errorText).isEqualTo("No customer account found");
        });
    }
}
