package com.tianqi.lishi.dao;

import com.tianqi.lishi.model.WeatherInfo;

import java.util.List;

/**
 * Created by jennifert on 2016/6/16.
 */
public interface WeatherDao {
    void insertWeatherInfosBatch(List<WeatherInfo> list);
}
