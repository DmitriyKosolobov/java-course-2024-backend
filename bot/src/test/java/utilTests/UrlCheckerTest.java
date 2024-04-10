package utilTests;

import edu.java.bot.util.UrlChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UrlCheckerTest {
    @Test
    @DisplayName("Проверка корректной ссылки")
    public void testCorrectLink() {
        String link = "https://stackoverflow.com/";
        boolean result = UrlChecker.isValid(link);
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Проверка НЕкорректной ссылки")
    public void testIncorrectLink() {
        String link = "jefkewjfnwfwkjf";
        boolean result = UrlChecker.isValid(link);
        Assertions.assertFalse(result);
    }
}
