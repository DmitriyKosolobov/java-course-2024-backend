package edu.java.controller;

import edu.java.exception.ExistChatException;
import edu.java.exception.NotFoundChatException;
import edu.java.service.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> createTgChat(@PathVariable Long id) throws ExistChatException {
        chatService.register(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteTgChat(@PathVariable Long id) throws NotFoundChatException {
        chatService.unregister(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
