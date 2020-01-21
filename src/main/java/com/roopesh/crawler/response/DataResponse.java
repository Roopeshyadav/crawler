package com.roopesh.crawler.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class DataResponse {

    @JsonProperty("total_links")
    private int totalLinks;

    @JsonProperty("total_images")
    private int totalImages;

    @JsonProperty("details")
    private List<PageResponse> details;

    public int getTotalLinks() {
        return totalLinks;
    }

    public void setTotalLinks(int totalLinks) {
        this.totalLinks = totalLinks;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public void setTotalImages(int totalImages) {
        this.totalImages = totalImages;
    }

    public List<PageResponse> getDetails() {
        return details;
    }

    public void setDetails(List<PageResponse> details) {
        this.details = details;
    }
}
