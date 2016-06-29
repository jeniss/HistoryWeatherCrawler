package com.tianqi.lishi.crawler;

import com.tianqi.lishi.model.WeatherInfo;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by jennifert on 2016/6/29.
 */
public class BaoCityDayHtmlParse {
    private Logger logger = Logger.getLogger(BaoCityDayHtmlParse.class);
    private String dayUrl;

    private List<String> dayUrlList;
    private List<WeatherInfo> weatherInfoList;

    private Date startDate;
    private Date endDate;
    private String cityName;
    private String cityAlias;

    public List<String> getDayUrlList() {
        return dayUrlList;
    }

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }

    public BaoCityDayHtmlParse(String dayUrl, Date startDate, Date endDate, String cityName, String cityAlias) {
        this.dayUrl = dayUrl;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cityName = cityName;
        this.cityAlias = cityAlias;
        dayUrlList = new ArrayList<>();
        weatherInfoList = new ArrayList<>();
    }

    public boolean parse() {
        Document document = null;
        //解析这个月的每天页面
        try {
            document = Jsoup.connect(dayUrl).get();
            Elements dayHtmlElements = document.select("div[class=wdetail] table tr");
            Iterator dayIterator = dayHtmlElements.iterator();
            while (dayIterator.hasNext()) {
                Element trElement = (Element) dayIterator.next();
                Elements tdElements = trElement.select("td");
                Element dayElement = tdElements.first().child(0);
                String dayUrl = dayElement.attr("href");
                String text = dayElement.text();
                //判断该日是否在查询条件之中
                DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                Date curDay = null;
                try {
                    curDay = dateFormat.parse(text);
                } catch (ParseException e) {
                    continue;
                }
                boolean isParse = true;
                if (startDate != null && startDate.after(curDay)) {
                    isParse = false;
                }
                if (endDate != null && endDate.before(curDay)) {
                    isParse = false;
                }
                if (!isParse) {
                    continue;
                }

//                String fullDayUrl = BaoCityHtmlParse.WEB_URL + dayUrl;
//                logger.info(text + ": " + fullDayUrl);
//                dayUrlList.add(fullDayUrl);

                String desc = tdElements.get(1).text().replace("/", "转");
                desc = desc.replace(" ", "");
                String[] temperatures = tdElements.get(2).text().split("/");
                String highTempStr = temperatures[0].trim();
                String lowTempStr = temperatures[1].trim();
                String lowTemp = lowTempStr.substring(0, lowTempStr.length() - 1);
                String highTemp = highTempStr.substring(0, highTempStr.length() - 1);
                WeatherInfo weatherInfo = new WeatherInfo();
                weatherInfo.setServertime(new Date());
                weatherInfo.setPublishDate(new Date());
                weatherInfo.setDate(curDay);
                weatherInfo.setDesc(desc);
                weatherInfo.setHighestTemp(Integer.parseInt(highTemp));
                weatherInfo.setLowestTemp(Integer.parseInt(lowTemp));
                if (StringUtils.isEmpty(cityAlias)) {
                    weatherInfo.setCity(cityName);
                } else {
                    weatherInfo.setCity(cityAlias);
                }
                logger.info(text + ", 最高温度：" + weatherInfo.getHighestTemp() + ", 最低温度：" + weatherInfo.getLowestTemp() + ", " + weatherInfo.getDesc());
                weatherInfoList.add(weatherInfo);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }
}
