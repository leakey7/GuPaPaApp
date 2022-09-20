package com.gzyslczx.stockmarket;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.gzyslczx.stockmarket.adapter.StockTimeChartMode;

public abstract class BaseSubChart extends BaseChart implements MainSubChartLink{

    private BaseMainChart MainChart;
    private StockTimeChartMode timeChartMode;

    public BaseSubChart(Context context) {
        super(context);
    }

    public BaseSubChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseSubChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseSubChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BaseMainChart getMainChart() {
        return MainChart;
    }

    public void setMainChart(BaseMainChart mainChart) {
        MainChart = mainChart;
        mainChart.setSubChartLink(this);
    }

    public StockTimeChartMode getTimeChartMode() {
        return timeChartMode;
    }

    public void setTimeChartMode(StockTimeChartMode timeChartMode) {
        this.timeChartMode = timeChartMode;
    }
}
