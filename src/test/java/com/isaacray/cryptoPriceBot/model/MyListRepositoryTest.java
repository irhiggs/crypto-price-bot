package com.isaacray.cryptoPriceBot.model;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyListRepositoryTest {

    @Autowired
    MyListRepository myListRepository;

    @Test
    @Disabled
    public void testDb() {
        MyList saveIt = new MyList();
        saveIt.setSymbols(asList("deez", "nutz"));
        saveIt.setUserName("userName");

        myListRepository.save(saveIt);

        MyList findIt = myListRepository.findById("userName").get();

        assertThat(findIt.getUserName()).isEqualTo("userName");
        assertThat(findIt.getSymbols()).containsExactly("deez", "nutz");

        findIt.getSymbols().add("are");
        myListRepository.save(findIt);

        findIt = myListRepository.findById("userName").get();
        assertThat(findIt.getSymbols()).containsExactly("deez", "nutz", "are");
    }
}