package com.tianqi.lishi.crawler;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * Created by jennifert on 2016/6/17.
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {
    static {
        try {
            Log4jConfigurer.initLogging("classpath:config/log4j.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Cannot Initialize log4j.");
        }
    }

    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
