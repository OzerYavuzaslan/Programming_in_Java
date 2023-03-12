package com.emlakcepte.controller;

import com.emlakcepte.model.Message;
import com.emlakcepte.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Message> getAll(){
        return messageService.getAll();
    }

    @PostMapping
    public ResponseEntity<Message> create(@RequestBody Message message){
        messageService.create(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/{title}")
    public ResponseEntity<Message> update(@RequestBody Message message){
        messageService.update(message);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<String> delete(@RequestBody String title){
        messageService.delete(title);
        return new ResponseEntity<>(title, HttpStatus.CREATED);
    }
}
