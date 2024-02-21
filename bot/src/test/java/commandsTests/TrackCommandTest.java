package commandsTests;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.TrackCommand;
import org.junit.jupiter.api.Assertions;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class TrackCommandTest {

    private final TrackCommand trackCommand = new TrackCommand();

    @Test
    @DisplayName("Проверка метода toApiCommand")
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/track");
        when(botCommandMock.description()).thenReturn("Начать отслеживание ссылки");

        BotCommand result = trackCommand.toApiCommand();
        Assertions.assertEquals(botCommandMock.command(),result.command());
        Assertions.assertEquals(botCommandMock.description(),result.description());
    }
    @Test
    @DisplayName("Проверка метода supports c корретной командой")
    public void supportsTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/track https://stackoverflow.com/");

        boolean result = trackCommand.supports(updateMock);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода supports c НЕкорретной командой")
    public void supportsTest2(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/trackjvrvjf https://stackoverflow.com/");

        boolean result = trackCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем message")
    public void supportsTest3(){
        Update updateMock = Mockito.mock(Update.class);

        boolean result = trackCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем text")
    public void supportsTest4(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        boolean result = trackCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода handle c корректной ссылкой")
    public void handleTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/track https://stackoverflow.com/");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, "Ссылка успешно добавлена к отслеживанию: https://stackoverflow.com/");

        SendMessage result = trackCommand.handle(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }

    @Test
    @DisplayName("Проверка метода handle с НЕкорректной ссылкой")
    public void handleTest2(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/track   fjdfjdj");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, "Введите корректную ссылку");

        SendMessage result = trackCommand.handle(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }

}

