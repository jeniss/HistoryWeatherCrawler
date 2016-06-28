package com.tianqi.lishi.crawler;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by jennifert on 2016/6/28.
 */
public class BaoCityHtmlParse {
    private Logger logger = Logger.getLogger(BaoCityHtmlParse.class);
    private final static String CITY_URL = "http://tianqihoubao.com/lishi/bj.htm";

    private String province;
    private String city;
    private String url;

    public String getUrl() {
        return url;
    }

    public BaoCityHtmlParse(String province, String city) {
        this.province = province;
        this.city = city;
    }

    public boolean parse() {
        Document document = null;
        try {
            //获取省 / 直辖市 / 自治区的url
            document = Jsoup.connect(CITY_URL).get();
            Elements provinceElements = document.select("div[class=box p] ul li a");
            Iterator provinceIterator = provinceElements.iterator();
            String provinceUrl = this.getUrlResult(provinceIterator);

            if (StringUtils.isEmpty(provinceUrl)) {
                logger.error(province + "的Url未找到！");
                return false;
            }

            document = Jsoup.connect(provinceUrl).get();
            Elements cityElements = document.select("div[class=citychk] dl dd a");
            Iterator cityIterator = cityElements.iterator();
            url = this.getUrlResult(cityIterator);
            if (StringUtils.isEmpty(url)) {
                logger.error(city + "的Url未找到！");
                return false;
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    private String getUrlResult(Iterator iterator) {
        String result = null;
        while (iterator.hasNext()) {
            Element element = (Element) iterator.next();
            if (city.equals(element.text())) {
                result = element.attr("href");
                logger.info(city + ": " + url);
                break;
            }
        }
        return result;
    }
}
