package com.lxzh123.weather;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class GetWeather {
	/** 新浪天气网址 */ 
	private final String SINA_URL = "http://php.weather.sina.com.cn/xml.php"; 
	/** 新浪天气XML调用密码 */ 
	private final String PASSWORD = "DJOYnieT8234jlsK";
	
	
	/** 城市 */ 
	private String city; 
    /**day 0表示当天天气，1表示第二天的天气，2表示第三天的天气，以此类推，最大为4 */ 
	private String day;
    /** 白天天气 */ 
	private String status1; 
    /** 夜晚天气 */ 
	private String status2; 
    /** 白天天气 拼音 */ 
	private String figure1; 
    /** 夜晚天气拼音 */ 
	private String figure2; 
    /** 白天风向 */ 
	private String direction1; 
    /** 夜晚风向 */ 
	private String direction2; 
    /** 白天风级 */ 
	private String power1; 
    /** 夜晚风级 */ 
	private String power2; 
    /** 白天温度 */ 
	private String temperature1; 
    /** 夜晚温度 */ 
	private String temperature2; 
    /** 体感温度 */ 
	private String tgd; 
    /** 紫外线指数 */ 
	private String zwx_l; 
    /** 紫外线说明 */ 
	private String zwx_s; 
    /** 体感度指数 */ 
	private String ssd_l; 
    /** 体感度说明 */ 
	private String ssd_s; 
    /** 空调指数 */ 
	private String ktk_l; 
    /** 空调说明 */ 
	private String ktk_s; 
    /** 洗车指数 */ 
	private String xcz_l; 
    /** 洗车说明 */ 
	private String xcz_s; 
    /** 穿衣指数 */ 
	private String chy_l; 
    /** 穿衣说明 */ 
	private String chy_s; 
    /** 污染物扩散条件 */ 
	private String pollution_l; 
    /** 污染物扩散条件说明 */ 
	private String pollution_s; 
    /** 感冒指数 */ 
	private String gm_l; 
    /** 感冒说明 */ 
	private String gm_s; 
    /** 运动指数 */ 
	private String yd_l; 
    /** 运动说明 */ 
	private String yd_s; 
	
    private boolean isLoaded;
    
    public GetWeather(String city,String day)
    {
    	this.city=city;
    	this.day=day;
    }
    
    /** 
     * 更新天气 
     *  
     * @param city 
     *            城市名 
     * @param day 
     *            0表示当天天气，1表示第二天的天气，2表示第三天的天气，以此类推，最大为4 
     */ 
    public void UpdateWeatherInfo(String city, String day) { 
        if (city.equals("")) { 
            isLoaded = false; 
            return; 
        } 
        String html = null; 
        try { 
            html = doGet(SINA_URL + "?city=" 
                    + java.net.URLEncoder.encode(city, "gb2312") + "&password=" 
                    + PASSWORD + "&day=" + day); 
 
            Document doc = Jsoup.parse(html); 
            if (doc.body().getElementsByTag("Profiles").size() == 0) { 
                isLoaded = false; 
                return; 
            } 
            if (doc.body().getElementsByTag("Profiles").get(0).getElementsByTag("Weather").size() == 0) { 
                isLoaded = false; 
                return; 
            } 
            Element element = doc.body().getElementsByTag("Profiles").get(0) 
                    .getElementsByTag("Weather").get(0); 
 
            this.city = element.getElementsByTag("city").text(); 
            status1 = element.getElementsByTag("status1").text(); 
            status2 = element.getElementsByTag("status2").text(); 
            figure1 = element.getElementsByTag("figure1").text(); 
            figure2 = element.getElementsByTag("figure2").text(); 
            direction1 = element.getElementsByTag("direction1").text(); 
            direction2 = element.getElementsByTag("direction2").text(); 
            power1 = element.getElementsByTag("power1").text(); 
            power2 = element.getElementsByTag("power2").text(); 
            temperature1 = element.getElementsByTag("temperature1").text(); 
            temperature2 = element.getElementsByTag("temperature2").text(); 
 
            tgd = element.getElementsByTag("tgd").text(); 
            zwx_l = element.getElementsByTag("zwx_l").text(); 
            zwx_s = element.getElementsByTag("zwx_s").text(); 
            ssd_l = element.getElementsByTag("ssd_l").text(); 
            ssd_s = element.getElementsByTag("ssd_s").text(); 
            ktk_l = element.getElementsByTag("ktk_l").text(); 
            ktk_s = element.getElementsByTag("ktk_s").text(); 
            xcz_l = element.getElementsByTag("xcz_l").text(); 
            xcz_s = element.getElementsByTag("xcz_s").text(); 
            chy_l = element.getElementsByTag("chy_l").text(); 
            chy_s = element.getElementsByTag("chy_shuoming").text(); 
            pollution_l = element.getElementsByTag("pollution_l").text(); 
            pollution_s = element.getElementsByTag("pollution_s").text(); 
            gm_l = element.getElementsByTag("gm_l").text(); 
            gm_s = element.getElementsByTag("gm_s").text(); 
            yd_l = element.getElementsByTag("yd_l").text(); 
            yd_s = element.getElementsByTag("yd_s").text(); 
 
            isLoaded = true; 
        } catch (UnsupportedEncodingException e) { 
            isLoaded = false; 
        } 
    } 
    
    public final String ENCODE = "utf-8"; 
    
    public String doGet(String url) { 
        try { 
            HttpGet httpGet = new HttpGet(url); 
            HttpClient hc = new DefaultHttpClient(); 
            HttpResponse ht = hc.execute(httpGet); 
            if (ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { 
                HttpEntity he = ht.getEntity(); 
                InputStream is = he.getContent(); 
                BufferedReader br = new BufferedReader( 
                        new InputStreamReader(is)); 
                String response = ""; 
                String readLine = null; 
                while ((readLine = br.readLine()) != null) { 
                    response = response + readLine; 
                } 
                is.close(); 
                br.close(); 
                return response; 
            } else { 
                return "error"; 
            } 
        } catch (Exception e) { 
            return "error"; 
        } 
    } 
    
    public String toString()
    {
    	UpdateWeatherInfo(city,day);
    	
    	StringBuffer weatherInfo=new StringBuffer();
    	weatherInfo.append(city+":的天气为\n");
    	weatherInfo.append("白天天气:"+status1+"\n");
    	weatherInfo.append("夜晚天气:"+status2+"\n");
    	weatherInfo.append("白天天气 拼音:"+figure1+"\n");
    	weatherInfo.append("夜晚天气 拼音:"+figure2+"\n");
    	weatherInfo.append("白天风向:"+direction1+"\n");
    	weatherInfo.append("夜晚风向:"+direction2+"\n");
    	weatherInfo.append("白天风级:"+power1+"\n");
    	weatherInfo.append("夜晚风级:"+status2+"\n");
    	weatherInfo.append("白天温度:"+temperature1+"\n");
    	weatherInfo.append("夜晚温度:"+temperature2+"\n");
    	weatherInfo.append("体感温度:"+tgd+"\n");
    	weatherInfo.append("紫外线指数:"+zwx_l+"\n");
    	weatherInfo.append("紫外线说明:"+zwx_s+"\n");
    	weatherInfo.append("体感度指数:"+ssd_l+"\n");
    	weatherInfo.append("体感度说明:"+ssd_s+"\n");
    	weatherInfo.append("空调指数:"+ktk_l+"\n");
    	weatherInfo.append("空调说明:"+ktk_s+"\n");
    	weatherInfo.append("洗车指数:"+xcz_l+"\n");
    	weatherInfo.append("洗车说明:"+xcz_s+"\n");
    	weatherInfo.append("穿衣指数:"+chy_l+"\n");
    	weatherInfo.append("穿衣说明:"+chy_s+"\n");
    	weatherInfo.append("污染物扩散条件:"+pollution_l+"\n");
    	weatherInfo.append("污染物扩散条件说明:"+pollution_s+"\n");
    	weatherInfo.append("感冒指数:"+gm_l+"\n");
    	weatherInfo.append("感冒说明:"+gm_s+"\n");
    	weatherInfo.append("运动指数:"+yd_l+"\n");
    	weatherInfo.append("运动说明:"+yd_s+"\n");
    	return weatherInfo.toString();
    }
}
