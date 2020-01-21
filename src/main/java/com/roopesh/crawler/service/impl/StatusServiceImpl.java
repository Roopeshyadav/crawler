package com.roopesh.crawler.service.impl;

import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.repository.StatusRepository;
import com.roopesh.crawler.request.StatusRequest;
import com.roopesh.crawler.response.StatusDetail;
import com.roopesh.crawler.response.StatusResponse;
import com.roopesh.crawler.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public void changeStatus(String id, Status status) {
        statusRepository.put(id,status);
    }

    @Override
    public Status getStatus(String id) {
        return statusRepository.get(id);
    }

    public StatusResponse getStatus(StatusRequest statusRequest){
        StatusResponse statusResponse = new StatusResponse();
        for(String req: statusRequest.getRequestIds()){
            statusResponse.addDetail(new StatusDetail(req, statusRepository.get(req)));
        }
        return statusResponse;
    }
}
