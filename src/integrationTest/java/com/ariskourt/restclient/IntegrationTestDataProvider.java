package com.ariskourt.restclient;

import com.ariskourt.restclient.resources.RegisterResource;
import com.ariskourt.restclient.resources.UserModificationResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public class IntegrationTestDataProvider {

    static final Logger LOGGER = LogManager.getLogger(IntegrationTestDataProvider.class);
    static final String BASE_USERS_URL = "https://reqres.in/api/users";
    static final String REGISTER_USER_URL = "https://reqres.in/api/register";
    static final String USER_NAME = "morpheus";
    static final String USER_JOB = "leader";
    static final String REGISTERED_USER_EMAIL = "eve.holt@reqres.in";
    static final String REGISTERED_USER_PASSWORD = "pistol";

    public UserModificationResource createPayload() {
        var payload = new UserModificationResource();
        payload.setName(USER_NAME);
        payload.setJob(USER_JOB);
        return payload;
    }

    public RegisterResource createCorrectSignUpPayload() {
        var payload = new RegisterResource();
        payload.setEmail(REGISTERED_USER_EMAIL);
        payload.setPassword(REGISTERED_USER_PASSWORD);
        return payload;
    }

    public RegisterResource createFailedSignUpPayload() {
        var payload = new RegisterResource();
        payload.setEmail(REGISTERED_USER_EMAIL);
        return payload;
    }

}
