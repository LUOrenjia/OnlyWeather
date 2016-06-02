package com.shunhaoluo.Layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.OverScroller;

import com.shunhaoluo.UI.IOSWeatherInfoView;
import com.shunhaoluo.onlyweather.R;

/**
 * Created by lenovo on 2016/5/31.
 */
public class WeatherLayout extends LinearLayout {

    private static final String LOG_TAG = WeatherLayout.class.getSimpleName();

    private IOSWeatherInfoView topView;
    private View middleView;
    private ListView bottomView;

    private int mTopViewHeight;
    private ViewGroup mInnerScrollView;
    private boolean isTopHidden = false;

    private OverScroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;

    private float mLastY;
    private boolean mDragging;

    private boolean isInControl = false;

    public WeatherLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL);
    }

    public WeatherLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context , new BounceInterpolator());
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topView = (IOSWeatherInfoView)findViewById(R.id.iso_weather_top_id);
        middleView = findViewById(R.id.iso_weather_middle_id);
        bottomView = (ListView)findViewById(R.id.iso_weather_bottom_id);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams params = bottomView.getLayoutParams();
        params.height = getMeasuredHeight() - middleView.getMeasuredHeight() - topView.getMeasuredHeight()/3;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.i( LOG_TAG , "onInterceptTouchEvent");
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
//                getCurrentScrollView();
                if (Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                    if (bottomView instanceof ListView) {
                        ListView lv = bottomView;
                        View c = lv.getChildAt(lv.getFirstVisiblePosition());
                        // 如果topView没有隐藏
                        // 或sc的listView在顶部 && topView隐藏 && 下拉，则拦截
                        if (!isTopHidden || //
                                (c != null //
                                        && c.getTop() == 0//
                                        && isTopHidden && dy > 0)) {
                            initVelocityTrackerIfNotExists();
                            mVelocityTracker.addMovement(ev);
                            mLastY = y;
                            return true;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mDragging = false;
                recycleVelocityTracker();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
//        Log.i( LOG_TAG , "computeScroll");
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = topView.getMeasuredHeight();
    }

    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > mTopViewHeight*2/3) {
            y = mTopViewHeight*2/3;
        }
//        Log.i( LOG_TAG , "scrollTo ");
        if (y != getScrollY()) {
            float progress = getProgress(y);
            topView.setProgress(progress);
            super.scrollTo(x, y);
        }

        isTopHidden = getScrollY() == mTopViewHeight*2/3;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        Log.i( LOG_TAG , "onTouchEvent");
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int action = event.getAction();
        float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished())
                    mScroller.abortAnimation();
                mLastY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = y - mLastY;
//                Log.e("TAG", "dy = " + dy + " , y = " + y + " , mLastY = " + mLastY);
                if (!mDragging && Math.abs(dy) > mTouchSlop) {
                    mDragging = true;
                }
                if (mDragging) {
                    scrollBy(0, (int) -dy);
//                     如果topView隐藏，且上滑动时，则改变当前事件为ACTION_DOWN
                    if (getScrollY() == mTopViewHeight*2/3 && dy < 0) {
                        event.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(event);
                        isInControl = false;
                    }
                }
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                mDragging = false;
                recycleVelocityTracker();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                mDragging = false;
                mVelocityTracker.computeCurrentVelocity(800, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                if (Math.abs(velocityY) > mMinimumVelocity) {
                    fling(-velocityY);
                }
                recycleVelocityTracker();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        int action = ev.getAction();
//        float y = ev.getY();
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mLastY = y;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dy = y - mLastY;
//                break;
//        }
        return super.dispatchTouchEvent(ev);
    }


    public void fling(int velocityY) {
        mScroller.fling(0, getScrollY(), 0, velocityY, 0, 0, 0, mTopViewHeight*2/3);
        invalidate();
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private float getProgress(float offset){
        return 1 - offset/(mTopViewHeight*2/3);
    }

}
