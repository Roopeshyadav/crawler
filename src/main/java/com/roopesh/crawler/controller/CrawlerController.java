package com.roopesh.crawler.controller;

import com.roopesh.crawler.request.DataRequest;
import com.roopesh.crawler.request.SubmitRequest;
import com.roopesh.crawler.response.DataResponse;
import com.roopesh.crawler.response.SubmitResponse;
import com.roopesh.crawler.service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
public class CrawlerController {
    Logger logger = LoggerFactory.getLogger(CrawlerController.class);
    @Autowired
    private CrawlerService crawlerService;

    @PostMapping("/v1/deepcrawl")
    public ResponseEntity<SubmitResponse> deepCrawl(@RequestBody @Valid SubmitRequest submitRequest){
        String id = crawlerService.deepCrawl(submitRequest);
        if(id != null) {
            return new ResponseEntity<>(new SubmitResponse(id), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<>(new SubmitResponse("Can't Process the request right now"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/v1/data")
    public ResponseEntity<DataResponse> getData(@RequestBody @Valid DataRequest dataRequest){
        DataResponse dataResponse = crawlerService.getCrawlData(dataRequest);
        if(dataResponse != null) {
            return new ResponseEntity<>(dataResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(dataResponse, HttpStatus.ACCEPTED);
        }
    }

}
