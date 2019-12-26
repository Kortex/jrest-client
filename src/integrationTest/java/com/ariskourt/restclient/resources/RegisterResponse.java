package com.ariskourt.restclient.resources;

public class RegisterResponse {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String token;
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    @Override
    public String toString() {
        return "RegisterResponse{" +
            "id=" + id +
            ", token='" + token + '\'' +
            '}';
    }

}
