package com.isaacray.cryptoPriceBot.response;

import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

@Component
public class Price implements IResponse {

    @Override
    public String getMatcher() {
        return "$AnyCryptoSymbolOrName";
    }

    @Override
    public String getMessage(User user, String contentRaw) {
        return "";
    }

    @Override
    public String getDescription() {
        return "Returns the price, in USD of any crypto symbol, name, or id.\n - For example: **$eth** returns:\n - Ethereum is worth $410.44";
    }
}
