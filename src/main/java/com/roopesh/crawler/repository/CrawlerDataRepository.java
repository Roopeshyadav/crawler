package com.roopesh.crawler.repository;

import com.roopesh.crawler.model.BasePage;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CrawlerDataRepository {
    private ConcurrentHashMap<String, BasePage> responseMap;

    public CrawlerDataRepository() {
        responseMap = new ConcurrentHashMap<>();
    }

    public void put(String id, BasePage basePage){
        responseMap.put(id, basePage);
    }

    public BasePage get(String id){
        return responseMap.get(id);
    }
}
