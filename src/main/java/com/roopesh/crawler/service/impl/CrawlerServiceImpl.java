package com.roopesh.crawler.service.impl;

import com.roopesh.crawler.constant.Constant;
import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.exception.RequestFailedException;
import com.roopesh.crawler.helper.Mapper;
import com.roopesh.crawler.repository.CrawlerDataRepository;
import com.roopesh.crawler.request.DataRequest;
import com.roopesh.crawler.request.SubmitRequest;
import com.roopesh.crawler.response.DataResponse;
import com.roopesh.crawler.service.CrawlerService;
import com.roopesh.crawler.service.QueueService;
import com.roopesh.crawler.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;


@Service
public class CrawlerServiceImpl implements CrawlerService {
    Logger logger = LoggerFactory.getLogger(CrawlerServiceImpl.class);

    @Autowired
    private QueueService queueService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CrawlerDataRepository crawlerDataRepository;


    public String deepCrawl(SubmitRequest submitRequest){
        try {
            queueService.submit(submitRequest.getUrl(), submitRequest.getDepth());
            logger.info("id: {} url: {} depth: {} status changed to SUBMITTED",MDC.get(Constant.MDC_REQ_ID), submitRequest.getUrl(), submitRequest.getDepth());
        }catch(IllegalStateException e){
            logger.error(e.getMessage());
            return null;
        }
        return MDC.get(Constant.MDC_REQ_ID);

    }

    public DataResponse getCrawlData(DataRequest dataRequest){
        DataResponse dataResponse = Mapper.convertToDataResponse(crawlerDataRepository.get(dataRequest.getId()));
        if(dataResponse == null && statusService.getStatus(dataRequest.getId()) == null){
            throw new NoSuchElementException("Id does not exist in the system");
        }
        if(statusService.getStatus(dataRequest.getId()) == Status.FAILED){
            throw new RequestFailedException("Request status is Failed");
        }
        return  dataResponse;
    }


}
