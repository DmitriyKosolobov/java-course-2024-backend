package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controller.dto.UpdateRequest;
import edu.java.bot.telegram.MyTelegramBot;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {
    private final MyTelegramBot myTelegramBot;

    public UpdateService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void handle(UpdateRequest updateRequest) {
        List<Long> tgChatIds = updateRequest.tgChatIds();
        for (Long id : tgChatIds) {
            String text = "Обновление по ссылке: "
                + updateRequest.url()
                + "\n" + "Описание: "
                + updateRequest.description();
            SendMessage message = new SendMessage(id, text);
            myTelegramBot.execute(message);
        }
    }
}
