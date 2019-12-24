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
	return "Start time: " + start + " - End Time: " + stop + ". "
	    + Optional.ofNullable(elapsed())
	    .map(elapsed -> "Operation took: " + elapsed + " ms")
	    .orElse("Time not stopped. Operation is still running..");
    }

}
