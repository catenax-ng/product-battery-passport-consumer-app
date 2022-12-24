package tools;

import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
@SpringBootTest
class httpToolsTest {

    @AfterAll
    static void doGet() {
        assertInstanceOf(String.class, httpTools.doGet("http://localhost:8080/health", String.class, false).getBody());
    }
    @AfterAll
    static void doPost() {
        assertInstanceOf(String.class, httpTools.doPost("http://localhost:8080/health", String.class, false).getBody());
    }
}
