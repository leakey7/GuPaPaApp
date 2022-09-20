package com.gzyslczx.stockmarket.adapter;

public interface StockTimeChartMode {

    void setPrePrice(float price); //设置昨收价

    float getPrePrice(); //昨收价

    float getStockTimeRealPrice(int i); //分时图实价

    float getStockTimeAvePrice(int i); //分时图均价

    long getStockTimeVolume(int i); //分时图成交量

    void setMaxPrice(float price); //设置分时图实时最高价

    float getMaxPrice(); //分时图实时最高价

    void setMinPrice(float price); //设置分时图实时最低价

    float getMinPrice(); //分时图实时最低价

    void CountMaxMinValueOfTimeChart(); //计算最高值和最低值

    void CountMaxVolumeOfTimeChart(); //计算最大值成交量

    long getMaxVolume(); //获取最大成交量

    float getMaxGain(); //获取最大幅度

    float getMinGain(); //获取最小幅度

    float getMaxValue(); //最大值

    float getMinValue(); //最小值
}
