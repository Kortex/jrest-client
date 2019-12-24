package com.ariskourt.restclient.utils;

import com.ariskourt.restclient.resources.RestResponse;
import com.ariskourt.restclient.exceptions.UnmarshallerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

public class InputStreamUnmarshaller {

    private static final Logger LOGGER = LogManager.getLogger(InputStreamUnmarshaller.class);

    private final ObjectMapper mapper;

    InputStreamUnmarshaller(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public InputStreamUnmarshaller() {
        this(new ObjectMapper());
    }

    public <R, E>Supplier<RestResponse<R, E>> unmarshal(InputStream stream, Class<R> response, Class<E> error, HttpResponse.ResponseInfo info) {
        return () -> {
            var responseBuilder = RestResponse.<R, E>builder()
                .code(info.statusCode())
                .isSuccess(is2xx(info.statusCode()));

            try (stream) {
                if (is2xx(info.statusCode())) {
                    return responseBuilder
                        .response(mapper.readValue(stream, response))
                        .build();
                }

                return responseBuilder
                    .error(mapper.readValue(stream, error))
                    .build();

            } catch (IOException e) {
                LOGGER.error("An error has occurred while unmarshalling to response type {} and error type {}",
                    response.getCanonicalName(),
                    error.getCanonicalName());
                throw new UnmarshallerException(e);
            }
        };
    }

    private static boolean is2xx(int responseCode) {
        return responseCode >= HttpStatus.SC_OK
            && responseCode <= HttpStatus.SC_MULTI_STATUS;
    }

}
