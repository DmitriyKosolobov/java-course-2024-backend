package commandsTests;


import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.UntrackCommand;
import org.junit.jupiter.api.Assertions;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {

    private final UntrackCommand untrackCommand = new UntrackCommand();

    @Test
    @DisplayName("Проверка метода toApiCommand")
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/untrack");
        when(botCommandMock.description()).thenReturn("Прекратить отслеживание ссылки");

        BotCommand result = untrackCommand.toApiCommand();
        Assertions.assertEquals(botCommandMock.command(),result.command());
        Assertions.assertEquals(botCommandMock.description(),result.description());
    }

    @Test
    @DisplayName("Проверка метода supports c корретной командой")
    public void supportsTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack https://stackoverflow.com/");

        boolean result = untrackCommand.supports(updateMock);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка метода supports c НЕкорретной командой")
    public void supportsTest2(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrackjvrvjf https://stackoverflow.com/");

        boolean result = untrackCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем message")
    public void supportsTest3(){
        Update updateMock = Mockito.mock(Update.class);

        boolean result = untrackCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода supports с пустым полем text")
    public void supportsTest4(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        boolean result = untrackCommand.supports(updateMock);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Проверка метода handle c корректной ссылкой")
    public void handleTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack https://stackoverflow.com/");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, "Ссылка успешно удалена из отслеживания: https://stackoverflow.com/");

        SendMessage result = untrackCommand.handle(updateMock);
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
        when(messageMock.text()).thenReturn("/untrack   fjdfjdj");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, "Введите корректную ссылку");

        SendMessage result = untrackCommand.handle(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }

}
