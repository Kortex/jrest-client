package com.ariskourt.restclient.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class PerformanceTimer {

    private Instant start;
    Instant getStart() { return start; }

    private Instant stop;
    Instant getStop() { return stop; }

    public void start() {
        start = Instant.now();
        stop = null;
    }

    public void stop() {
        stop = Instant.now();
    }

    Long elapsed() {
        return Optional.ofNullable(stop)
	    .map(stopInstant -> Duration.between(start, stopInstant).toMillis())
	    .orElse(null);
    }

    @Override
    public String toString() {
	return Optional.ofNullable(elapsed())
	    .map(elapsedMs -> "REST call took " + elapsedMs + " ms")
	    .orElse("Timer hasn't been stopped");
    }

}
