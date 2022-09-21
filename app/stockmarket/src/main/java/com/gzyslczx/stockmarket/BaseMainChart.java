package com.gzyslczx.stockmarket;

import android.content.Context;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public abstract class BaseMainChart extends BaseChart{

    private MainSubChartLink subChartLink;
    private GestureDetector  gestureDetector;
    private boolean enableLongPress = false;
    private boolean enableScale = false;
    private boolean isLongPress = false;
    private boolean isUpAfterLong = false;

    public BaseMainChart(Context context) {
        super(context);
    }

    public BaseMainChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitGestureDetector(context);
    }

    public BaseMainChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseMainChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public void setSubChartLink(MainSubChartLink subChartLink) {
        this.subChartLink = subChartLink;
    }

    public MainSubChartLink getSubChartLink() {
        return subChartLink;
    }

    public abstract int getDataSize();

    public boolean isEnableLongPress() {
        return enableLongPress;
    }

    public void setEnableLongPress(boolean enableLongPress) {
        this.enableLongPress = enableLongPress;
    }

    public boolean isEnableScale() {
        return enableScale;
    }

    public void setEnableScale(boolean enableScale) {
        this.enableScale = enableScale;
    }

    private void InitGestureDetector(Context context){
        gestureDetector = new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                PrintLog("手指按下");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                //滑动操作
                PrintLog(String.format("滑动操作,XDis=%s,YDis=%s", distanceX, distanceY));
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (enableLongPress){
                    PrintLog("确认长按");
                    isLongPress = true;
                }
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return true;
            }
        });
    }

}
