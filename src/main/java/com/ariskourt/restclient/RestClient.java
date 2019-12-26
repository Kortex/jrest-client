package com.ariskourt.restclient;

import com.ariskourt.restclient.exceptions.RestClientException;
import com.ariskourt.restclient.resources.RestResponse;
import com.ariskourt.restclient.utils.CustomJsonBodyPublisher;
import com.ariskourt.restclient.utils.CustomerJsonBodyHandler;
import com.ariskourt.restclient.utils.HttpMethod;
import com.ariskourt.restclient.utils.PerformanceTimer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RestClient {

    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(5);
    private static final Logger LOGGER = LogManager.getLogger(RestClient.class);
    private static final Lock INIT_LOCK = new ReentrantLock();

    private static RestClient instance;

    private final HttpClient client;
    private final PerformanceTimer timer;

    RestClient(HttpClient client, PerformanceTimer timer) {
        this.client = client;
        this.timer = timer;
    }

    RestClient() {
        this(createConfiguredClient(), new PerformanceTimer());
    }

    public static RestClient getInstance() {
        if (instance == null) {
            INIT_LOCK.lock();
            try {
                instance = new RestClient();
            } finally {
                INIT_LOCK.unlock();
            }
        }
        return instance;
    }

    /***
     * Method that perform a GET request to the given URI. Performs internal deserialization based on
     * response's HTTP status, mapping either to the expected response type or the expected error type. Will throw
     * a {@link RestClientException} in case a communication error takes takes place or the unmarshalling to the object
     * type fails
     *
     * @param response - The response type class
     * @param error - The error type class
     * @param params - A map representing the params to be added to the request
     * @param <R> - The type of the expected response
     * @param <E> - The type of the expected error
     * @return - A {@link RestResponse} containing the response or the error along with the status code
     */
    public <R, E> RestResponse<R, E> GET(String uri, Class<R> response, Class<E> error, Map<String, String> params) throws RestClientException {
        try {
            timer.start();
            var uriBuilder = new URIBuilder(uri);
            params.forEach(uriBuilder::addParameter);

            var request = HttpRequest.newBuilder(uriBuilder.build())
                .header("Content-Type", "application/json")
                .GET()
                .build();

            return sendRequest(request, response, error);
        } catch (URISyntaxException e) {
            LOGGER.error("An error has occurred while attempting to call {} and params {}", uri, params, e);
            throw new RestClientException(e);
        } catch (InterruptedException e) {
            LOGGER.error("HttpClient send has been interrupted", e);
            Thread.currentThread().interrupt();
            throw new RestClientException(e);
        } catch (ExecutionException e) {
            LOGGER.error("HttpClient has faced an execution error", e);
            throw new RestClientException(e);
        } finally {
            timer.stop();
            LOGGER.debug(timer.toString());
        }
    }

    public <R, E, P> RestResponse<R, E> POST(String uri, P payload, Class<R> response, Class<E> error, Map<String, String> params) throws RestClientException {
        try {
            timer.start();
            var uriBuilder = new URIBuilder(uri);
            params.forEach(uriBuilder::addParameter);

            var request = HttpRequest
                .newBuilder(uriBuilder.build())
                .header("Content-Type", "application/json")
                .POST(new CustomJsonBodyPublisher().getPublisher(payload))
                .build();

            return sendRequest(request, response, error);
        } catch (URISyntaxException | JsonProcessingException | ExecutionException e) {
            LOGGER.error("An error has occurred while attempting to call {} and params {}", uri, params, e);
            throw new RestClientException(e);
        } catch (InterruptedException e){
            LOGGER.error("HttpClient send has been interrupted", e);
            Thread.currentThread().interrupt();
            throw new RestClientException(e);
        } finally {
            timer.stop();
            LOGGER.debug(timer.toString());
        }
    }

    public <R, E, P> RestResponse<R, E> PUT(String uri, P payload, Class<R> response, Class<E> error, Map<String, String> params) throws RestClientException {
        try {
            timer.start();
            var uriBuilder = new URIBuilder(uri);
            params.forEach(uriBuilder::addParameter);

            var request = HttpRequest
                .newBuilder(uriBuilder.build())
                .header("Content-Type", "application/json")
                .PUT(new CustomJsonBodyPublisher().getPublisher(payload))
                .build();

            return sendRequest(request, response, error);
        } catch (URISyntaxException | JsonProcessingException | ExecutionException e) {
            LOGGER.error("An error has occurred while attempting to call {} and params {}", uri, params, e);
            throw new RestClientException(e);
        } catch (InterruptedException e){
            LOGGER.error("HttpClient send has been interrupted", e);
            Thread.currentThread().interrupt();
            throw new RestClientException(e);
        } finally {
            timer.stop();
            LOGGER.debug(timer.toString());
        }
    }

    public <R, E, P> RestResponse<R, E> DELETE(String uri, P payload, Class<R> response, Class<E> error, Map<String, String> params) throws RestClientException {
        try {
            timer.start();
            var uriBuilder = new URIBuilder(uri);
            params.forEach(uriBuilder::addParameter);

            var request = HttpRequest
                .newBuilder(uriBuilder.build())
                .header("Content-Type", "application/json")
                .method(HttpMethod.DELETE.name(), new CustomJsonBodyPublisher().getPublisher(payload))
                .build();

            return sendRequest(request, response, error);
        } catch (URISyntaxException | JsonProcessingException | ExecutionException e) {
            LOGGER.error("An error has occurred while attempting to call {} and params {}", uri, params, e);
            throw new RestClientException(e);
        } catch (InterruptedException e){
            LOGGER.error("HttpClient send has been interrupted", e);
            Thread.currentThread().interrupt();
            throw new RestClientException(e);
        } finally {
            timer.stop();
            LOGGER.debug(timer.toString());
        }
    }

    public <R, E, P> RestResponse<R, E> PATCH(String uri, P payload, Class<R> response, Class<E> error, Map<String, String> params) throws RestClientException {
        try {
            timer.start();
            var uriBuilder = new URIBuilder(uri);
            params.forEach(uriBuilder::addParameter);

            var request = HttpRequest
                .newBuilder(uriBuilder.build())
                .header("Content-Type", "application/json")
                .method(HttpMethod.PATCH.name(), new CustomJsonBodyPublisher().getPublisher(payload))
                .build();

            return sendRequest(request, response, error);
        } catch (URISyntaxException | JsonProcessingException | ExecutionException e) {
            LOGGER.error("An error has occurred while attempting to call {} and params {}", uri, params, e);
            throw new RestClientException(e);
        } catch (InterruptedException e){
            LOGGER.error("HttpClient send has been interrupted", e);
            Thread.currentThread().interrupt();
            throw new RestClientException(e);
        } finally {
            timer.stop();
            LOGGER.debug(timer.toString());
        }
    }

    private <R, E> RestResponse<R, E> sendRequest(HttpRequest request, Class<R> response, Class<E> error) throws ExecutionException, InterruptedException {
        return client
            .sendAsync(request, new CustomerJsonBodyHandler<>(response, error))
            .thenApply(HttpResponse::body)
            .get()
            .get();
    }

    private static HttpClient createConfiguredClient() {
        return HttpClient.newBuilder()
            .executor(EXECUTOR)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .version(HttpClient.Version.HTTP_2)
            .build();
    }

}
