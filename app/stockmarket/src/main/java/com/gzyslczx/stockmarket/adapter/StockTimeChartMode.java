package com.gzyslczx.stockmarket.adapter;

public interface StockTimeChartMode {

    float getPrePrice(); //昨收价

    float getStockTimeRealPrice(int i); //分时图实价

    float getStockTimeAvePrice(int i); //分时图均价

    long getStockTimeVolume(int i); //分时图成交量

    float getMaxPrice(); //分时图实时最高价

    float getMinPrice(); //分时图实时最低价

}
