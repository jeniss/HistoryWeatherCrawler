package com.tianqi.lishi.service.impl;

import com.tianqi.lishi.crawler.DateHtmlParse;
import com.tianqi.lishi.crawler.DayWeatherHtmlParse;
import com.tianqi.lishi.dao.WeatherDao;
import com.tianqi.lishi.model.DateInfo;
import com.tianqi.lishi.model.WeatherInfo;
import com.tianqi.lishi.service.WeatherService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jennifert on 2016/6/16.
 */
@Component
@Transactional
public class WeatherServiceImpl implements WeatherService {
    private Logger logger = Logger.getLogger(WeatherServiceImpl.class);

    @Autowired
    private WeatherDao weatherMapper;

    @Override
    public void crawlerDataToDB(String url, Date begin, Date end) {
        //parse month of year html
        DateHtmlParse monthDateHtmlParse = new DateHtmlParse(url);
        monthDateHtmlParse.parse();
        List<DateInfo> monthDateInfoList = monthDateHtmlParse.getDateInfoList();

        //print month date
        logger.info("--------months------");
        for (DateInfo dateInfo : monthDateInfoList) {
            logger.info(dateInfo.getDayDate() + ": " + dateInfo.getUrl());
        }

        //parse day of month html and to DB
        for (DateInfo dateInfo : monthDateInfoList) {
            //parse day of month html
            String dayUrl = dateInfo.getUrl();
            DateHtmlParse dayHtmlParse = new DateHtmlParse(dayUrl, "tqtongji2");
            dayHtmlParse.parse();
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
            weatherMapper.insertWeatherInfosBatch(weatherInfos);

            break;
        }
    }
}
