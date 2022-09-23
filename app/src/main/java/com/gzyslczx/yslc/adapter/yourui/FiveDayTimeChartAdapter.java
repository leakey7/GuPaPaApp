package com.gzyslczx.yslc.adapter.yourui;

import com.gzyslczx.stockmarket.adapter.StockTimeChartAdapter;
import com.gzyslczx.yslc.tools.PrintTool;
import com.yourui.sdk.message.use.TrendDataModel;

import java.util.ArrayList;
import java.util.List;

public class FiveDayTimeChartAdapter extends StockTimeChartAdapter<TrendDataModel> {

    private List<TrendDataModel> dataList;
    private float PrePrice;
    private float MaxPrice, MinPrice;
    private float MaxValue, MinValue;
    private float MaxGain, MinGain;
    private long MaxVolume;
    private int TagItemEndIndex =0;

    @Override
    public void setDataList(List<TrendDataModel> dataList) {
        PrintTool.PrintLogD(getClass().getSimpleName(), "更新数据源");
        if (this.dataList==null){
            this.dataList = new ArrayList<TrendDataModel>();
        }else {
            this.dataList.clear();
            TagItemEndIndex = 0;
        }
        if (this.dataList.addAll(dataList)){
            CountMaxMinValueOfTimeChart();
            CountMaxVolumeOfTimeChart();
        }
        getMainChart().invalidate();
        if (getMainChart().getSubChartLink()!=null) {
            getMainChart().getSubChartLink().NoticeSubUpdate();
        }
    }

    @Override
    public void setDataList(float PrePrice, float MaxPrice, float MinPrice, List<TrendDataModel> dataList) {
        setPrePrice(PrePrice);
        setMaxPrice(MaxPrice);
        setMinPrice(MinPrice);
        setDataList(dataList);
    }

    public void addDataList(float PrePrice, float MaxPrice, float MinPrice, List<TrendDataModel> dataList){
        setPrePrice(PrePrice);
        setMaxPrice(MaxPrice);
        setMinPrice(MinPrice);
        if (this.dataList==null){
            this.dataList = new ArrayList<TrendDataModel>();
        }
        if (this.dataList.addAll(dataList)){
            CountMaxMinValueOfTimeChart();
            CountMaxVolumeOfTimeChart();
        }
        getMainChart().invalidate();
        if (getMainChart().getSubChartLink()!=null) {
            getMainChart().getSubChartLink().NoticeSubUpdate();
        }
    }

    @Override
    public List<TrendDataModel> getDataList() {
        return dataList;
    }

    @Override
    public int getDataSize() {
        return dataList.size();
    }

    @Override
    public void setPrePrice(float price) {
        PrePrice = Math.max(PrePrice, price);
    }

    @Override
    public float getPrePrice() {
        return PrePrice;
    }

    @Override
    public float getStockTimeRealPrice(int i) {
        return dataList.get(i).getPrice();
    }

    @Override
    public float getStockTimeAvePrice(int i) {
        return dataList.get(i).getAvgPrice();
    }

    @Override
    public long getStockTimeVolume(int i) {
        return dataList.get(i).getTradeAmount();
    }

    @Override
    public void setMaxPrice(float price) {
        MaxPrice = Math.max(price, MaxPrice);
    }

    @Override
    public float getMaxPrice() {
        return MaxPrice;
    }

    @Override
    public void setMinPrice(float price) {
        MinPrice = Math.min(price, MinPrice);
    }

    @Override
    public float getMinPrice() {
        return MinPrice;
    }

    @Override
    public void CountMaxMinValueOfTimeChart() {
        float DisMax = Math.abs(MaxPrice-PrePrice);
        float DisMin = Math.abs(MinPrice-PrePrice);
        if (DisMax>DisMin){
            MaxValue = MaxPrice;
            MinValue = PrePrice-DisMax;
        }else if (DisMax<DisMin){
            MinValue = MinPrice;
            MaxValue = PrePrice+DisMin;
        }else {
            MaxValue = MaxPrice;
            MinValue = MinPrice;
        }
        MaxGain=CountGainPercent(MaxValue, PrePrice);
        MinGain=-MaxGain;
    }

    @Override
    public void CountMaxVolumeOfTimeChart() {
        for (int i = TagItemEndIndex; i<dataList.size(); i++){
            if (i==0){
                MaxVolume = dataList.get(i).getTradeAmount();
            }else {
                MaxVolume = Math.max(MaxVolume, dataList.get(i).getTradeAmount());
            }
        }
        TagItemEndIndex =  dataList.size()-1;
    }

    @Override
    public long getMaxVolume() {
        return MaxVolume;
    }

    @Override
    public float getMaxGain() {
        return MaxGain;
    }

    @Override
    public float getMinGain() {
        return MinGain;
    }

    @Override
    public float getMaxValue() {
        return MaxValue;
    }

    @Override
    public float getMinValue() {
        return MinValue;
    }
}
