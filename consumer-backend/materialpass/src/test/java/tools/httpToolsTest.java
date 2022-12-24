package tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class httpToolsTest {

    @Test
    void doGet() {
        assertNotNull(httpTools.doGet("http://localhost:8080/health", String.class, false));
    }
    @Test
    void doPost() {
        assertNotNull(httpTools.doPost("http://localhost:8080/health", String.class, false));
    }
}
