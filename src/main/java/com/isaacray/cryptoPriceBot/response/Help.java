package com.isaacray.cryptoPriceBot.response;

import net.dv8tion.jda.api.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Help implements IResponse {

    @Autowired
    private List<IResponse> responses;

    @Override
    public String getMatcher() {
        return "!help";
    }

    @Override
    public String getMessage(User user, String contentRaw) {
        StringBuilder message = new StringBuilder();
        responses.forEach(r -> {
            message.append("**" + r.getMatcher() + "**: " + r.getDescription() + "\n");
        });
        return message.toString();
    }

    @Override
    public String getDescription() {
        return "This help message.";
    }
}
