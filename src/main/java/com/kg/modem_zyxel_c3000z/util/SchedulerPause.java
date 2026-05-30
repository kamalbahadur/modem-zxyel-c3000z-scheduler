package com.kg.modem_zyxel_c3000z.util;

import org.springframework.stereotype.Component;

@Component
public class SchedulerPause {

    private volatile boolean paused;

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        paused = false;
    }
}
