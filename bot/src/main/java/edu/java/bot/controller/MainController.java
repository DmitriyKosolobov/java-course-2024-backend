package edu.java.bot.controller;

import edu.java.bot.controller.dto.UpdateRequest;
import edu.java.bot.service.UpdateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final UpdateService updateService;

    public MainController(UpdateService updateService) {
        this.updateService = updateService;
    }

    @PostMapping("/updates")
    public ResponseEntity<?> updates(@RequestBody @Valid UpdateRequest updateRequest) {
        updateService.handle(updateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
