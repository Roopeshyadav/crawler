package com.roopesh.crawler.filter;

import com.roopesh.crawler.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestFilter extends HttpFilter {
    Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    public RequestFilter() {
        super();
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        long time = System.currentTimeMillis();
        MDC.put(Constant.MDC_REQ_ID, UUID.randomUUID().toString());
        super.doFilter(request, response, chain);
        logger.info("Time taken by API: {} is {}ms",request.getRequestURI(),(System.currentTimeMillis()-time));
    }
}
