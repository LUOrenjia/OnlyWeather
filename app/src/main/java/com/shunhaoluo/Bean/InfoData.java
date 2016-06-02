package com.shunhaoluo.Bean;

import com.shunhaoluo.Utils.WeekEnum;

/**
 * Created by lenovo on 2016/6/1.
 */
public class InfoData {
    //城市名称
    private String city;
    //天气状况
    private String weather;
    //当前温度
    private int temperature;
    //星期
    private WeekEnum day;
    //最低温度
    private int lowTemperature;
    //最高温度
    private int highTemperature;

    public void setCity(String city) {
        this.city = city;
    }

    public void setDay(WeekEnum day) {
        this.day = day;
    }

    public void setHighTemperature(int highTemperature) {
        this.highTemperature = highTemperature;
    }

    public void setLowTemperature(int lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getCity() {
        return city;
    }

    public WeekEnum getDay() {
        return day;
    }

    public int getHighTemperature() {
        return highTemperature;
    }

    public int getLowTemperature() {
        return lowTemperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public String getWeather() {
        return weather;
    }
}