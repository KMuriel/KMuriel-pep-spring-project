package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
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
        if (message.getPosted_by() <= 0)
        {    
            return null;
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getAllMessagesById(int message_id) {

        return messageRepository.findById(message_id).get();        
    }

    //should this return void or Message??
    public void deleteMessage(int message_id) {
        /*Message message = messageRepository.getAllMessagesById(message_id);
        if (message_id.getMessage_text == null)
        {
            return null;
        }
        messageDAO.deleteMessage(message);*/   
        //return message;
        messageRepository.deleteById(message_id);
    }

    public Message updateMessage(int message_id, Message replacementMessage)
    {
        /*if (messageRepository.getAllMessagesById(message_id) == null)
        {
            return null;
        }
        if (message.getMessage_text().length() == 0 || message.getMessage_text().length() > 255)
        {
            return null;
        }
        messageRepository.updateMessage(message_id, message);
        return messageRepository.getAllMessagesById(message_id);*/

        Optional<Message> optionalMessage = messageRepository.findById(message_id);
        if(optionalMessage.isPresent())
        {
            Message message = optionalMessage.get();
            message.setMessage_text(replacementMessage.getMessage_text());
            messageRepository.save(message);
            return message;
        }
        
        return null;
    }

    public List<Message> getMessagesByUser(int posted_by)
    {
        return messageRepository.getMessagesByUser(posted_by);
    }

}
