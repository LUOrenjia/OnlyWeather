package com.shunhaoluo.onlyweather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lenovo on 2016/6/1.
 */
public class IOSWeatherInfoView extends View {

    private Context mContext;

    private int mWidth;
    private int mHeight;

    private Paint mPaint;
    private Rect mBound;

    private String mCity = "北京市";
    private String mWeather = "晴天";
    private String mTemperature = "0";
    private String mDay = "星期天";
    private String mLowTemperature = "0";
    private String mHighTemperature = "0";

    private float progress = 1.0f;

    private float padding ;

    public IOSWeatherInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBound = new Rect();
        mContext = context;
        padding = DisplayUtil.dip2px(mContext , 15);
    }

    public IOSWeatherInfoView(Context context) {
        super(context);
    }

    public void setInfo(MainActivity.InfoData infoData){
        mCity = infoData.getCity();
        mWeather = infoData.getWeather();
        mTemperature = infoData.getTemperature() + "";
        mDay = WeekEnum.getString(infoData.getDay());
        mLowTemperature = infoData.getLowTemperature()+"";
        mHighTemperature = infoData.getHighTemperature()+"";

    }

    public void setProgress(float progress){
        this.progress = progress;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (widthMode){
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = widthSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }

        switch (heightMode){
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mHeight = heightSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        setMeasuredDimension(mWidth , mHeight);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.alpha(0));
        mPaint.setAntiAlias(true);
        //background
        canvas.drawRect(0 , 0 , mWidth , mHeight , mPaint);

        //city
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(DisplayUtil.sp2px(mContext , 45));
        mPaint.getTextBounds(mCity, 0, mCity.length(), mBound);
        int cityHeight = (int)(mHeight*(0.15 + (1-progress)*0.45));
        Paint.FontMetrics cityFontMetrics = mPaint.getFontMetrics();
        float cityOffsetY = cityHeight + Math.abs(cityFontMetrics.top);
        canvas.drawText(mCity ,  mWidth/2-mBound.width()/2, cityOffsetY, mPaint);

        //weather
        mPaint.setTextSize(DisplayUtil.sp2px(mContext , 20));
        mPaint.getTextBounds(mWeather, 0, mWeather.length(), mBound);
        Paint.FontMetrics weatherFontMetrics = mPaint.getFontMetrics();
        float weatherPadding = DisplayUtil.dip2px(mContext , 10);
        float weatherOffsetY = cityOffsetY + Math.abs(weatherFontMetrics.top) + weatherPadding;
        canvas.drawText(mWeather , mWidth/2-mBound.width()/2 , weatherOffsetY , mPaint);

        //temperatures
        mPaint.setAlpha((int)(255*progress));
        mPaint.setTextSize(DisplayUtil.sp2px(mContext , 120));
        mPaint.getTextBounds(mTemperature, 0, mTemperature.length(), mBound);
        Paint.FontMetrics temperaturesFontMetrics = mPaint.getFontMetrics();
        float temperaturesOffsetY = weatherOffsetY + Math.abs(temperaturesFontMetrics.top);
        canvas.drawText(mTemperature+"°" ,  mWidth/2-mBound.width()/2, temperaturesOffsetY, mPaint);

        //day
        mPaint.setTextSize(DisplayUtil.sp2px(mContext , 20));
        mPaint.getTextBounds(mDay, 0, mDay.length(), mBound);
        canvas.drawText(mDay , padding, getHeight()-padding, mPaint);

        //highTemperture
        mPaint.setTextSize(DisplayUtil.sp2px(mContext , 20));
        mPaint.getTextBounds(mHighTemperature, 0, mHighTemperature.length(), mBound);
        float highTemperatureOffset = DisplayUtil.dip2px(mContext , 40);
        canvas.drawText(mHighTemperature ,mWidth - padding - mBound.width() - highTemperatureOffset, getHeight()-padding, mPaint);

        //highTemperture
        mPaint.setTextSize(DisplayUtil.sp2px(mContext , 20));
        mPaint.getTextBounds(mLowTemperature, 0, mLowTemperature.length(), mBound);
        float lowTemperatureOffset = DisplayUtil.dip2px(mContext , 10);
        canvas.drawText(mLowTemperature ,mWidth - padding - mBound.width() - lowTemperatureOffset, getHeight()-padding, mPaint);
    }
}
