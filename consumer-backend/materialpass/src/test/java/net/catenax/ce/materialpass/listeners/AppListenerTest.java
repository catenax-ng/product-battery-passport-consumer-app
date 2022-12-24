package net.catenax.ce.materialpass.listeners;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppListenerTest {

    AppListener listener;
    @BeforeEach
    void setUp() throws Exception {
        this.listener = new AppListener();
    }
    @Test
    void onStartUp() {

        this.listener.onStartUp();
    }
}
