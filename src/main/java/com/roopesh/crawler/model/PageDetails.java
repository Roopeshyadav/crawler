package com.roopesh.crawler.model;

public class PageDetails {
    private String pageLink;
    private String pageTitle;
    private int image_count;

    public PageDetails() {

    }

    public PageDetails(String pageLink, String pageTitle, int image_count) {
        this.pageLink = pageLink;
        this.pageTitle = pageTitle;
        this.image_count = image_count;
    }

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

    public int getImage_count() {
        return image_count;
    }

    public void setImage_count(int image_count) {
        this.image_count = image_count;
    }
}
