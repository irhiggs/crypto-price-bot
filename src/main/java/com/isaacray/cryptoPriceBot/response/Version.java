package com.isaacray.cryptoPriceBot.response;

import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:gradle.properties")
public class Version implements IResponse {

    @Value("${version}")
    private String applicationVersion;

    @Override
    public String getMatcher() {
        return "!version";
    }

    @Override
    public String getMessage(User user, String contentRaw) {
        return "This Crypto Price Bot is currently running " + applicationVersion + "\nPowered by CoinGecko API";
    }

    @Override
    public String getDescription() {
        return "Reports information about Crypto Price Bot";
    }
}
