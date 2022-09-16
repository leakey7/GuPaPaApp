package com.gzyslczx.stockmarket;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.gzyslczx.stockmarket.adapter.StockMarketChartAdapter;

public abstract class BaseChart extends View {

    private StockMarketChartAdapter adapter;

    public BaseChart(Context context) {
        super(context);
    }

    public BaseChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        UpdateDraw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.adapter!=null){
            this.adapter.releaseBaseChart();
        }
    }

    public void PrintLog(String log){
        Log.d(getClass().getSimpleName(), log);
    }

    public abstract void InitPaint();

    public abstract void UpdateDraw(Canvas canvas);

    public int dp2px(Context context, int dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }
    public static int sp2px(Context context, int sp) {
        return (int) (getFontDensity(context) * sp + 0.5);
    }
    public static int px2dp(Context context, int px) {
        return (int) (px / getDensity(context) + 0.5);
    }
    public static int px2sp(Context context, int px) {
        return (int) (px / getFontDensity(context) + 0.5);
    }

    private static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    private static float getFontDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    public StockMarketChartAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(StockMarketChartAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setBaseChart(this);
    }

}
