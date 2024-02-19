package serviceTests;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandRegistry;
import edu.java.bot.service.TelegramUserMessageProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.mockito.Mockito.when;

public class TelegramUserMessageProcessorTest {

    List<Command> commands = CommandRegistry.getRegisteredCommands();
    TelegramUserMessageProcessor messageProcessor = new TelegramUserMessageProcessor(commands);

    @Test
    @DisplayName("Проверка метода process c корректной командой")
    public void processTest1(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/help");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, """
                Список доступных команд:
                /start - Зарегистрировать пользователя
                /help - Вывести окно с командами
                /track - Начать отслеживание ссылки
                /untrack - Прекратить отслеживание ссылки
                /list - Показать список отслеживаемых ссылок
                """);

        SendMessage result = messageProcessor.process(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }

    @Test
    @DisplayName("Проверка метода process c НЕкорректной командой")
    public void processTest2(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);
        Chat chatMock = Mockito.mock(Chat.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/helpcjsnjcnjsd");
        when(messageMock.chat()).thenReturn(chatMock);
        when(chatMock.id()).thenReturn(1L);

        SendMessage testSendMessage = new SendMessage(1L, "Неизвестная команда");

        SendMessage result = messageProcessor.process(updateMock);
        Assertions.assertEquals(testSendMessage.getParameters().get("chat_id"),result.getParameters().get("chat_id"));
        Assertions.assertEquals(testSendMessage.getParameters().get("text"),result.getParameters().get("text"));
    }
}
