package com.isaacray.cryptoPriceBot.response;

import com.isaacray.cryptoPriceBot.SymbolManager;
import org.springframework.stereotype.Component;

@Component
public class UpdateTokenList implements IResponse {

private final SymbolManager symbolManager;

    public UpdateTokenList(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    @Override
    public String getMatcher() {
        return "!update";
    }

    @Override
    public String getMessage() {
        symbolManager.resetSymbols();
        symbolManager.getSymbols();
        return "The crypto symbols from CoinGecko were updated " + symbolManager.getTime();
    }

    @Override
    public String getDescription() {
        return "The list of tokens on coin gecko is stored locally.  Calling this endpoint will refresh it.";
    }
}
