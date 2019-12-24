package com.ariskourt.restclient;

import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpResponse;

public class MockDataProvider {

    public static final String TARGET_MESSAGE = "Some Data";
    public static final String ERROR_MESSAGE = "Some Error Message";

    public static class TargetClass {

        private Integer code;
	public Integer getCode() { return code; }
	public void setCode(Integer code) { this.code = code; }

	private String data;
	public String getData() { return data; }
	public void setData(String data) { this.data = data; }

	public TargetClass(Integer code, String data) {
	    this.code = code;
	    this.data = data;
	}

    }

    public static class ErrorClass {

        private Integer code;
	public Integer getCode() { return code; }
	public void setCode(Integer code) { this.code = code; }

	private String error;
	public String getError() { return error; }
	public void setError(String error) { this.error = error; }

	public ErrorClass(Integer code, String error) {
	    this.code = code;
	    this.error = error;
	}

    }

    public TargetClass createTarget(Integer code, String data) {
        return new TargetClass(code, data);
    }

    public ErrorClass createError(Integer code, String error) {
        return new ErrorClass(code, error);
    }

    public HttpResponse.ResponseInfo createFor(Integer code) {
        return new HttpResponse.ResponseInfo() {
	    @Override
	    public int statusCode() {
		return code;
	    }

	    @Override
	    public HttpHeaders headers() {
		return null;
	    }

	    @Override
	    public HttpClient.Version version() {
		return null;
	    }
	};
    }

}
