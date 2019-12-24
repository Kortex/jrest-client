package com.ariskourt.restclient.utils;

import com.ariskourt.restclient.resources.RestResponse;

import java.net.http.HttpResponse;
import java.util.function.Supplier;

public class CustomerJsonBodyHandler<R, E> implements HttpResponse.BodyHandler<Supplier<RestResponse<R, E>>> {

    private final Class<R> response;
    private final Class<E> error;
    private final InputStreamUnmarshaller unmarshaller;

    CustomerJsonBodyHandler(Class<R> response, Class<E> error, InputStreamUnmarshaller unmarshaller) {
	this.response = response;
	this.error = error;
	this.unmarshaller = unmarshaller;
    }

    public CustomerJsonBodyHandler(Class<R> response, Class<E> error) {
        this(response, error, new InputStreamUnmarshaller());
    }

    @Override
    public HttpResponse.BodySubscriber<Supplier<RestResponse<R, E>>> apply(HttpResponse.ResponseInfo responseInfo) {
	return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofInputStream(),
	    stream -> unmarshaller.unmarshal(stream, response, error, responseInfo));
    }

}
