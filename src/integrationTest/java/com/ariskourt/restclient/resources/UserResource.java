package com.ariskourt.restclient.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserResource {

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @JsonProperty("first_name")
    private String firstName;
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @JsonProperty("last_name")
    private String lastName;
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    private String avatar;
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    @Override
    public String toString() {
        return "UserResource{" +
            "id=" + id +
            ", email='" + email + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", avatar='" + avatar + '\'' +
            '}';
    }

}
