package com.orlovskiy.demowebshop;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.orlovskiy.demowebshop.TestData.faker;
import static com.orlovskiy.demowebshop.specs.Specs.request;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class AddToCardTests extends TestBase {

    TestData testData = new TestData();
    public String authorizationCookie;

    @Test
    void addedGiftToCardAndCheckResultTest() {
        step("Get authorization cookie", () -> {
            authorizationCookie =
                    given()
                            .spec(request)
                            .formParam("Email", login)
                            .formParam("Password", password)
                    .when()
                            .post("/login")
                    .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");
            step("Open minimal element on site", () -> {
                open("/Themes/DefaultClean/Content/images/logo.png");
            });
            step("Set cookie to browser", () -> {
                getWebDriver().manage().addCookie(
                        new Cookie("NOPCOMMERCE.AUTH", authorizationCookie)
                );
            });
        });

        step("Add gift card to card", () -> {
            given()
                    .spec(request)
                    .cookie("NOPCOMMERCE.AUTH", authorizationCookie)
                    .formParam("giftcard_1.RecipientName", testData.firstName)
                    .formParam("giftcard_1.RecipientEmail", testData.email)
                    .formParam("giftcard_1.SenderName", testData.firstName)
                    .formParam("giftcard_1.SenderEmail", testData.email)
                    .formParam("giftcard_1.Message", faker.lorem().fixedString(10))
                    .formParam("addtocart_1.EnteredQuantity", 1)
                    .when()
                    .post("/addproducttocart/details/1/1")
                    .then()
                    .statusCode(200)
                    .body("success", is(true),
                            "message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
        });
        step("Open shipping card", () -> {
            open("/cart");
        });
        step("Check added gift card", () -> {
            $("tr.cart-item-row").shouldHave(text("$5 Virtual Gift Card"));
            $("tr.cart-item-row").shouldHave(text("From: " + testData.firstName + " <" + testData.email + ">"));
            $("tr.cart-item-row").shouldHave(text("For: " + testData.firstName + " <" + testData.email + ">"));
        });
    }

    @Test
    void addGiftCardToCardAnonymTest() {
        step("Add gift card to card", () -> {
            given()
                    .spec(request)
                    .formParam("giftcard_1.RecipientName", testData.firstName)
                    .formParam("giftcard_1.RecipientEmail", testData.email)
                    .formParam("giftcard_1.SenderName", testData.firstName)
                    .formParam("giftcard_1.SenderEmail", testData.email)
                    .formParam("giftcard_1.Message", faker.lorem().fixedString(10))
                    .formParam("addtocart_1.EnteredQuantity", 1)
                    .when()
                    .post("/addproducttocart/details/1/1")
                    .then()
                    .statusCode(200)
                    .body("success", is(true),
                            "message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"));
        });
    }
}
