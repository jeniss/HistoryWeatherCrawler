package com.tianqi.lishi.service.impl;

import com.tianqi.lishi.crawler.CityHtmlParse;
import com.tianqi.lishi.crawler.DateHtmlParse;
import com.tianqi.lishi.crawler.DayWeatherHtmlParse;
import com.tianqi.lishi.dao.WeatherDao;
import com.tianqi.lishi.model.DateInfo;
import com.tianqi.lishi.model.WeatherInfo;
import com.tianqi.lishi.service.WeatherService;
import org.apache.commons.collections.CollectionUtils;
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

    /**
     * @param cityName
     * @param cityNameAlias
     * @param beginDateStr yyyy-MM-dd
     * @param endDateStr yyyy-MM-dd
     */
    @Override
    public void crawlerDataToDB(String cityName, String cityNameAlias, String beginDateStr, String endDateStr) {
        //get the city url
        CityHtmlParse cityHtmlParse = new CityHtmlParse(cityName);
        cityHtmlParse.parse();
        String url = cityHtmlParse.getUrl();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;
        try {
            if (beginDateStr != null) {
                beginDate = dateFormat.parse(beginDateStr);
            }
            if (endDateStr != null) {
                endDate = dateFormat.parse(endDateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //parse month of year html
        DateHtmlParse monthDateHtmlParse = new DateHtmlParse(url, beginDate, endDate);
        monthDateHtmlParse.setDateFormatStr("yyyy年MM月");
        monthDateHtmlParse.parse();
        List<DateInfo> monthDateInfoList = monthDateHtmlParse.getDateInfoList();

        //print month date
        logger.info("--------months------");
        if (CollectionUtils.isNotEmpty(monthDateInfoList)) {
            for (DateInfo dateInfo : monthDateInfoList) {
                logger.info(dateInfo.getDayDate() + ": " + dateInfo.getUrl());
            }
        } else {
            logger.info("无该数据。 URL：" + url);
        }

        //parse day of month html and to DB
        for (DateInfo dateInfo : monthDateInfoList) {
            //parse day of month html
            String dayUrl = dateInfo.getUrl();
            DateHtmlParse dayHtmlParse = new DateHtmlParse(dayUrl, "tqtongji2", beginDate, endDate);
            dayHtmlParse.setDateFormatStr("yyyy-MM-dd");
            dayHtmlParse.parse();
            List<DateInfo> dayDateInfoList = dayHtmlParse.getDateInfoList();

            //print day date
            logger.info("--------days------");
            if (CollectionUtils.isNotEmpty(dayDateInfoList)) {
                for (DateInfo dayDateInfo : dayDateInfoList) {
                    logger.info(dayDateInfo.getDayDate() + ": " + dayDateInfo.getUrl());
                }
            } else {
                logger.info(dateInfo.getDayDate() + " 无该数据。 URL：" + url);
            }

            List<WeatherInfo> weatherInfos = new ArrayList<WeatherInfo>();
            for (DateInfo dayDateInfo : dayDateInfoList) {
                String dayWeatherUrl = dayDateInfo.getUrl();
                //parse day weather html
                DayWeatherHtmlParse dayWeatherHtmlParse = new DayWeatherHtmlParse(dayWeatherUrl);
                dayWeatherHtmlParse.parse();
                WeatherInfo weatherInfo = dayWeatherHtmlParse.getWeatherInfo();

                Date curDate = null;
                try {
                    dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    curDate = dateFormat.parse(dayDateInfo.getDayDate());
                    weatherInfo.setDate(curDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (cityNameAlias != null) {
                    weatherInfo.setCity(cityNameAlias);
                } else {
                    weatherInfo.setCity(cityName);
                }

                weatherInfo.setPublishDate(curDate);
                weatherInfo.setServertime(new Date());

                weatherInfos.add(weatherInfo);
            }

            //save to DB
            if (CollectionUtils.isNotEmpty(weatherInfos)) {
                weatherMapper.insertWeatherInfosBatch(weatherInfos);
            }
        }
    }
}
