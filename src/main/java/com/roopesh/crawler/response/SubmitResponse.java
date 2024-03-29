package com.roopesh.crawler.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubmitResponse {
    @JsonProperty("id")
    private String id;

    public SubmitResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
