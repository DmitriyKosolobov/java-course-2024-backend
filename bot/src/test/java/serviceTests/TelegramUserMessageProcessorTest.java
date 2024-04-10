package serviceTests;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.TelegramUserMessageProcessor;
import edu.java.bot.service.command.Command;
import edu.java.bot.service.command.HelpCommand;
import edu.java.bot.service.command.ListCommand;
import edu.java.bot.service.command.StartCommand;
import edu.java.bot.service.command.TrackCommand;
import edu.java.bot.service.command.UntrackCommand;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class TelegramUserMessageProcessorTest {

    private TelegramUserMessageProcessor messageProcessor;

    @BeforeEach
    public void setup() {
        List<Command> commands = new ArrayList<>();
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand());
        HelpCommand helpCommand = new HelpCommand(commands);
        messageProcessor = new TelegramUserMessageProcessor(commands);
    }

    @Test
    @DisplayName("Проверка метода commands")
    public void commandsTest() {
        List<Command> commandList = messageProcessor.commands();
        Assertions.assertEquals(5, commandList.size());
    }

    @Test
    @DisplayName("Проверка метода commandsForMenu")
    public void commandsForMenuTest() {
        BotCommand[] commandList = messageProcessor.commandsForMenu();
        Assertions.assertEquals(5, commandList.length);
    }

    @Test
    @DisplayName("Проверка метода process c корректной командой")
    public void processTest1() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/help");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, """
            Список доступных команд:
            /help - Вывести окно с командами
            /start - Зарегистрировать пользователя
            /track - Начать отслеживание ссылки
            /untrack - Прекратить отслеживание ссылки
            /list - Показать список отслеживаемых ссылок""");

        SendMessage result = messageProcessor.process(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"), result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"), result.getParameters().get("text"));
    }

    @Test
    @DisplayName("Проверка метода process c НЕкорректной командой")
    public void processTest2() {
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/helpcjsnjcnjsd");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, "Неизвестная команда");

        SendMessage result = messageProcessor.process(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"), result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"), result.getParameters().get("text"));
    }
}
