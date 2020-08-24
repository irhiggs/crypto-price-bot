package com.isaacray.cryptoPriceBot.response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class Price implements IResponse {

    @Override
    public String getMatcher() {
        return "$AnyCryptoSymbolOrName";
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Returns the price, in USD of any crypto symbol, name, or id.\n - For example: **$eth** returns:\n - Ethereum is worth $410.44";
    }
}
