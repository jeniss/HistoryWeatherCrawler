package com.tianqi.lishi.service;

import java.util.Date;

/**
 * Created by jennifert on 2016/6/16.
 */
public interface WeatherService {
    void crawlerDataToDB(String url, Date begin, Date end);
}
