package com.isaacray.cryptoPriceBot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isaacray.cryptoPriceBot.dto.CryptoSymbol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CryptoSymbolConfiguration {

    @Bean
    public List<CryptoSymbol> symbols(ResourceLoader resourceLoader) {
        final Resource resource = resourceLoader.getResource("classpath:allSymbols.json");
        ObjectMapper objectMapper = new ObjectMapper();
        CryptoSymbol[] cryptoSymbols = null;
        try {
            cryptoSymbols = objectMapper.readValue(resource.getInputStream(), CryptoSymbol[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(cryptoSymbols.clone());
    }
}