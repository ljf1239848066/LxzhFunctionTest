package com.lxzh123.weather;

public class Weather {
    private String city;
    private String temp;
    private String WD;
    private String WS;
    private String SD;
    private String WSE;
    private String time;
    private String isRadar;
    private String Radar;
    private String njd;
    private String qy;
    private String rain;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWD() {
        return WD;
    }

    public void setWD(String WD) {
        this.WD = WD;
    }

    public String getWS() {
        return WS;
    }

    public void setWS(String WS) {
        this.WS = WS;
    }

    public String getSD() {
        return SD;
    }

    public void setSD(String SD) {
        this.SD = SD;
    }

    public String getWSE() {
        return WSE;
    }

    public void setWSE(String WSE) {
        this.WSE = WSE;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsRadar() {
        return isRadar;
    }

    public void setIsRadar(String isRadar) {
        this.isRadar = isRadar;
    }

    public String getRadar() {
        return Radar;
    }

    public void setRadar(String radar) {
        Radar = radar;
    }

    public String getNjd() {
        return njd;
    }

    public void setNjd(String njd) {
        this.njd = njd;
    }

    public String getQy() {
        return qy;
    }

    public void setQy(String qy) {
        this.qy = qy;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public Weather() {
    }

    public Weather(String city, String temp, String WD, String WS, String SD, String WSE, String time, String isRadar, String radar, String njd, String qy, String rain) {
        this.city = city;
        this.temp = temp;
        this.WD = WD;
        this.WS = WS;
        this.SD = SD;
        this.WSE = WSE;
        this.time = time;
        this.isRadar = isRadar;
        Radar = radar;
        this.njd = njd;
        this.qy = qy;
        this.rain = rain;
    }
}
