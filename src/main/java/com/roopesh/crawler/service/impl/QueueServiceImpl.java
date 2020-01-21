package com.roopesh.crawler.service.impl;

import com.roopesh.crawler.constant.Constant;
import com.roopesh.crawler.constant.Status;
import com.roopesh.crawler.model.QueueData;
import com.roopesh.crawler.service.QueueService;
import com.roopesh.crawler.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class QueueServiceImpl implements QueueService {
    Logger logger = LoggerFactory.getLogger(QueueServiceImpl.class);

    @Autowired
    private StatusService statusService;

    private Queue<QueueData> q;
    public QueueServiceImpl() {
        this.q = new LinkedList<>();
    }

    public synchronized  void submit(String url, int depth){
        QueueData queueData = new QueueData(MDC.get(Constant.MDC_REQ_ID), url, depth);
        this.notify();
        q.add(queueData);
        statusService.changeStatus(MDC.get(Constant.MDC_REQ_ID), Status.SUBMITTED);
        logger.info("url: {} and depth: {} added", url, depth);
    }

    public synchronized QueueData get(){
        QueueData queueData = null;
        try {
            wait();
        }catch (InterruptedException e){
            logger.error(e.getMessage());
        }
        queueData = q.poll();
        logger.info("id: {} url: {} and depth: {} removed", queueData.getId(), queueData.getUrl(), queueData.getDepth());
        return queueData;
    }
}
