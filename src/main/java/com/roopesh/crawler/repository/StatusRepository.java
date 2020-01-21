package com.roopesh.crawler.repository;

import com.roopesh.crawler.constant.Status;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class StatusRepository {
    private ConcurrentHashMap<String, Status> statusMap;

    public StatusRepository() {
        statusMap = new ConcurrentHashMap<>();
    }

    public void put(String id, Status status) {
        statusMap.put(id,status);
    }

    public Status get(String id) {
        return statusMap.get(id);
    }

}
