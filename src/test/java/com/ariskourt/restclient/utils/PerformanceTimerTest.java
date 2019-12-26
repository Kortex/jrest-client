package com.ariskourt.restclient.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

class PerformanceTimerTest {

    private PerformanceTimer timer;

    @BeforeEach
    void setUp() {
        timer = new PerformanceTimer();
    }

    @Test
    public void Start_WhenStartingTimer_StartNotNullStopIsNull() {
        timer.start();
        assertAll(
            () -> assertNotNull(timer.getStart()),
            () -> assertNull(timer.getStop())
        );
    }

    @Test
    public void Stop_WhenStoppingTimer_StartAndStopInstantsAreNotNull() {
        timer.start();
        timer.stop();
        assertAll(
            () -> assertNotNull(timer.getStart()),
            () -> assertNotNull(timer.getStop())
        );
    }

    @Test
    public void Elapsed_StartingAndStoppingTime_CorrectElapsedTime() {
        timer.start();
        await().atMost(10, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                timer.stop();
                var m = Duration.between(timer.getStart(), timer.getStop()).toMillis();
                assertEquals(m, (long) timer.elapsed());
            });
    }

    @Test
    public void Elapsed_StartingAndStoppingTimer_CorrectStringMessage() {
        timer.start();
        await().atMost(10, TimeUnit.SECONDS)
            .untilAsserted(() -> {
                timer.stop();
                assertNotNull(timer.getStart());
                assertNotNull(timer.getStop());
                assertThat(timer.toString()).contains(String.valueOf(TimeUnit.MILLISECONDS.toSeconds(timer.elapsed())));
            });
    }

    @Test
    public void ToString_WhenTimerNotStopped_CorrectMessageIsReturned() {
        timer.start();
        assertThat(timer.toString()).contains("Timer hasn't been stopped");
    }

}