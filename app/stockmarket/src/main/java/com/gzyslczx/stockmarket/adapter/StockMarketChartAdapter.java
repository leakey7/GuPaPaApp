package com.gzyslczx.stockmarket.adapter;

import com.gzyslczx.stockmarket.BaseChart;

import java.util.ArrayList;
import java.util.List;

public abstract class StockMarketChartAdapter<T> {

    public List<T> DataList;
    private BaseChart baseChart;
    private float MaxValue, MinValue;

    public BaseChart getBaseChart() {
        return baseChart;
    }

    public void setBaseChart(BaseChart baseChart) {
        this.baseChart = baseChart;
    }

    public void releaseBaseChart(){
        this.baseChart = null;
    }

    public List<T> getDataList() {
        return DataList;
    }

    /*
    * 重置数据源
    * */
    public abstract void setDataList(List<T> dataList);

    /*
    * 获取数据量
    * */
    public int getDataListSize(){
        if (DataList!=null){
            return DataList.size();
        }
        return 0;
    }

    //获取最大值
    public float getMaxValue() {
        return MaxValue;
    }

    //获取最小值
    public float getMinValue() {
        return MinValue;
    }

    //设置最大值
    public void setMaxValue(float maxValue) {
        MaxValue = maxValue;
    }

    //设置最小值
    public void setMinValue(float minValue) {
        MinValue = minValue;
    }

    /*
    * 计算幅度，单位百分数
    * */
    public float CountGainPercent(float nowValue, float baseValue){
        return (nowValue-baseValue)/baseValue*100f;
    }

    /*
    * 分时图Mode
    * */
    public StockTimeChartMode getTimeChartMode(){
        if (this instanceof StockTimeChartMode){
            return (StockTimeChartMode) this;
        }
        return null;
    }

}
