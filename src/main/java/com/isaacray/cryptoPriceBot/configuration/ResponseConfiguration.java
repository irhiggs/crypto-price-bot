package com.isaacray.cryptoPriceBot.configuration;

import com.isaacray.cryptoPriceBot.response.IResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ResponseConfiguration {

    @Bean("allResponses")
    public Map<String, IResponse> allResponses(List<IResponse> listOfResponses) {
        Map<String, IResponse> responses = new HashMap<>();
        listOfResponses.forEach(response -> {
            responses.put(response.getMatcher(), response);
        });
        return responses;
    }
}
