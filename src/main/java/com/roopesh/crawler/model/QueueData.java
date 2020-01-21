package com.roopesh.crawler.model;

public class QueueData {
    private String id;
    private String url;
    private int depth;

    public QueueData(String id, String url, int depth) {
        this.id = id;
        this.url = url;
        this.depth = depth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
