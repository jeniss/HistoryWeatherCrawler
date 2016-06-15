package com.tianqi.lishi.crawler;

import org.junit.Test;

/**
 * Created by jennifert on 2016/6/15.
 */
public class WeatherCrawlerTest {
    @Test
    public void crawlerDataToDB() throws Exception {
        String url = "http://lishi.tianqi.com/ansai/index.html";
        WeatherCrawler weatherCrawler = new WeatherCrawler(url);
        weatherCrawler.crawlerDataToDB();
    }

}