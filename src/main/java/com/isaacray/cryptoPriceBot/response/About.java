package com.isaacray.cryptoPriceBot.response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@PropertySource("classpath:gradle.properties")
public class About implements IResponse {

    @Value("${version}")
    private String applicationVersion;

    private final TimeUnit priceTimeUnit;
    private final long priceValue;
    private final TimeUnit symbolTimeUnit;
    private final long symbolValue;

    public About(
            @Value("${com.isaacray.cryptoPriceBot.cache.getPrice.units}") TimeUnit priceTimeUnit,
            @Value("${com.isaacray.cryptoPriceBot.cache.getPrice.value}") long priceValue,
            @Value("${com.isaacray.cryptoPriceBot.cache.getSymbols.units}") TimeUnit symbolTimeUnit,
            @Value("${com.isaacray.cryptoPriceBot.cache.getSymbols.value}") long symbolValue) {
        this.priceTimeUnit = priceTimeUnit;
        this.priceValue = priceValue;
        this.symbolTimeUnit = symbolTimeUnit;
        this.symbolValue = symbolValue;
    }

    @Override
    public String getMatcher() {
        return "!about";
    }

    @Override
    public String getMessage() {
        return "This Crypto Price Bot is currently running version: **" + applicationVersion + "**." +
                "\nPowered by CoinGecko API. <https://www.coingecko.com/en/api_terms>" +
                "\nThe price query cache time is **" + priceValue + " " + priceTimeUnit + "**." +
                "\nThe symbol list cache time is **" + symbolValue + " " + symbolTimeUnit + "**." +
                "\nCheckout the repo: <https://github.com/irhiggs/crypto-price-bot>";
    }

    @Override
    public String getDescription() {
        return "Returns the version, cache time, and license information of the bot.";
    }
}