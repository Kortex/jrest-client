package com.ariskourt.restclient.resources;

public class SingleUserResource {

    private UserResource data;
    public UserResource getData() { return data; }
    public void setData(UserResource data) { this.data = data; }

    private SupportResource support;
    public SupportResource getSupport() { return support; }
    public void setSupport(SupportResource support) { this.support = support; }

}
