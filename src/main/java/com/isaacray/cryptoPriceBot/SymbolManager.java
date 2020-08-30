package com.isaacray.cryptoPriceBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaacray.cryptoPriceBot.dto.CryptoSymbol;
import com.isaacray.cryptoPriceBot.service.CoinGeckoService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SymbolManager {

    private final CoinGeckoService coinGeckoService;

    private Date time = new Date();
    private List<CryptoSymbol> symbols = null;

    public SymbolManager(CoinGeckoService coinGeckoService) {
        this.coinGeckoService = coinGeckoService;
    }

    public Date getTime() {
        return time;
    }

    public void resetTime() {
        time = new Date();
    }

    public List<CryptoSymbol> getSymbols() {
        if (symbols == null) {
            symbols = generateSymbols(coinGeckoService);
        }
        return symbols;
    }

    public void resetSymbols() {
        symbols = null;
    }

    private List<CryptoSymbol> generateSymbols(CoinGeckoService coinGeckoService) {
        ObjectMapper objectMapper = new ObjectMapper();
        CryptoSymbol[] cryptoSymbols = null;
        try {
            cryptoSymbols = objectMapper.readValue(coinGeckoService.getSymbols(), CryptoSymbol[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(cryptoSymbols.clone());
    }
}
