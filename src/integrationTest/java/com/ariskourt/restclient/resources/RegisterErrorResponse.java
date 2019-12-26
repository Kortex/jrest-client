package com.ariskourt.restclient.resources;

public class RegisterErrorResponse {

    private String error;
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    @Override
    public String toString() {
        return "RegisterErrorResponse{" +
            "error='" + error + '\'' +
            '}';
    }

}
