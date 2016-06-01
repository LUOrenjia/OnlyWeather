package com.shunhaoluo.onlyweather;

/**
 * Created by lenovo on 2016/6/1.
 */
public enum WeekEnum {
    sunday , monday , tuesday , wednesday , thursday , firday , staurday;

    private static String[] mWeekDay = {"星期天" , "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    public static String getString(WeekEnum weekday){
        switch (weekday){
            case sunday:
                return mWeekDay[sunday.ordinal()];
            case monday:
                return mWeekDay[monday.ordinal()];
            case tuesday:
                return mWeekDay[tuesday.ordinal()];
            case wednesday:
                return mWeekDay[wednesday.ordinal()];
            case thursday:
                return mWeekDay[thursday.ordinal()];
            case firday:
                return mWeekDay[firday.ordinal()];
            case staurday:
                return mWeekDay[staurday.ordinal()];
            default:
                return "";
        }
    }

    public static void setWeekDay(String[] weekDay){
        mWeekDay = weekDay;
    }
}
