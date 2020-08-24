package com.isaacray.cryptoPriceBot;

import com.isaacray.cryptoPriceBot.dto.CryptoSymbol;
import com.isaacray.cryptoPriceBot.response.IResponse;
import com.isaacray.cryptoPriceBot.service.CoinGeckoService;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TheBot extends ListenerAdapter {

    private final CoinGeckoService coinGeckoService;
    private final List<CryptoSymbol> symbols;
    private final Logger LOG = LoggerFactory.getLogger(TheBot.class);
    private final Map<String, IResponse> allResponses;

    public TheBot(
        CoinGeckoService coinGeckoService,
        List<CryptoSymbol> symbols,
        @Value("${com.isaacray.cryptoPriceBot.botToken}") String botToken,
        @Qualifier("allResponses") Map<String, IResponse> allResponses) throws Exception {
        this.coinGeckoService = coinGeckoService;
        this.symbols = symbols;
        this.allResponses = allResponses;
        JDABuilder jdaBuilder = JDABuilder.createDefault(botToken);
        jdaBuilder.setToken(botToken);
        jdaBuilder.addEventListeners(this);
        jdaBuilder.build();
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        final String contentRaw = event.getMessage().getContentRaw();
        String firstWord = contentRaw.split(" ")[0];
        final IResponse iResponse = allResponses.get(firstWord.toLowerCase());
        if (iResponse != null) {
            event.getChannel().sendMessage(iResponse.getMessage()).queue();
        } else {
            final String[] strings = contentRaw.split(" ");
            String message = "";
            for (int i = 0; i < strings.length; i++) {
                if (strings[i].startsWith("$")) {
                    String searchString = strings[i]
                        .replace("$", "")
                        .replace(",", "")
                        .replace("!", "")
                        .replace("?", "");
                    if (searchString.endsWith(".")) {
                        searchString = searchString.substring(0, searchString.length() - 1);
                    }
                    CryptoSymbol symbol = findSymbol(searchString);
                    if (symbol != null) {
                        message += coinGeckoService.getPrice(symbol.getId());
                    }
                }
            }
            if (Strings.isNotEmpty(message.trim())) {
                LOG.info("Received \"{}\" from {}", contentRaw, event.getMessage().getAuthor().getAsTag());
                event.getChannel().sendMessage(message).queue();
            }
        }
    }

    private CryptoSymbol findSymbol(String search) {
        return symbols.stream().filter(s ->
            s.getId().equalsIgnoreCase(search)
                || s.getName().equalsIgnoreCase(search)
                || s.getSymbol().equalsIgnoreCase(search)
        ).findFirst().orElse(null);
    }
}