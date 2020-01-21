package com.roopesh.crawler.service;

import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.model.BasePage;
import com.roopesh.crawler.request.DataRequest;
import com.roopesh.crawler.request.StatusRequest;
import com.roopesh.crawler.request.SubmitRequest;
import com.roopesh.crawler.response.DataResponse;

import java.util.Map;

public interface CrawlerService {

    String deepCrawl(SubmitRequest submitRequest);

    DataResponse getCrawlData(DataRequest dataRequest);
}
