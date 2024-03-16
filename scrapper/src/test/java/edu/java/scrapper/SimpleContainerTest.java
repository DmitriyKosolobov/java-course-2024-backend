package edu.java.scrapper;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SimpleContainerTest {
    public static PostgreSQLContainer<?> CONTAINER = IntegrationTest.POSTGRES;

    @Test
    public void testContainerStartup() {
        assertTrue(CONTAINER.isRunning());
    }
}
