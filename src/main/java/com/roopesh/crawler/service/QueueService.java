package com.roopesh.crawler.service;

import com.roopesh.crawler.model.QueueData;

public interface QueueService {

    void submit(String url, int depth);

    QueueData get();
}
