package com.ariskourt.restclient;

import com.ariskourt.restclient.exceptions.RestClientException;
import com.ariskourt.restclient.resources.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class RestClientIntTest extends IntegrationTestDataProvider {

    @Test
    public void GET_WhenResourceIsList_ResponseIsReturnedCorrectly() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .GET(BASE_USERS_URL, UserResourceList.class, Void.class, Map.of("page", "2"));
        var resourceList = restResponse.getResponse();
        var error = restResponse.getError();

        LOGGER.info("Got the following response {}", resourceList);
        LOGGER.info("Got the following error {}", error);
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(resourceList),
            () -> assertNull(error),
            () -> assertEquals(200, restResponse.getCode()),
            () -> assertThat(resourceList).isInstanceOf(UserResourceList.class),
            () -> assertEquals(2, resourceList.getPage()),
            () -> assertEquals(6, resourceList.getPerPage()),
            () -> assertEquals(12, resourceList.getTotal()),
            () -> assertEquals(2, resourceList.getTotalPages()),
            () -> assertThat(resourceList.getData()).isNotEmpty(),
            () -> assertEquals(6, resourceList.getData().size()),
            () -> assertThat(resourceList.getData()).allSatisfy(Assertions::assertNotNull)
        );
    }

    @Test
    public void GET_WhenResourceIsSingleItem_ResponseIsReturnedCorrectly() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .GET(BASE_USERS_URL + "/2", SingleUserResource.class, Void.class, Collections.emptyMap());

        var resource = restResponse.getResponse();
        var userResource = resource.getData();
        var error = restResponse.getError();
        LOGGER.info("Got the following response {}", resource);
        LOGGER.info("Got the following error {}", error);
        assertAll(
            () -> assertNotNull(resource),
            () -> assertNull(error),
            () -> assertEquals(200, restResponse.getCode()),
            () -> assertNotNull(userResource),
            () -> assertNotNull(userResource.getId()),
            () -> assertNotNull(userResource.getEmail()),
            () -> assertNotNull(userResource.getFirstName()),
            () -> assertNotNull(userResource.getLastName()),
            () -> assertNotNull(userResource.getAvatar())
        );
    }

    @Test
    public void GET_WhenResourceGives404_ResponseIsReturnedCorrectly() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .GET(BASE_USERS_URL + "/23", SingleUserResource.class, Void.class, Collections.emptyMap());

        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertEquals(404, restResponse.getCode()),
            () -> assertNull(restResponse.getResponse())
        );
    }

    @Test
    public void POST_WhenResourceIsHandled_VerifyCorrectResponseAndBehavior() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .POST(BASE_USERS_URL, createPayload(), UserModificationResponse.class, Void.class, Collections.emptyMap());

        var response = restResponse.getResponse();
        LOGGER.info("Got the following response {}", response);
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(response),
            () -> assertEquals(201, restResponse.getCode()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(response.getName()),
            () -> assertEquals(USER_NAME, response.getName()),
            () -> assertEquals(USER_JOB, response.getJob()),
            () -> assertNotNull(response.getId()),
            () -> assertNotNull(response.getCreatedAt())
        );
    }

    @Test
    public void PUT_WhenResourceIsHandled_VerifyCorrectResponseAndBehavior() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .PUT(BASE_USERS_URL + "/2", createPayload(), UserModificationResponse.class, Void.class, Collections.emptyMap());
        var response = restResponse.getResponse();
        LOGGER.info("Got the following response {}", response);
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(response),
            () -> assertEquals(200, restResponse.getCode()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(response.getName()),
            () -> assertEquals(USER_NAME, response.getName()),
            () -> assertEquals(USER_JOB, response.getJob()),
            () -> assertNotNull(response.getUpdatedAt())
        );
    }

    @Test
    public void PATCH_WhenResourceIsHandled_VerifyCorrectResponseAndBehavior() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .PATCH(BASE_USERS_URL + "/2", createPayload(), UserModificationResponse.class, Void.class, Collections.emptyMap());
        var response = restResponse.getResponse();
        LOGGER.info("Got the following response {}", response);
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(response),
            () -> assertEquals(200, restResponse.getCode()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(response.getName()),
            () -> assertEquals(USER_NAME, response.getName()),
            () -> assertEquals(USER_JOB, response.getJob()),
            () -> assertNotNull(response.getUpdatedAt())
        );
    }

    @Test
    public void DELETE_WhenResourceIsHandled_VerifyCorrectResponseAndBehavior() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .DELETE(BASE_USERS_URL + "/2", null, Void.class, Void.class, Collections.emptyMap());
        assertAll(() -> assertEquals(204, restResponse.getCode()));
    }

    @Test
    public void POST_RegisterWhenResourceIsHandled_VerifyCorrectResponseAndBehavior() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .POST(REGISTER_USER_URL, createCorrectSignUpPayload(), RegisterResponse.class, Void.class, Collections.emptyMap());
        var response = restResponse.getResponse();
        LOGGER.info("Got the following response {}", response);
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNotNull(response),
            () -> assertEquals(200, restResponse.getCode()),
            () -> assertNull(restResponse.getError()),
            () -> assertNotNull(response.getId()),
            () -> assertNotNull(response.getToken())
        );
    }

    @Test
    public void POST_RegisterWhenResourceIsNotHandled_VerifyCorrectResponseAndBehavior() throws RestClientException {
        var restResponse = RestClient
            .getInstance()
            .POST(REGISTER_USER_URL, createFailedSignUpPayload(), RegisterResponse.class, RegisterErrorResponse.class, Collections.emptyMap());
        var error = restResponse.getError();
        LOGGER.info("Got the following error response {}", error);
        assertAll(
            () -> assertNotNull(restResponse),
            () -> assertNull(restResponse.getResponse()),
            () -> assertEquals(400, restResponse.getCode()),
            () -> assertNotNull(error),
            () -> assertNotNull(error.getError())
        );
    }

}
