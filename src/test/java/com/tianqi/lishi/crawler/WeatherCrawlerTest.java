package com.tianqi.lishi.crawler;

import com.tianqi.lishi.service.WeatherService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by jennifert on 2016/6/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext.xml"})
public class WeatherCrawlerTest extends TestCase {
    @Autowired
    private WeatherService weatherService;

    @Test
    public void testCrawlerDataToDB() {
        weatherService.crawlerDataToDB("http://lishi.tianqi.com/ansai/index.html", null, null);
    }
}