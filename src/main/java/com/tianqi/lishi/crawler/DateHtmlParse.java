package com.tianqi.lishi.crawler;

import com.tianqi.lishi.model.DateInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jennifert on 2016/6/15.
 */
public class DateHtmlParse extends HtmlParse {
    protected List<DateInfo> dateInfoList = new ArrayList<DateInfo>();

    public List<DateInfo> getDateInfoList() {
        return dateInfoList;
    }

    public DateHtmlParse(String url) {
        this.url = url;
    }

    public void parse() {
        try {
            Document document = Jsoup.connect(url).get();
            Elements dateListElements = document.body().select("div[id=tool_site] > div[class=tqtongji1] li a");
            Iterator iterator = dateListElements.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                DateInfo dateInfo = new DateInfo();
                dateInfo.setDayDate(element.text().replace("天气", ""));
                dateInfo.setUrl(element.attr("href"));
                dateInfoList.add(dateInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(String className) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements dateListElements = document.body().select("div[id=tool_site] > div[class=" + className + "] li a");
            Iterator iterator = dateListElements.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                DateInfo dateInfo = new DateInfo();
                dateInfo.setDayDate(element.text().replace("天气", ""));
                dateInfo.setUrl(element.attr("href"));
                dateInfoList.add(dateInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
