package com.orlovskiy.demowebshop;

import com.codeborne.selenide.Configuration;
import com.orlovskiy.demowebshop.config.AuthConfig;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    private static AuthConfig config;
    public static String login;
    public static String password;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com";
        Configuration.baseUrl = "https://demowebshop.tricentis.com";
        //System.setProperty("webdriver.chrome.driver", "C://webdrivers/chromedriver.exe");
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        config = ConfigFactory.create(AuthConfig.class, System.getProperties());
        login = config.getLogin();
        password = config.getPass();
    }



}
