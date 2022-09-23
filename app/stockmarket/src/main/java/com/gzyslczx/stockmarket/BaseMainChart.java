package com.gzyslczx.stockmarket;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

public abstract class BaseMainChart extends BaseChart{

    private MainSubChartLink subChartLink;
    private GestureDetector  gestureDetector;
    private boolean enableLongPress = false;
    private boolean enableZoom = false;
    private boolean enableScroll = false;
    private boolean isLongPress = false;
    private boolean isUpAfterLong = false;
    private OnGesturesListener gesturesListener;

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
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE){
            getParent().requestDisallowInterceptTouchEvent(false);
        }else {
            getParent().requestDisallowInterceptTouchEvent(true);
            if (enableLongPress && isLongPress) {
                PrintLog(String.format("确认长按后操作%d", event.getAction()));
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //长按滑动
                    if (gesturesListener!=null){
                        float x = event.getX();
                        float y = event.getY();
                        gesturesListener.OnLongPressMove(x, y);
                        if (subChartLink!=null){
                            subChartLink.LongPressMove(x, y);
                        }
                    }
                    return super.onTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //长按抬起
                    PrintLog("长按抬起");
                    isUpAfterLong = true;
                    if (gesturesListener!=null){
                        float x = event.getX();
                        float y = event.getY();
                        gesturesListener.OnLongPressAfterUp(x, y);
                        if (subChartLink!=null){
                            subChartLink.LongPressMove(x, y);
                        }
                    }
                    return true;
                } else if (isUpAfterLong && event.getAction() == MotionEvent.ACTION_DOWN) {
                    PrintLog("取消指示状态");
                    isLongPress = false;
                    isUpAfterLong = false;
                    if (gesturesListener!=null){
                        gesturesListener.CancelLongPressBySingleClick();
                        if (subChartLink!=null){
                            subChartLink.CancelLongPress();
                        }
                    }
                    return gestureDetector.onTouchEvent(event);
                }
            } else if (enableZoom && event.getPointerCount() == 2 && event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                PrintLog("双指操作");
                if (gesturesListener!=null){
                    float x1 = event.getX(0);
                    float y1 = event.getY(0);
                    float x2 = event.getX(1);
                    float y2 = event.getY(1);
                    gesturesListener.OnZoom(x1, y1, x2, y2);
                    if (subChartLink!=null){
                        subChartLink.Zoom(x1, y1, x2, y2);
                    }
                }
                return true;
            }
        }
        return gestureDetector.onTouchEvent(event);
    }

    private void InitGestureDetector(Context context){
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDown(MotionEvent e) {
                //单击按下
                PrintLog("按下");
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                PrintLog("短按");
                super.onShowPress(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                PrintLog("确认长按");
                if (enableLongPress){
                    isLongPress = true;
                    if (gesturesListener!=null){
                        float x = e.getX();
                        float y = e.getY();
                        gesturesListener.OnLongPressMove(x, y);
                        if (subChartLink!=null){
                            subChartLink.LongPressMove(x, y);
                        }
                    }
                }
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                PrintLog("单指滑动");
                if (enableScroll) {
                    if (gesturesListener != null) {
                        float x = e2.getX();
                        float y = e2.getY();
                        gesturesListener.OnSingleMove(x, y, distanceX, distanceY);
                        if (subChartLink != null) {
                            subChartLink.SingleMove(x, y, distanceX, distanceY);
                        }
                    }
                }
                return true;
            }
        });
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

    public boolean isEnableZoom() {
        return enableZoom;
    }

    public void setEnableZoom(boolean enableZoom) {
        this.enableZoom = enableZoom;
    }

    public boolean isEnableScroll() {
        return enableScroll;
    }

    public void setEnableScroll(boolean enableScroll) {
        this.enableScroll = enableScroll;
    }

    public boolean isLongPress() {
        return isLongPress;
    }

    public boolean isUpAfterLong() {
        return isUpAfterLong;
    }

    public OnGesturesListener getGesturesListener() {
        return gesturesListener;
    }

    public void setGesturesListener(OnGesturesListener gesturesListener) {
        this.gesturesListener = gesturesListener;
    }
}
