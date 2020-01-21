package com.roopesh.crawler.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageResponse {

    @JsonProperty("page_link")
    private String pageLink;

    @JsonProperty("page_title")
    private String pageTitle;

    @JsonProperty("image_count")
    private int imageCount;

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }
}
