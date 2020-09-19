package com.isaacray.cryptoPriceBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaacray.cryptoPriceBot.dto.CryptoSymbol;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class SymbolManager {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(SymbolManager.class);
    private final RestTemplate restTemplate;
    private final String symbolListUrl;

    private Date time;

    public SymbolManager(RestTemplate restTemplate,
                         @Value("${com.isaacray.cryptoPriceBot.coinGecko.url.symbolList}")
                                 String symbolListUrl) {
        this.restTemplate = restTemplate;
        this.symbolListUrl = symbolListUrl;
    }

    public Date getTime() {
        return time;
    }

    private String fetchSymbols() {
        time = new Date();
        return restTemplate.getForObject(symbolListUrl, String.class);
    }

    @Cacheable(value = "getSymbols", cacheManager = "getSymbolsCacheManager")
    public List<CryptoSymbol> getSymbols() {
        LOG.info("Running symbol gen");
        ObjectMapper objectMapper = new ObjectMapper();
        CryptoSymbol[] cryptoSymbols = null;
        try {
            cryptoSymbols = objectMapper.readValue(fetchSymbols(), CryptoSymbol[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(cryptoSymbols.clone());
    }
}