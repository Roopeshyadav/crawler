package com.roopesh.crawler.service;

import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.request.StatusRequest;
import com.roopesh.crawler.response.StatusResponse;


public interface StatusService {

    void changeStatus(String id, Status status);

    Status getStatus(String id);

    StatusResponse getStatus(StatusRequest statusRequest);
}
