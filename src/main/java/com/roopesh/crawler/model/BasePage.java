package com.roopesh.crawler.model;

import java.util.ArrayList;
import java.util.List;

public class BasePage {
    private int totalLinks;
    private int totalImages;
    private List<PageDetails> details;

    public BasePage() {

    }

    public BasePage(int totalLinks, int totalImages, List<PageDetails> details) {
        this.totalLinks = totalLinks;
        this.totalImages = totalImages;
        this.details = details;
    }

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

    public List<PageDetails> getDetails() {
        return details;
    }

    public void setDetails(List<PageDetails> details) {
        this.details = details;
    }

    public void addPageDetails(PageDetails pageDetails){
        if(details == null){
            details = new ArrayList<>();
        }
        details.add(pageDetails);
    }

    public void addInTotalLinks(int totalLinks){
        this.totalLinks += totalLinks;
    }

    public void addInTotalImages(int totalImages){
        this.totalImages += totalImages;
    }
}
