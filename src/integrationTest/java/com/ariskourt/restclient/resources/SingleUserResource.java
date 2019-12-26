package com.ariskourt.restclient.resources;

public class SingleUserResource {

    private UserResource data;
    public UserResource getData() { return data; }
    public void setData(UserResource data) { this.data = data; }

    @Override
    public String toString() {
	return "SingleUserResource{" +
	    "data=" + data +
	    '}';
    }

}
