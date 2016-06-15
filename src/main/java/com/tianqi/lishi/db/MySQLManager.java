package com.tianqi.lishi.db;

import com.tianqi.lishi.model.WeatherInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by jennifert on 2016/6/15.
 */
public class MySQLManager {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private final static String DB_URL = "jdbc:mysql://localhost:3306/test?user=root&password=tyc0343&useUnicode=true&characterEncoding=UTF8";

    private static final MySQLManager instance = new MySQLManager();

    private MySQLManager() {
    }

    public static MySQLManager getInstance() {
        return instance;
    }

    Connection connection;

    public void getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(JDBC_DRIVER);
                connection = DriverManager.getConnection(DB_URL);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertBatch(List<WeatherInfo> weatherInfoList) {
        String sql = "insert into weather(highest_temp,lowest_temp,weather_desc,wind,icon1,icon2,publish_datetime) values(?,?,?,?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (WeatherInfo weatherInfo : weatherInfoList) {
                preparedStatement.setString(1, weatherInfo.getHighestTemp());
                preparedStatement.setString(2, weatherInfo.getLowestTemp());
                preparedStatement.setString(3, weatherInfo.getDesc());
                preparedStatement.setString(4, weatherInfo.getWind());
                preparedStatement.setString(5, weatherInfo.getDayInfo().getWeatherPic());
                preparedStatement.setString(6, weatherInfo.getNightInfo().getWeatherPic());
                preparedStatement.setTimestamp(7, new Timestamp(weatherInfo.getDate().getTime()));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConn();
        }
    }

    public void closeConn() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
