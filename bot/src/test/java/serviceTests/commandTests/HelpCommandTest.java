package serviceTests.commandTests;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.command.Command;
import edu.java.bot.service.command.ListCommand;
import edu.java.bot.service.command.StartCommand;
import edu.java.bot.service.command.TrackCommand;
import edu.java.bot.service.command.UntrackCommand;
import org.junit.jupiter.api.Assertions;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.service.command.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;


public class HelpCommandTest {

    private HelpCommand helpCommand;

    @BeforeEach
    public void setup() {
        List<Command> commands = new ArrayList<>();
        commands.add(new StartCommand());
        commands.add(new TrackCommand());
        commands.add(new UntrackCommand());
        commands.add(new ListCommand());
        helpCommand = new HelpCommand(commands);
    }

    @Test
    @DisplayName("Проверка метода toApiCommand")
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/help");
        when(botCommandMock.description()).thenReturn("Вывести окно с командами");

        BotCommand result = helpCommand.toApiCommand();
        Assertions.assertEquals(botCommandMock.command(),result.command());
        Assertions.assertEquals(botCommandMock.description(),result.description());
    }

    @Test
    @DisplayName("Проверка метода supports c корретной командой")
    public void supportsTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/help");

        boolean result = helpCommand.supports(updateMock);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода supports c НЕкорретной командой")
    public void supportsTest2(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/helpwdjwfkq");

        boolean result = helpCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем message")
    public void supportsTest3(){
        Update updateMock = Mockito.mock(Update.class);

        boolean result = helpCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем text")
    public void supportsTest4(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        boolean result = helpCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода handle")
    public void handleTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, """
                Список доступных команд:
                /help - Вывести окно с командами
                /start - Зарегистрировать пользователя
                /track - Начать отслеживание ссылки
                /untrack - Прекратить отслеживание ссылки
                /list - Показать список отслеживаемых ссылок""");

        SendMessage result = helpCommand.handle(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }

}
