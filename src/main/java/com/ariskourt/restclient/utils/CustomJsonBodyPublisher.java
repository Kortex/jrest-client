package com.ariskourt.restclient.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.net.http.HttpRequest;
import java.util.Optional;

public class CustomJsonBodyPublisher {

    private final ObjectWriter writer;

    CustomJsonBodyPublisher(ObjectWriter writer) {
        this.writer = writer;
    }

    public CustomJsonBodyPublisher() {
        this( createConfiguredWriter());
    }

    public <P> HttpRequest.BodyPublisher getPublisher(P payload) throws JsonProcessingException {
        String written = "";
        if (payload != null) {
            written = writer.writeValueAsString(payload);
        }
        return HttpRequest.BodyPublishers.ofString(written);
    }

    private static ObjectWriter createConfiguredWriter() {
	return new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

}
