package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {
    //@Query("FROM account WHERE username :username")
    Account findAccountByUsername(String username);

    Account findAccountByPassword(String password);
}
