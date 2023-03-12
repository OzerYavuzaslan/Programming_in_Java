package com.emlakcepte.repository;

import com.emlakcepte.model.Message;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {
    private static List<Message> messageList = new ArrayList<>();

    public void saveMessage(Message message) {
        messageList.add(message);
    }

    public void updateMessage(Message message){
        deleteMessage(message.getTitle());
        saveMessage(message);
    }

    public void deleteMessage(String title){
        messageList.removeIf(message -> message.getTitle().equalsIgnoreCase(title));
    }
    public List<Message> findAll() {
        return messageList;
    }
}
