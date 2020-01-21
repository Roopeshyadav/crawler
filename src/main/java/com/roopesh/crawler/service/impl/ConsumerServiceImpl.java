package com.roopesh.crawler.service.impl;

import com.roopesh.crawler.constant.Constant;
import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.model.BasePage;
import com.roopesh.crawler.model.PageDetails;
import com.roopesh.crawler.model.QueueData;
import com.roopesh.crawler.repository.CrawlerDataRepository;
import com.roopesh.crawler.service.ConsumerService;
import com.roopesh.crawler.service.QueueService;
import com.roopesh.crawler.service.StatusService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Value("${CONSUMER_POOL_SIZE}")
    private int consumerPoolSize;

    @Value("${Jsoup.timeout}")
    private int jsoupTimeOut;

    @Autowired
    private QueueService queueService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private CrawlerDataRepository crawlerDataRepository;

    @PostConstruct
    public void init(){
        logger.info("jsoupTimeOut: {}",jsoupTimeOut);
        logger.info("No. of consumers running: {}",consumerPoolSize);
        for(int i=0; i<consumerPoolSize; i++) {
            new Thread(() -> this.startConsuming()).start();
        }
    }

    public void startConsuming() {
        logger.info("started consuming");
        while (true) {
            QueueData queueData = queueService.get();
            if(queueData != null){
                statusService.changeStatus(queueData.getId(), Status.INPROGRESS);
                logger.info("id: {} url: {} depth: {} status changed to INPROGRESS",queueData.getId(), queueData.getUrl(), queueData.getDepth());
                BasePage basePage = doCrawl(queueData.getUrl(), queueData.getDepth());
                crawlerDataRepository.put(queueData.getId(), basePage);
                if(basePage.getTotalLinks() > 0){
                    statusService.changeStatus(queueData.getId(), Status.PROCESSED);
                    logger.info("id: {} url: {} depth: {} status changed to PROCESSED",queueData.getId(), queueData.getUrl(), queueData.getDepth());
                }else{
                    statusService.changeStatus(queueData.getId(), Status.FAILED);
                    logger.info("id: {} url: {} depth: {} status changed to FAILED",queueData.getId(), queueData.getUrl(), queueData.getDepth());

                }
            }
        }
    }

    @Cacheable("crawlerCache")
    public BasePage doCrawl(String url, int depth){
        HashSet<String> links = new HashSet<>();
        BasePage basePage = new BasePage();
        deepCrawlUrl(url, depth, basePage, links);
        return basePage;
    }

    public boolean deepCrawlUrl(String url, int depth, BasePage basePage, Set<String> links){
        if(depth <= 0 || links.contains(url)){
            return true;
        }
        links.add(url);
        logger.debug("url: {} and depth: {}",url, depth);
        try {
            Document document = Jsoup.connect(url).timeout(jsoupTimeOut).get();
            int imgCount = crawlForImgs(url);
            PageDetails pageDetails = new PageDetails(url, document.title(), imgCount);
            basePage.addPageDetails(pageDetails);
            basePage.addInTotalLinks(1);
            basePage.addInTotalImages(imgCount);

            Elements linksOnPage = document.select(Constant.REF);
            for(Element page: linksOnPage){
                String nextUrl = page.attr("abs:href");
                deepCrawlUrl(nextUrl, depth - 1, basePage, links);
            }
        }catch (IOException e){
            logger.error(e.getMessage());
            return false;
        }catch (IllegalArgumentException e){
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }


    public int crawlForImgs(String url){
        long time = System.currentTimeMillis();
        int size = 0;
        try {
            Document document = Jsoup.connect(url).get();
            Elements images = document.select(Constant.IMG);
            size = images.size();
        }catch (IOException e){
            e.printStackTrace();
        }
        logger.debug("time taken by crawlForImgs for url: {} is: {}ms",url,(System.currentTimeMillis()-time));
        return size;
    }
}
