package com.gzyslczx.stockmarket.adapter;

import com.gzyslczx.stockmarket.BaseChart;

import java.util.ArrayList;
import java.util.List;

public abstract class StockMarketChartAdapter<T> {

    private List<T> DataList;
    private BaseChart baseChart;
    private StockTimeChartMode timeChartMode;
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
    public void setDataList(List<T> dataList) {
        if (DataList==null){
            DataList = new ArrayList<T>();
        }else {
            DataList.clear();
        }
        if (DataList.addAll(dataList)){
            CountMaxMinPriceOfTimeChart();
            if (baseChart!=null){
                baseChart.invalidate();
            }
        }
    }

    /*
    * 添加数据源
    * */
    public void addDataList(List<T> dataList){
        if (DataList==null){
            setDataList(dataList);
        }else {
            if (DataList.addAll(dataList)){
                CountMaxMinPriceOfTimeChart();
                if (baseChart!=null){
                    baseChart.invalidate();
                }
            }
        }
    }

    /*
    * 获取数据量
    * */
    public int getDataListSize(){
        if (DataList!=null){
            return DataList.size();
        }
        return 0;
    }


    public StockTimeChartMode getTimeChartMode() {
        return timeChartMode;
    }

    public void setTimeChartMode(StockTimeChartMode timeChartMode) {
        this.timeChartMode = timeChartMode;
    }

    /*
    * 计算分时图的最高和最低显示价
    * */
    public void CountMaxMinPriceOfTimeChart(){
        if (timeChartMode!=null){
            float dif_max = Math.abs(timeChartMode.getMaxPrice()- timeChartMode.getPrePrice());
            float dif_min = Math.abs(timeChartMode.getMinPrice()- timeChartMode.getPrePrice());
            if (dif_max > dif_min){
                MaxValue = timeChartMode.getMaxPrice();
                MinValue = timeChartMode.getPrePrice()-dif_max;
            }else if (dif_max < dif_min){
                MaxValue = timeChartMode.getPrePrice()+dif_min;
                MinValue = timeChartMode.getMinPrice();
            }else {
                MaxValue = timeChartMode.getMaxPrice();
                MinValue = timeChartMode.getMinPrice();
            }
        }
    }

    //显示最大值
    public float getMaxValue() {
        return MaxValue;
    }

    //显示最小值
    public float getMinValue() {
        return MinValue;
    }

    /*
    * 计算幅度，单位百分数
    * */
    public float CountGainPercent(float nowValue, float baseValue){
        return (nowValue-baseValue)/baseValue*100f;
    }

}
