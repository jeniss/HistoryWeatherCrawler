package com.tianqi.lishi.crawler;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jennifert on 2016/6/28.
 */
public class BaoCityDateHtmlParse {
    private Logger logger = Logger.getLogger(BaoCityDateHtmlParse.class);
    private String htmlUrl;

    private Date startDate;
    private Date endDate;

    private List<String> monthUrlList;

    public List<String> getMonthUrlList() {
        return monthUrlList;
    }

    public BaoCityDateHtmlParse(String htmlUrl, Date startDate, Date endDate) {
        this.htmlUrl = htmlUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        monthUrlList = new ArrayList<>();
    }

    public boolean parse() {
        Document document = null;
        try {
            document = Jsoup.connect(htmlUrl).get();
            Elements elements = document.select("div[class=wdetail] div[class=box pcity] a");
            //获取每个月份的url
            Iterator iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String monthUrl = element.attr("href");
                //判断该月份是否在查询条件之中
                DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
                String[] urlParse = monthUrl.split("/");
                String dateStr = urlParse[urlParse.length - 1].split("\\.")[0];
                Date curDate = dateFormat.parse(dateStr);
                boolean isParse = true;
                if (startDate != null && startDate.after(curDate)) {
                    isParse = false;
                }
                if (endDate != null && endDate.before(curDate)) {
                    isParse = false;
                }
                if (!isParse) {
                    continue;
                }

                String resultUrl = BaoCityHtmlParse.WEB_URL + monthUrl;
                logger.info(element.text() + ": " + resultUrl);
                monthUrlList.add(resultUrl);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return true;
    }
}
