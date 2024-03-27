package edu.java.bot.controller;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controller.dto.UpdateRequest;
import edu.java.bot.telegram.MyTelegramBot;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final MyTelegramBot myTelegramBot;

    public MainController(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    @PostMapping("/updates")
    public ResponseEntity<?> updates(@RequestBody @Valid UpdateRequest updateRequest) {
        List<Long> tgChatIds = updateRequest.tgChatIds();
        for (Long id : tgChatIds) {
            String text = "Обновление по ссылке: "
                + updateRequest.url()
                + "\n" + "Описание: "
                + updateRequest.description();
            SendMessage message = new SendMessage(id, text);
            myTelegramBot.execute(message);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
