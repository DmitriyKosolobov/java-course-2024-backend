package edu.java.controller;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinksResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<?> createTgChat(@PathVariable Integer id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<?> deleteTgChat(@PathVariable Integer id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/links")
    public ResponseEntity<ListLinksResponse> getLinks(@RequestHeader("Tg-Chat-Id") Integer chatId) {
        var listLinksResponse =
            new ListLinksResponse(List.of(new LinkResponse(1, URI.create("https://github.com/dashboard"))), 1);
        return new ResponseEntity<>(listLinksResponse, HttpStatus.OK);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> createLink(
        @RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody @Valid AddLinkRequest addLinkRequest
    ) {
        var linkResponse = new LinkResponse(1, URI.create("https://github.com"));
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> deleteLink(
        @RequestHeader("Tg-Chat-Id") Integer chatId,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) {
        var linkResponse = new LinkResponse(1, URI.create("https://stackoverflow.com/"));
        return new ResponseEntity<>(linkResponse, HttpStatus.OK);
    }
}
