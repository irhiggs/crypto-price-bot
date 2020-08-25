package com.isaacray.cryptoPriceBot.service;

import org.apache.commons.text.WordUtils;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Service
public class CoinGeckoService {

    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(CoinGeckoService.class);

    private final RestTemplate restTemplate;

    public CoinGeckoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable("getPrice")
    public String getPrice(String symbol) {
        LOG.info(symbol);
        final Map map = restTemplate.getForObject("https://api.coingecko.com/api/v3/simple/price?ids=" + symbol + "&vs_currencies=usd", Map.class);
        String value = String.valueOf(((Map) map.get(symbol)).get("usd"));
        return WordUtils.capitalize(symbol) + " is worth $" + value + ". \n*Fetched " + (new Date()).toString() + "*\n";
    }
}