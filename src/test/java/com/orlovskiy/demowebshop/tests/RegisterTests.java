package com.orlovskiy.demowebshop.tests;

import com.orlovskiy.demowebshop.TestBase;
import com.orlovskiy.demowebshop.TestData;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.orlovskiy.demowebshop.specs.Specs.request;
import static com.orlovskiy.demowebshop.specs.Specs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class RegisterTests extends TestBase {
    TestData testData = new TestData();
    private String requestVerificationToken;
    public String authorizationCookie;

    @BeforeEach
    void setUp() {
        step("Get RequestVerificationToken", () -> {
            requestVerificationToken = given()
                    .spec(request)
                    .when()
                    .get("/register")
                    .then()
                    .spec(responseSpec)
                    .body("html.head.title", is("Demo Web Shop. Register"))
                    .extract()
                    .cookie("__RequestVerificationToken");
        });
    }

    @Test
    void registerTest() {
        step("Fill form and register", () -> {
            authorizationCookie = given()
                    .spec(request)
                    .formParam("__RequestVerificationToken", requestVerificationToken)
                    .formParam("Gender", testData.gender)
                    .formParam("FirstName", testData.firstName)
                    .formParam("LastName", testData.lastName)
                    .formParam("Email", testData.email)
                    .formParam("Password", testData.password)
                    .formParam("ConfirmPassword", testData.password)
                    .formParam("register-button", "Register")
            .when()
                    .post("/register")
            .then()
                    .statusCode(302)
                    .extract()
                    .cookie("NOPCOMMERCE.AUTH");
        });
        step("Check correct redirect after register", () -> {
            Response response = given()
                    .spec(request)
                    .cookie("NOPCOMMERCE.AUTH", authorizationCookie)
                    .when()
                    .get("/registerresult/1")
                    .then()
                    .spec(responseSpec)
                    .body("html.head.title", is("Demo Web Shop. Register"))
                    .extract()
                    .response();

            String str = response.asString();
            Document doc = Jsoup.parse(str);

            String text = Objects.requireNonNull(doc.select("div:contains(Your registration completed)")
                    .first()).text();
            assertThat(text).isEqualTo("Your registration completed");
        });
    }

    @Test
    void registerWithOutGenderTest() {
        step("Fill form and register", () -> {
            given()
                    .spec(request)
                    .formParam("__RequestVerificationToken", requestVerificationToken)
                    .formParam("FirstName", testData.firstName)
                    .formParam("LastName", testData.lastName)
                    .formParam("Email", testData.email)
                    .formParam("Password", testData.password)
                    .formParam("ConfirmPassword", testData.password)
                    .formParam("register-button", "Register")
                    .when()
                    .post("/register")
                    .then()
                    .statusCode(302);
        });
    }
}
