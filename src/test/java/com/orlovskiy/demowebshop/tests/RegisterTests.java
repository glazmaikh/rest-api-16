package com.orlovskiy.demowebshop.tests;

import com.orlovskiy.demowebshop.TestBase;
import com.orlovskiy.demowebshop.TestData;
import com.orlovskiy.demowebshop.client.RegisterClient;
import com.orlovskiy.demowebshop.model.User;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class RegisterTests extends TestBase {
    TestData testData = new TestData();
    public static String requestVerificationToken;
    private final User user = new User();
    public static final RegisterClient client = new RegisterClient();

    @Test
    void registerTest() {
        step("set data for user model", () -> {
            user.setGender(testData.gender);
            user.setFirstName(testData.firstName);
            user.setLastName(testData.lastName);
            user.setEmail(testData.email);
            user.setPassword(testData.password);
        });
        step("go to registration page/get token for new user", () -> {
            requestVerificationToken = client.getVerifyToken().htmlPath().getString("**.find{it.@name == '__RequestVerificationToken'}.@value");
        });
        step("create new user", () -> {
            client.createNewUser(user);
        });
        step("check success redirect", () -> {
            client.successRedirect(user);

            Document doc = Jsoup.parse(client.successRedirect(user).asString());
            Element successText = doc.select("div:containsOwn(Your registration completed)")
                    .first();

            assert successText != null;
            assertThat(successText.html()).isEqualTo("Your registration completed");
        });
    }
}

