package com.isaacray.cryptoPriceBot.response;

import com.isaacray.cryptoPriceBot.SymbolManager;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Component;

@Component
public class Symbols implements IResponse {

    private final SymbolManager symbolManager;

    public Symbols(SymbolManager symbolManager) {
        this.symbolManager = symbolManager;
    }

    @Override
    public String getMatcher() {
        return "!symbols";
    }

    @Override
    public String getMessage(User user, String contentRaw) {
        symbolManager.getSymbols();
        return "The crypto symbols from CoinGecko were updated " + symbolManager.getTime();
    }

    @Override
    public String getDescription() {
        return "The list of tokens on coin gecko is stored locally.  Let's see how old they are.";
    }
}
