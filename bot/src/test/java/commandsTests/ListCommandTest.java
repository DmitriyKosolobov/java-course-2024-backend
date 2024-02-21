package commandsTests;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.ListCommand;
import org.junit.jupiter.api.Assertions;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class ListCommandTest {

    private final ListCommand listCommand = new ListCommand();

    @Test
    @DisplayName("Проверка метода toApiCommand")
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/list");
        when(botCommandMock.description()).thenReturn("Показать список отслеживаемых ссылок");

        BotCommand result = listCommand.toApiCommand();
        Assertions.assertEquals(botCommandMock.command(),result.command());
        Assertions.assertEquals(botCommandMock.description(),result.description());
    }

    @Test
    @DisplayName("Проверка метода supports c корретной командой")
    public void supportsTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/list");

        boolean result = listCommand.supports(updateMock);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода supports c НЕкорретной командой")
    public void supportsTest2(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/listwdjwfkq");

        boolean result = listCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем message")
    public void supportsTest3(){
        Update updateMock = Mockito.mock(Update.class);

        boolean result = listCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем text")
    public void supportsTest4(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        boolean result = listCommand.supports(updateMock);
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

        SendMessage testSendMessage = new SendMessage(1L, "Список отслеживаемых ссылок пуст!");

        SendMessage result = listCommand.handle(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }

}

