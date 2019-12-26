package com.ariskourt.restclient.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.net.http.HttpRequest;

public class CustomJsonBodyPublisher {

    private final ObjectWriter writer;

    CustomJsonBodyPublisher(ObjectWriter writer) {
        this.writer = writer;
    }

    public CustomJsonBodyPublisher() {
        this( createConfiguredWriter());
    }

    public <P> HttpRequest.BodyPublisher getPublisher(P payload) throws JsonProcessingException {
        return HttpRequest.BodyPublishers.ofString(writer.writeValueAsString(payload));
    }

    private static ObjectWriter createConfiguredWriter() {
	return new ObjectMapper().writerWithDefaultPrettyPrinter();
    }

}
