package com.orlovskiy.demowebshop.tests;

import com.orlovskiy.demowebshop.TestBase;
import com.orlovskiy.demowebshop.TestData;
import com.orlovskiy.demowebshop.client.RegisterClient;
import com.orlovskiy.demowebshop.config.AuthConfig;
import com.orlovskiy.demowebshop.model.User;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Objects;

import static com.orlovskiy.demowebshop.helpers.CustomApiListener.withCustomTemplates;
import static com.orlovskiy.demowebshop.specs.Specs.request;
import static com.orlovskiy.demowebshop.specs.Specs.responseSpec;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class RegisterTests extends TestBase {
    TestData testData = new TestData();
    private String requestVerificationToken;
    private String authorizationCookie;
    private static AuthConfig config;
    private static String login;
    private static String password;

    private final User user = new User();
    private final RegisterClient client = new RegisterClient();
    @Test
    void regTest() {
        Response extractResponseFromLogin = given()
                .formParam("Email", "tester667@gmail.com")
                .formParam("Password", "123456")
                .when()
                .post("/login")
                .then()
                .extract().response();
        String fromResponseFromLogin = extractResponseFromLogin.getCookie("NOPCOMMERCE.AUTH");
        System.out.println("NOPCOMMERCE.AUTH fromResponseFromLogin  !!!! " + fromResponseFromLogin);

        String extractCookieFromLogin = given()
                .formParam("Email", "tester667@gmail.com")
                .formParam("Password", "123456")
                .when()
                .post("/login")
                .then()
                .extract().cookie("NOPCOMMERCE.AUTH");
        System.out.println("NOPCOMMERCE.AUTH extractCookieFromLogin  !!!! " + extractCookieFromLogin);

        String tokenForRegister = given()
                .when()
                .get("/register")
                .then()
                .extract()
                .cookie("__RequestVerificationToken");

        Response extractResponseFromRegister = given()
                .formParam("__RequestVerificationToken", tokenForRegister)
                .formParam("Gender", "M")
                .formParam("FirstName", "testtesttesttest1")
                .formParam("LastName", "testtesttesttest1")
                .formParam("Email", "testtest1@gmail.com")
                .formParam("Password", "123123")
                .formParam("ConfirmPassword", "123123")
                .formParam("register-button", "Register")
                .when()
                .post("/register")
                .then()
                .extract().response();
        String fromResponseFromRegister = extractResponseFromRegister.getCookie("NOPCOMMERCE.AUTH");
        System.out.println("NOPCOMMERCE.AUTH fromResponseFromRegister  !!!! " + fromResponseFromRegister);

        String tokenForRegister1 = given()
                .when()
                .get("/register")
                .then()
                .extract()
                .cookie("__RequestVerificationToken");

        String extractCookieFromRegister = given()
                .formParam("__RequestVerificationToken", tokenForRegister1)
                .formParam("Gender", "M")
                .formParam("FirstName", "testtesttesttest2")
                .formParam("LastName", "testtesttesttest2")
                .formParam("Email", "testtest2@gmail.com")
                .formParam("Password", "123123")
                .formParam("ConfirmPassword", "123123")
                .formParam("register-button", "Register")
                .when()
                .post("/register")
                .then()
                .extract().cookie("NOPCOMMERCE.AUTH");
        System.out.println("NOPCOMMERCE.AUTH extractCookieFromRegister  !!!! " + extractCookieFromRegister);
    }
//    @Test
//    void registerTest() {
//        user.setFirstName("Test")
//                .setLastName("Test")
//                .setEmail("nidoyef352@charav.com")
//                .setPassword("111111")
//                .setGender("M");
//
//        Response register = client.register();
//        String token = register.htmlPath().getString("**.find{it.@name == '__RequestVerificationToken'}.@value");
//
//        Response newUser = client.createNewUser(token, register.cookies(), user);
//
//        Map<String, String> cookies = newUser.cookies();
//        System.out.println("Cookies !!!: " + cookies);
//    }

//    @BeforeEach
//    void setUp() {
//        step("Get RequestVerificationToken", () -> {
//            requestVerificationToken = given()
//                    .spec(request)
//                    .when()
//                    .get("/register")
//                    .then()
//                    .spec(responseSpec)
//                    .body("html.head.title", is("Demo Web Shop. Register"))
//                    .extract()
//                    .cookie("__RequestVerificationToken");
//        });
//    }
}

//    @Test
//    void registerTest() {
//        step("Fill form and register", () -> {
//            authorizationCookie = given()
//                    .spec(request)
//                    .formParam("__RequestVerificationToken", requestVerificationToken)
//                    .formParam("Gender", testData.gender)
//                    .formParam("FirstName", testData.firstName)
//                    .formParam("LastName", testData.lastName)
//                    .formParam("Email", testData.email)
//                    .formParam("Password", testData.password)
//                    .formParam("ConfirmPassword", testData.password)
//                    .formParam("register-button", "Register")
//            .when()
//                    .post("/register")
//            .then()
//                    .statusCode(302)
//                    .extract()
//                    .cookie("NOPCOMMERCE.AUTH");
//        });
//        step("Check correct redirect after register", () -> {
//            Response response = given()
//                    .spec(request)
//                    .cookie("NOPCOMMERCE.AUTH", authorizationCookie)
//                    .when()
//                    .get("/registerresult/1")
//                    .then()
//                    .spec(responseSpec)
//                    .body("html.head.title", is("Demo Web Shop. Register"))
//                    .extract()
//                    .response();
//
//            String str = response.asString();
//            Document doc = Jsoup.parse(str);
//
//            String text = Objects.requireNonNull(doc.select("div:contains(Your registration completed)")
//                    .first()).text();
//            assertThat(text).isEqualTo("Your registration completed");
//        });
//    }

