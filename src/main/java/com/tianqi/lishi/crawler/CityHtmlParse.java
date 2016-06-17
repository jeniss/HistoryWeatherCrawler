package com.tianqi.lishi.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by jennifert on 2016/6/17.
 */
public class CityHtmlParse {
    private final static String CITIES_URL = "http://lishi.tianqi.com";
    private String cityName;
    private String url;

    public CityHtmlParse(String cityName) {
        this.cityName = cityName;
    }

    public String getUrl() {
        return this.url;
    }

    public void parse() {
        Document document = null;
        try {
            document = Jsoup.connect(CITIES_URL).get();
            Elements dateListElements = document.body().select("div[id=tool_site] li a");
            Iterator iterator = dateListElements.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                if (cityName.equals(element.text())) {
                    this.url = element.attr("href");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
