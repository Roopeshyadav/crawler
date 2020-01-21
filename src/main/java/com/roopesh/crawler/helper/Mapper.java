package com.roopesh.crawler.helper;

import com.roopesh.crawler.model.BasePage;
import com.roopesh.crawler.response.DataResponse;
import org.springframework.beans.BeanUtils;

public class Mapper {

    public static DataResponse convertToDataResponse(BasePage basePage){
        DataResponse dataResponse = null;
        if(basePage != null) {
            dataResponse = new DataResponse();
            BeanUtils.copyProperties(basePage, dataResponse);
        }
        return dataResponse;
    }
}
