package com.example.controller;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    public AccountService accountService;
    public MessageService messageService;
    
    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount)
    {
        Account newUser = accountService.findByUsername(newAccount.getUsername());
        if (newUser != null)
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Account anotherAccount = accountService.createAccount(newAccount);
        if (anotherAccount != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(anotherAccount);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }


}
