package com.roopesh.crawler.controller;

import com.roopesh.crawler.request.StatusRequest;
import com.roopesh.crawler.response.StatusResponse;
import com.roopesh.crawler.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;

    @PostMapping("/v1/status")
    public ResponseEntity<StatusResponse> getStatus(@RequestBody @Valid StatusRequest statusRequest){
        StatusResponse statusResponse = statusService.getStatus(statusRequest);
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }
}
