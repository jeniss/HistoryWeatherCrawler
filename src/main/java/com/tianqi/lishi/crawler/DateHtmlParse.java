package com.tianqi.lishi.crawler;

import com.tianqi.lishi.model.DateInfo;
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
 * Created by jennifert on 2016/6/15.
 */
public class DateHtmlParse {
    protected List<DateInfo> dateInfoList = new ArrayList<DateInfo>();
    private String className = "tqtongji1";

    private Date beginDate;

    private Date endDate;

    private String dateFormatStr;

    private String url;

    public void setDateFormatStr(String dateFormatStr) {
        this.dateFormatStr = dateFormatStr;
    }

    public List<DateInfo> getDateInfoList() {
        return dateInfoList;
    }

    public DateHtmlParse(String url) {
        this.url = url;
    }

    public DateHtmlParse(String url, Date begin, Date end) {
        this.url = url;
        this.beginDate = begin;
        this.endDate = end;
    }

    public DateHtmlParse(String url, String className) {
        this.url = url;
        this.className = className;
    }

    public DateHtmlParse(String url, String className, Date begin, Date end) {
        this.url = url;
        this.className = className;
        this.beginDate = begin;
        this.endDate = end;
    }

    /**
     * the order of date is desc in html
     */
    public void parse() {
        try {
            Document document = Jsoup.connect(url).get();
            Elements dateListElements = document.body().select("div[id=tool_site] > div[class=" + className + "] li a");
            Iterator iterator = dateListElements.iterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();

                String dateStr = element.text().replace("天气", "");

                DateFormat dateFormat = null;
                if (dateFormatStr != null) {
                    dateFormat = new SimpleDateFormat(dateFormatStr);

                    Date curDate = dateFormat.parse(dateStr);
                    if (beginDate != null) {
                        if (beginDate.after(curDate)) {
                            break;
                        }
                    }
                    if (endDate != null) {
                        if (endDate.before(curDate)) {
                            continue;
                        }
                    }
                }

                DateInfo dateInfo = new DateInfo();
                dateInfo.setDayDate(dateStr);
                dateInfo.setUrl(element.attr("href"));
                dateInfoList.add(dateInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
