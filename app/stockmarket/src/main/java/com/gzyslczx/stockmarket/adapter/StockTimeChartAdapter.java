package com.gzyslczx.stockmarket.adapter;

import com.gzyslczx.stockmarket.BaseMainChart;

import java.util.List;

public abstract class StockTimeChartAdapter<T> implements StockTimeChartMode{

    private BaseMainChart mainChart;

    public abstract void setDataList(List<T> dataList);

    public abstract void setDataList(float PrePrice, float MaxPrice, float MinPrice, List<T> dataList);

    public abstract List<T> getDataList();

    public abstract int getDataSize();

    /*
     * 计算幅度，单位百分数
     * */
    public float CountGainPercent(float nowValue, float baseValue){
        return (nowValue-baseValue)/baseValue*100f;
    }

    public void setChart(BaseMainChart mainChart){
        this.mainChart = mainChart;
    }

    public BaseMainChart getMainChart() {
        return mainChart;
    }

    public void RemoveChart(){
        mainChart = null;
    }

}
