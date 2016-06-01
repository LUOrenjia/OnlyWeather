package com.shunhaoluo.onlyweather;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView ;
    private List<String> mDatas = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.iso_weather_bottom_id);
        initData();
//        final View decorView = getWindow().getDecorView();
//        decorView.setOnSystemUiVisibilityChangeListener
//                (new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0) {
//                            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//                        } else {
//                        }
//                    }
//                });
//        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        decorView.setSystemUiVisibility(uiOptions);

    }

    public void initData(){
        for (int i = 0; i < 50; i++)
        {
            mDatas.add("mTitle -> " + i);
        }
        listView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.item, R.id.id_info, mDatas)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                return super.getView(position, convertView, parent);
            }
        });
    }

    /**
     * Created by lenovo on 2016/6/1.
     */
    public static class InfoData {
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
}
