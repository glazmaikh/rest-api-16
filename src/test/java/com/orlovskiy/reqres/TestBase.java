package com.orlovskiy.reqres;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
        //System.setProperty("webdriver.chrome.driver", "C://webdrivers/chromedriver.exe");
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    }
}
