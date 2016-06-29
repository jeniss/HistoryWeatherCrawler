package com.tianqi.lishi.crawler;

import com.tianqi.lishi.service.WeatherService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by jennifert on 2016/6/16.
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/applicationContext.xml"})
public class WeatherCrawlerTest extends TestCase {
    @Autowired
    private WeatherService weatherService;

    @Test
    public void testCrawlerDataToDB() {
        weatherService.crawlerDataToDB("阿瓦提县", null, "2014-12-01", "2015-02-03");
    }

    @Test
    public void testCrawlerToDBFromHouBao() {
        weatherService.crawlerToDBFromHouBao("山西", "太原", null, "2014-12-01", "2015-02-03");
    }
}