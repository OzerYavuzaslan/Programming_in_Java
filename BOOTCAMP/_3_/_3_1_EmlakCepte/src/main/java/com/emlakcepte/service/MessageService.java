package com.emlakcepte.service;

import com.emlakcepte.model.Message;
import com.emlakcepte.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void create(Message message) {
        messageRepository.saveMessage(message);
    }

    public void update(Message message) {
        messageRepository.updateMessage(message);
    }

    public void delete(String  title) {
        messageRepository.deleteMessage(title);
    }

    public List<Message> getAll() {
        return messageRepository.findAll();
    }
}
