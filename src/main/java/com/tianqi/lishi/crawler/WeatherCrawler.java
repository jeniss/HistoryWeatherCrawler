package com.tianqi.lishi.crawler;

import com.tianqi.lishi.db.MySQLManager;
import com.tianqi.lishi.model.DateInfo;
import com.tianqi.lishi.model.WeatherInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jennifert on 2016/6/15.
 */
public class WeatherCrawler {
    private String url;

    public WeatherCrawler(String url) {
        this.url = url;
    }

    public void crawlerDataToDB() {
        //parse month of year html
        DateHtmlParse monthDateHtmlParse = new DateHtmlParse(url);
        monthDateHtmlParse.parse();
        List<DateInfo> monthDateInfoList = monthDateHtmlParse.getDateInfoList();

        //print month date
        System.out.println("--------month------");
        for (DateInfo dateInfo : monthDateInfoList) {
            System.out.println(dateInfo.getDayDate() + ": " + dateInfo.getUrl());
        }

        //parse day of month html and to DB
        for (DateInfo dateInfo : monthDateInfoList) {
            //parse day of month html
            String dayUrl = dateInfo.getUrl();
            DateHtmlParse dayHtmlParse = new DateHtmlParse(dayUrl);
            dayHtmlParse.parse("tqtongji2");
            List<DateInfo> dayDateInfoList = dayHtmlParse.getDateInfoList();

            List<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();
            for (DateInfo dayDateInfo : dayDateInfoList) {
                String dayWeatherUrl = dayDateInfo.getUrl();
                //parse day weather html
                DayWeatherHtmlParse dayWeatherHtmlParse = new DayWeatherHtmlParse(dayWeatherUrl);
                dayWeatherHtmlParse.parse();
                WeatherInfo weatherInfo = dayWeatherHtmlParse.getWeatherInfo();

                try {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    weatherInfo.setDate(dateFormat.parse(dayDateInfo.getDayDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                weatherInfos.add(weatherInfo);
            }

            //save to DB
            MySQLManager.getInstance().getConnection();
            MySQLManager.getInstance().insertBatch(weatherInfos);
        }
    }
}
