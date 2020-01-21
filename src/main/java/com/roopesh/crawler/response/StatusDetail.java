package com.roopesh.crawler.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roopesh.crawler.constant.Status;

public class StatusDetail {
    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private Status status;

    public StatusDetail() {
    }

    public StatusDetail(String id, Status status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
