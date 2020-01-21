package com.roopesh.crawler.request;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class StatusRequest {

    @NotEmpty
    private List<String> requestIds;

    public StatusRequest() {
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }
}
