package com.isaacray.cryptoPriceBot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.isaacray.cryptoPriceBot.model")
public class DatabaseConfig {
}
