package com.tianqi.lishi.service;

/**
 * Created by jennifert on 2016/6/16.
 */
public interface WeatherService {
    void crawlerDataToDB(String cityName, String cityNameAlias, String beginDate, String endDate);

    boolean crawlerToDBFromHouBao(String province, String cityName, String cityNameAlias, String beginDateStr, String endDateStr);
}
