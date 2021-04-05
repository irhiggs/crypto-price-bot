package com.isaacray.cryptoPriceBot.response;

import com.isaacray.cryptoPriceBot.TheBot;
import com.isaacray.cryptoPriceBot.model.MyList;
import com.isaacray.cryptoPriceBot.model.MyListRepository;
import net.dv8tion.jda.api.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
public class MyListResponse implements IResponse{

    private final MyListRepository myListRepository;
    private final TheBot theBot;

    public MyListResponse(MyListRepository myListRepository, @Lazy TheBot theBot) {
        this.myListRepository = myListRepository;
        this.theBot = theBot;
    }

    @Override
    public String getMatcher() {
        return "!mylist";
    }

    @Override
    public String getMessage(User user, String contentRaw) {
        String[] contents = contentRaw.split(" ");
        List<String> symbols = new ArrayList<>();
        for (String content : contents) {
            if(content.matches("\\$.*")){
                symbols.add(content);
            }
        }
        if(isNotEmpty(symbols)){
            MyList listToSave = new MyList();
            listToSave.setSymbols(symbols);
            listToSave.setUserName(user.getId());
            myListRepository.save(listToSave);
        }

        Optional<MyList> byId = myListRepository.findById(user.getId());
        if(byId.isPresent()){
            if(isNotEmpty(byId.get().getSymbols())) {
                String[] objects = byId.get().getSymbols().toArray(new String[0]);
                return theBot.getPriceMessage(objects);
            }
        }
        return "I don't know you yet. " + getDescription();
    }

    @Override
    public String getDescription() {
        return "You can say \"!mylist $eth $btc\" to save your list then simple \"!mylist\" to execute it. This is in-memory and will not persist through bot restarts.";
    }
}
