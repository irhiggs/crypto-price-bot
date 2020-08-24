package com.isaacray.cryptoPriceBot.response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:gradle.properties")
public class About implements IResponse {

    @Value("${version}")
    private String applicationVersion;

    @Override
    public String getMatcher() {
        return "!about";
    }

    @Override
    public String getMessage() {
        return "This Crypto Price Bot is currently running version: " + applicationVersion + "\nPowered by CoinGecko API" +
            "\nThe query cache time is 15 minutes\nhttps://www.coingecko.com/en/api_terms";
    }

    @Override
    public String getDescription() {
        return "Returns the version, cache time, and license information of the bot.";
    }
}
