package com.tianqi.lishi.crawler;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by jennifert on 2016/6/28.
 */
public class BaoCityDateHtmlParse {
    private Logger logger = Logger.getLogger(BaoCityDateHtmlParse.class);
    private String htmlUrl;

    private Date startDate;
    private Date endDate;

    public BaoCityDateHtmlParse(String htmlUrl, Date startDate, Date endDate) {
        this.htmlUrl = htmlUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean parse() {
        Document document = null;
        try {
            document = Jsoup.connect(htmlUrl).get();
            Elements elements = document.select("div[class=wdetail] div[class=box pcity]");
            //获取每年的所有月份url
            Iterator iterator = elements.iterator();
            while (iterator.hasNext()) {

            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }
}
