package com.ariskourt.restclient.resources;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModificationResponse {

    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    private String job;
    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    private Integer id;
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    private Date createdAt;
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    private Date updatedAt;
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
	return "UserModificationResponse{" +
	    "name='" + name + '\'' +
	    ", job='" + job + '\'' +
	    ", id=" + id +
	    ", createdAt=" + createdAt +
	    ", updatedAt=" + updatedAt +
	    '}';
    }

}
