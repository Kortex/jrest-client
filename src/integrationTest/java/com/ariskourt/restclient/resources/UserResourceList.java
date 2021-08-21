package com.ariskourt.restclient.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserResourceList {

    private Integer page;
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    @JsonProperty("per_page")
    private Integer perPage;
    public Integer getPerPage() { return perPage; }
    public void setPerPage(Integer perPage) { this.perPage = perPage; }

    private Integer total;
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }

    @JsonProperty("total_pages")
    private Integer totalPages;
    public Integer getTotalPages() { return totalPages; }
    public void setTotalPages(Integer totalPages) { this.totalPages = totalPages; }

    private List<UserResource> data;
    public List<UserResource> getData() { return data; }
    public void setData(List<UserResource> data) { this.data = data; }

    private SupportResource support;
    public SupportResource getSupport() { return support; }
    public void setSupport(SupportResource support) { this.support = support; }

    @Override
    public String toString() {
        return "UserResourceList{" +
            "page=" + page +
            ", perPage=" + perPage +
            ", total=" + total +
            ", totalPages=" + totalPages +
            ", data=" + data +
            '}';
    }

}
