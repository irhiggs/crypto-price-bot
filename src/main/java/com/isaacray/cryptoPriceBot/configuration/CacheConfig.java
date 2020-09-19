package com.isaacray.cryptoPriceBot.configuration;

import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    private final TimeUnit priceTimeUnit;
    private final long priceValue;
    private final TimeUnit symbolTimeUnit;
    private final long symbolValue;

    public CacheConfig(
            @Value("${com.isaacray.cryptoPriceBot.cache.getPrice.units}") TimeUnit priceTimeUnit,
            @Value("${com.isaacray.cryptoPriceBot.cache.getPrice.value}") long priceValue,
            @Value("${com.isaacray.cryptoPriceBot.cache.getSymbols.units}") TimeUnit symbolTimeUnit,
            @Value("${com.isaacray.cryptoPriceBot.cache.getSymbols.value}") long symbolValue) {
        this.priceTimeUnit = priceTimeUnit;
        this.priceValue = priceValue;
        this.symbolTimeUnit = symbolTimeUnit;
        this.symbolValue = symbolValue;
    }

    @Primary
    @Bean
    public CacheManager getPriceCacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name, CacheBuilder.newBuilder()
                        .expireAfterWrite(priceValue, priceTimeUnit)
                        .build()
                        .asMap(),
                        false);
            }
        };
    }

    @Bean
    public CacheManager getSymbolsCacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(final String name) {
                return new ConcurrentMapCache(name, CacheBuilder.newBuilder()
                        .expireAfterWrite(symbolValue, symbolTimeUnit)
                        .build()
                        .asMap(),
                        false);
            }
        };
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
}