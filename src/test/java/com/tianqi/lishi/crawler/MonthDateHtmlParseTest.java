package com.tianqi.lishi.crawler;

import com.tianqi.lishi.model.DateInfo;
import org.junit.Test;

import java.util.List;

/**
 * Created by jennifert on 2016/6/15.
 */
public class MonthDateHtmlParseTest {
    @Test
    public void parse() throws Exception {
        DateHtmlParse dateHtmlParse = new DateHtmlParse("http://lishi.tianqi.com/acheng/index.html");
        dateHtmlParse.parse();
        List<DateInfo> dateInfos = dateHtmlParse.getDateInfoList();
    }

}