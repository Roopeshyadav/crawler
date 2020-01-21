package com.roopesh.crawler.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class StatusResponse {
    @JsonProperty("status_details")
    private List<StatusDetail> details;

    public StatusResponse() {
        details = new ArrayList<>();
    }

    public List<StatusDetail> getDetails() {
        return details;
    }

    public void setDetails(List<StatusDetail> details) {
        this.details = details;
    }

    public void addDetail(StatusDetail statusDetail){
        details.add(statusDetail);
    }
}
