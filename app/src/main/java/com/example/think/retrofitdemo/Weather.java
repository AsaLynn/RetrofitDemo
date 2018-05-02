package com.example.think.retrofitdemo;

/**
 * //java对象中的字段名称和json字符串中的键的名字必须一致,否则报错.解析不成功.
 */

public class Weather {

    private WeatherInfo weatherinfo;

    public void setWeatherinfo(WeatherInfo weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public WeatherInfo getWeatherinfo() {
        return weatherinfo;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "weatherinfo=" + weatherinfo +
                '}';
    }
}
