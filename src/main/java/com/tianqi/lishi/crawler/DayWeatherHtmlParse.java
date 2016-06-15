package com.tianqi.lishi.crawler;

import com.tianqi.lishi.model.DayInfo;
import com.tianqi.lishi.model.NightInfo;
import com.tianqi.lishi.model.WeatherInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by jennifert on 2016/6/15.
 */
public class DayWeatherHtmlParse extends HtmlParse {
    protected WeatherInfo weatherInfo = new WeatherInfo();

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public DayWeatherHtmlParse(String url) {
        this.url = url;
    }

    public void parse() {
        try {
            Document document = Jsoup.connect(url).get();
            Element body = document.body();


            String wStr = "div[class=history_data_w]";
            //get the highest/lowest temperature
            Elements tempElements = body.select(wStr + " li span font");
            Object[] tempElementArray = tempElements.toArray();
            weatherInfo.setHighestTemp(((Element) tempElementArray[0]).text());
            weatherInfo.setLowestTemp(((Element) tempElementArray[1]).text());

            //get desc
            Element descElement = body.select(wStr + " li[class=cDRed]").iterator().next();
            weatherInfo.setDesc(descElement.text().replace("~", "转"));

            //get wind
            Element windElement = body.select(wStr + " li[style=height:18px;overflow:hidden]").iterator().next();
            weatherInfo.setWind(windElement.text());

            //get day/night weather info
            String dStr = "div[class=history_data_m] div[id=today]";
            Object[] dayNightInfo = body.select(dStr).toArray();
            //day weather info
            DayInfo dayInfo = new DayInfo();
            Element dayElement = (Element) dayNightInfo[0];
            dayInfo.setDesc(dayElement.select("li[class=cDRed]").text());
            String[] dayWeatherPicUrlArray = dayElement.select("li img").attr("src").split("/.");
            dayInfo.setWeatherPic(dayWeatherPicUrlArray[dayWeatherPicUrlArray.length - 1]);
            weatherInfo.setDayInfo(dayInfo);

            //night weather info
            NightInfo nightInfo = new NightInfo();
            Element nightElement = (Element) dayNightInfo[1];
            nightInfo.setDesc(nightElement.select("li[class=cDRed]").text());
            String[] nightWeatherPicUrlArray = nightElement.select("li img").attr("src").split("/.");
            nightInfo.setWeatherPic(dayWeatherPicUrlArray[nightWeatherPicUrlArray.length - 1]);
            weatherInfo.setNightInfo(nightInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
