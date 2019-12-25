package com.ariskourt.restclient;

import com.ariskourt.restclient.exceptions.RestClientException;

import java.util.Collections;

public class TestClass {

    public static void main(String... args) {
	try {
	    Response response = RestClient
		.getInstance()
		.POST("https://reqres.in/api/register", new Request("eve.holt@reqres.in", "pistol"), Response.class, Void.class, Collections.emptyMap())
		.getResponse();

	    System.out.println(response);


	} catch (RestClientException e) {
	    e.printStackTrace();
	}

    }

    public static class Request {
        private String email;
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	private String password;
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public Request() {
	}

	public Request(String email, String password) {
	    this.email = email;
	    this.password = password;
	}

	@Override
	public String toString() {
	    return "Request{" +
		"email='" + email + '\'' +
		", password='" + password + '\'' +
		'}';
	}
    }

    public static class Response {
        private Integer id;
	public Integer getId() { return id; }
	public void setId(Integer id) { this.id = id; }

	private String token;
	public String getToken() { return token; }
	public void setToken(String token) { this.token = token; }

	public Response() {
	}

	public Response(Integer id, String token) {
	    this.id = id;
	    this.token = token;
	}

	@Override
	public String toString() {
	    return "Response{" +
		"id=" + id +
		", token='" + token + '\'' +
		'}';
	}
    }


}
