package com.isaacray.cryptoPriceBot.response;

import net.dv8tion.jda.api.entities.User;

public interface IResponse {
    String getMatcher();

    String getMessage(User user, String contentRaw);

    String getDescription();
}
