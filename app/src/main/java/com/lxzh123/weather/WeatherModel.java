package com.lxzh123.weather;

public class WeatherModel {
    private Weather weatherinfo;

    public Weather getWeatherinfo() {
        return weatherinfo;
    }

    public void setWeatherinfo(Weather weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public WeatherModel() {
    }

    public WeatherModel(Weather weatherinfo) {
        this.weatherinfo = weatherinfo;
    }
}
