package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
@Transactional
public class MessageService {
    MessageRepository messageRepository;
    AccountRepository accountRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }
    
    public Message createMessage(Message message){
        if (message.getMessage_text().length() <= 0)
        {
            return null;
        }
        if (message.getMessage_text().length() > 255)
        {    
            return null;
        }
       
        if (accountRepository.findById(message.getPosted_by()).isEmpty())
        {    
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(int message_id) {

        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if (optionalMessage.isPresent())
        {
            return optionalMessage.get();
        }
        return null;        
    }

    public boolean deleteMessage(int message_id) {
        Message message = getMessageById(message_id);
        if (message == null)
        {
            return false;
        }
        messageRepository.deleteById(message_id);
        return true;
    }

    public boolean updateMessage(int message_id, Message replacementMessage)
    {
        String updatedText = replacementMessage.getMessage_text();
        if (updatedText.length() == 0 || updatedText.length() > 255)
        {
            return false;
        }
        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent())
        {
            Message message = optionalMessage.get();
            message.setMessage_text(replacementMessage.getMessage_text());
            messageRepository.save(message);
            return true;
        }
        
        return false;
    }

    public List<Message> getMessagesByUser(int posted_by)
    {
        return messageRepository.getMessagesByUser(posted_by);
    }

}
