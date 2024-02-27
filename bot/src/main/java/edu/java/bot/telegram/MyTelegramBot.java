package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.service.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MyTelegramBot implements Bot {
    private final UserMessageProcessor messageProcessor;
    private final TelegramBot bot;

    public MyTelegramBot(UserMessageProcessor messageProcessor, TelegramBot bot) {
        this.messageProcessor = messageProcessor;
        this.bot = bot;
        this.bot.execute(new SetMyCommands(messageProcessor.commandsForMenu()));
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {

        if (request instanceof SendMessage sendMessageRequest) {
            SendResponse sendResponse = bot.execute(sendMessageRequest);
            if (!sendResponse.isOk()) {
                //Добавить логгирование
            }
        }
    }

    @Override
    public int process(List<Update> updates) {
        int processedUpdates = 0;
        for (Update update : updates) {
            if (update.message() != null) {
                SendMessage sendMessage = messageProcessor.process(update).parseMode(ParseMode.HTML);
                execute(sendMessage);
                processedUpdates++;
            }
        }
        return processedUpdates;
    }

    @PostConstruct
    @Override
    public void start() {
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            }
        });
    }

    @Override
    public void close() {

    }
}

