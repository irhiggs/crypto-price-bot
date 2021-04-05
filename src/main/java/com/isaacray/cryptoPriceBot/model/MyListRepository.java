package com.isaacray.cryptoPriceBot.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface MyListRepository extends JpaRepository<MyList, String> {
}