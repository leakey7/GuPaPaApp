package com.gzyslczx.stockmarket;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public abstract class BaseMainChart extends BaseChart{

    private MainSubChartLink subChartLink;

    public BaseMainChart(Context context) {
        super(context);
    }

    public BaseMainChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseMainChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseMainChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setSubChartLink(MainSubChartLink subChartLink) {
        this.subChartLink = subChartLink;
    }

    public MainSubChartLink getSubChartLink() {
        return subChartLink;
    }

    public abstract int getDataSize();

}
