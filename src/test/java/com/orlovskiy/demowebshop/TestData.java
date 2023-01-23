package com.orlovskiy.demowebshop;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class TestData {
    static Faker faker = new Faker(new Locale("de"));
    static Random random = new Random();

    String firstName = faker.name().firstName();
    String lastName = faker.name().lastName();
    String email = faker.internet().emailAddress();
    String gender = getGender();
    String password = faker.internet().password();

    public String getGender() {
        String[] genderArray = {"M", "F"};
        return getRandomArrayItem(genderArray);
    }

    public String getRandomArrayItem(String[] values) {
        int index = random.nextInt(values.length);
        return values[index];
    }
}
