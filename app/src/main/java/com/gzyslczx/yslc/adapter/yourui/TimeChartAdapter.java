package com.gzyslczx.yslc.adapter.yourui;

import com.gzyslczx.stockmarket.adapter.StockMarketChartAdapter;
import com.gzyslczx.stockmarket.adapter.StockTimeChartMode;
import com.yourui.sdk.message.use.TrendDataModel;

import java.util.ArrayList;
import java.util.List;

public class TimeChartAdapter extends StockMarketChartAdapter<TrendDataModel> implements StockTimeChartMode {

    private float PrePrice, MaxPrice, MinPrice;
    private long MaxVolume;

    @Override
    public void setPrePrice(float price) {
        PrePrice = price;
    }

    @Override
    public float getPrePrice() {
        return PrePrice;
    }

    @Override
    public float getStockTimeRealPrice(int i) {
        return DataList.get(i).getPrice();
    }

    @Override
    public float getStockTimeAvePrice(int i) {
        return DataList.get(i).getAvgPrice();
    }

    @Override
    public long getStockTimeVolume(int i) {
        return DataList.get(i).getTradeAmount();
    }

    @Override
    public void setMaxPrice(float price) {
        MaxPrice = price;
    }

    @Override
    public float getMaxPrice() {
        return MaxPrice;
    }

    @Override
    public void setMinPrice(float price) {
        MinPrice = price;
    }

    @Override
    public float getMinPrice() {
        return MinPrice;
    }

    @Override
    public void CountMaxMinPriceOfTimeChart() {
        float dif_max = Math.abs(getMaxPrice() - getPrePrice());
        float dif_min = Math.abs(getMinPrice() - getPrePrice());
        if (dif_max > dif_min){
            setMaxValue(getMaxPrice());
            setMinValue(getPrePrice()-dif_max);
        }else if (dif_max < dif_min){
            setMaxValue(getPrePrice()+dif_min);
            setMinValue(getMinPrice());
        }else {
            setMaxValue(getMaxPrice());
            setMinValue(getMinPrice());
        }
    }

    @Override
    public void CountMaxVolumeOfTimeChart() {
        for (int i=0; i<getDataListSize(); i++){
            if (i==0){
                MaxVolume = getStockTimeVolume(i);
            }else {
                MaxVolume = Math.max(MaxVolume, getStockTimeVolume(i));
            }
        }
    }

    @Override
    public long getMaxVolume() {
        return MaxVolume;
    }

    @Override
    public void setDataList(List<TrendDataModel> dataList) {
        /*
         * 重置数据源
         * */
        if (getDataList()==null){
            DataList = new ArrayList<TrendDataModel>();
        }else {
            DataList.clear();
        }
        if (DataList.addAll(dataList)){
            CountMaxMinPriceOfTimeChart();
            CountMaxVolumeOfTimeChart();
            if (getBaseChart()!=null){
                getBaseChart().invalidate();
            }
        }
    }

}
