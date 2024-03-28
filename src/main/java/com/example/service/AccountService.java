package com.example.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public Account createAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        if (username.length() == 0)
        {
            return null;
        }
        if (password.length() < 4)
        {    
            return null;
        }
        return accountRepository.save(account);
    }

    public Account findByUsername(String username)
    {
        return accountRepository.findAccountByUsername(username);
    }

    public Account checkLogin(Account account)
    {
        return accountRepository.findAccountByUsername_AndPassword(account.getUsername(), account.getPassword());
    }

}
