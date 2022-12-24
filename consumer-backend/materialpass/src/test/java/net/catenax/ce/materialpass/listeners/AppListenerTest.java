package net.catenax.ce.materialpass.listeners;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppListenerTest {

    @Test
    void onStartUp() {
        AppListener listener = new AppListener();
        listener.onStartUp();
    }
}
