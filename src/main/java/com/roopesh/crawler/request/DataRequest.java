package com.roopesh.crawler.request;

import javax.validation.constraints.NotNull;

public class DataRequest {
    @NotNull
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
