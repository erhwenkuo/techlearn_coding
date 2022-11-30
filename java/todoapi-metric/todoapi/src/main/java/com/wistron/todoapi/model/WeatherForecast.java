package com.wistron.todoapi.model;

import java.util.Date;

public class WeatherForecast {
    private Date date;
    private int temperatureC;
    private String summary;

    public WeatherForecast() {
    }

    public WeatherForecast(Date date, int temperatureC, String summary) {
        this.date = date;
        this.temperatureC = temperatureC;
        this.summary = summary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(int temperatureC) {
        this.temperatureC = temperatureC;
    }

    public int getTemperatureF() {
        return 32 + (int)(this.temperatureC / 0.5556);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
