package com.orlovskiy.demowebshop.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:auth.properties"
})
public interface AuthConfig extends Config {

    @Key("Email")
    String getLogin();

    @Key("Password")
    String getPass();
}
