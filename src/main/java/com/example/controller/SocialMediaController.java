package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import com.example.entity.Account;
import com.example.entity.Message;
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
    @PostMapping("/login")
    public ResponseEntity<Account> loginCheck(@RequestBody Account account)
    {
        Account existingUser = accountService.checkLogin(account);
        if (existingUser != null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(existingUser); //check this
        }
        else
        {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); //check over this
        }
    }

    //STEP 3 HERE AKA CREATE NEW MESSAGES
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message)
    {
        Message newMessage = messageService.createMessage(message);
        if (newMessage == null)
        {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(newMessage);

    }
    //STEP 4  RETRIEVE ALL MESSAGES
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages()
    {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(allMessages);
    }

    //STEP 5 GET MESSAGE BY ID
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id)
    {
        Message existingMessage = messageService.getMessageById(message_id);

        if (existingMessage == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(existingMessage);
    }
    //STEP 6 DELETE MESSAGE BY ID
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int message_id)
    {
        if (messageService.deleteMessage(message_id))
        {
            return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    //STEP 7 UPDATE MESSAGE BY ID 
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> updateMessageById(@PathVariable int message_id, @RequestBody Message message)
    {
        if (messageService.updateMessage(message_id, message))
        {
        return ResponseEntity.status(HttpStatus.OK).body(1);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    //STEP 8 RETRIEVE ALL MESSAGES BY USER
    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<?> getMessageByUser(@PathVariable int account_id)
    {
        List<Message> messageList = messageService.getMessagesByUser(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }
}
