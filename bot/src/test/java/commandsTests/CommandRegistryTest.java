package commandsTests;

import edu.java.bot.commands.Command;
import edu.java.bot.commands.CommandRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandRegistryTest {
    @Test
    @DisplayName("Проверка метода getRegisteredCommands")
    public void getRegisteredCommandsTest(){
        List<Command> commands = CommandRegistry.getRegisteredCommands();
        assertThat(commands).hasSize(5);
        for (Object el : commands) {
            assertThat(el).isInstanceOf(Command.class);
        }
    }
}
