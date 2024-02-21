package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandRegistry;
import edu.java.bot.service.TelegramUserMessageProcessor;
import edu.java.bot.service.UserMessageProcessor;
import java.util.List;

public class MyTelegramBot implements Bot {
    private final UserMessageProcessor messageProcessor;
    private final TelegramBot bot;

    public MyTelegramBot(String botToken) {
        List<Command> commands = CommandRegistry.getRegisteredCommands();
        this.messageProcessor = new TelegramUserMessageProcessor(commands);
        this.bot = new TelegramBot(botToken);
        bot.execute(new SetMyCommands(CommandRegistry.getCommandsForMenu()));
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

    @SuppressWarnings("MagicNumber")
    @Override
    public void start() {
        int offset = 0;
        while (true) {
            GetUpdates getUpdates = new GetUpdates().limit(100).offset(offset).timeout(0);
            GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
            List<Update> updates = updatesResponse.updates();
            if (updates != null && !updates.isEmpty()) {
                int processedUpdates = process(updates);
                if (processedUpdates > 0) {
                    offset = updates.get(processedUpdates - 1).updateId() + 1;
                }
            }
        }
    }

    @Override
    public void close() {

    }
}

