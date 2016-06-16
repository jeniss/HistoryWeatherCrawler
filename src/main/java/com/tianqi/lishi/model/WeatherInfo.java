package com.tianqi.lishi.model;

import java.util.Date;
import java.util.List;

/**
 * Created by jennifert on 2016/6/15.
 */
public class WeatherInfo {
    private String city;
    private Date date;
    private String highestTemp;
    private String lowestTemp;
    private String desc;
    private String wind;
    private String weatherPic;
    private DayInfo dayInfo;
    private NightInfo nightInfo;
    private List<LifeTips> lifeTipsList;
    private Date publishDate;
    private Date servertime;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getServertime() {
        return servertime;
    }

    public void setServertime(Date servertime) {
        this.servertime = servertime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHighestTemp() {
        return highestTemp;
    }

    public void setHighestTemp(String highestTemp) {
        this.highestTemp = highestTemp;
    }

    public String getLowestTemp() {
        return lowestTemp;
    }

    public void setLowestTemp(String lowestTemp) {
        this.lowestTemp = lowestTemp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeatherPic() {
        return weatherPic;
    }

    public void setWeatherPic(String weatherPic) {
        this.weatherPic = weatherPic;
    }

    public DayInfo getDayInfo() {
        return dayInfo;
    }

    public void setDayInfo(DayInfo dayInfo) {
        this.dayInfo = dayInfo;
    }

    public NightInfo getNightInfo() {
        return nightInfo;
    }

    public void setNightInfo(NightInfo nightInfo) {
        this.nightInfo = nightInfo;
    }

    public List<LifeTips> getLifeTipsList() {
        return lifeTipsList;
    }

    public void setLifeTipsList(List<LifeTips> lifeTipsList) {
        this.lifeTipsList = lifeTipsList;
    }
}
