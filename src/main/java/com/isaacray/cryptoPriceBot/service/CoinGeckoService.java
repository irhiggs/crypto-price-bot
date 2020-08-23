package com.isaacray.cryptoPriceBot.service;

import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CoinGeckoService {
    private final RestTemplate restTemplate;

    public CoinGeckoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getPrice(String symbol) {
        final Map map = restTemplate.getForObject("https://api.coingecko.com/api/v3/simple/price?ids=" + symbol + "&vs_currencies=usd", Map.class);
        String value = String.valueOf(((Map) map.get(symbol)).get("usd"));
        return WordUtils.capitalize(symbol) + " is worth $" + value + ". ";
    }
}