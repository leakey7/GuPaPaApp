package com.gzyslczx.yslc;

import com.google.gson.Gson;
import com.gzyslczx.yslc.adapter.yourui.FiveDayTimeChartAdapter;
import com.gzyslczx.yslc.adapter.yourui.TimeChartAdapter;
import com.gzyslczx.yslc.databinding.ActivityStockMarketBinding;
import com.gzyslczx.yslc.events.yourui.YRFiveDayTimeEvent;
import com.gzyslczx.yslc.events.yourui.YRTimeChartEvent;
import com.gzyslczx.yslc.events.yourui.YRTokenUpdateEvent;
import com.gzyslczx.yslc.presenter.YRPresenter;
import com.gzyslczx.yslc.tools.PrintTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class StockMarketActivity extends BaseActivity<ActivityStockMarketBinding> {

    private TimeChartAdapter timeChartAdapter;
    private FiveDayTimeChartAdapter fiveDayTimeChartAdapter;

    @Override
    void InitParentLayout() {
        mViewBinding = ActivityStockMarketBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    void InitView() {
        timeChartAdapter = new TimeChartAdapter();
        mViewBinding.StockTimeChart.setEnableLongPress(true); //允许长按滑动
        mViewBinding.StockTimeChart.setAdapter(timeChartAdapter); //分时图配置Adapter
        mViewBinding.SubStockChart.setMainChart(mViewBinding.StockTimeChart); //分时副图关联分时主图
        mViewBinding.SubStockChart.setTimeChartMode(timeChartAdapter); //分时副图数据源与分时主图合并

        fiveDayTimeChartAdapter = new FiveDayTimeChartAdapter();
        mViewBinding.FiveDayTimeChart.setAdapter(fiveDayTimeChartAdapter); //五日分时配置Adapter
        YRPresenter.instance().ForYRToken(this, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnUpdateToken(YRTokenUpdateEvent event){
        YRPresenter.instance().ForYRTimeChart(this, null);
        YRPresenter.instance().ForHistoryTimeChart(this, null, -4);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnUpdateTimeChartEvent(YRTimeChartEvent event){
        if (event.isSuccess() && event.getEntity().getTrendDataModelList().size()>timeChartAdapter.getDataSize()){
            timeChartAdapter.setDataList(event.getEntity().getPreClosePrice(), event.getEntity().getMaxPrice(),
                    event.getEntity().getMinPrice(), event.getEntity().getTrendDataModelList());
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnUpdateFiveDayTimeChartEvent(YRFiveDayTimeEvent event){
        if (event.getHisTrendExtEntity()!=null){
            PrintTool.PrintLogD(getClass().getSimpleName(), String.format("得到历史分时数据%d", event.getReqTime()));
            fiveDayTimeChartAdapter.addDataList(event.getHisTrendExtEntity().getPreClosePrice(), event.getHisTrendExtEntity().getMaxPrice(),
                    event.getHisTrendExtEntity().getMinPrice(), event.getHisTrendExtEntity().getTrendDataModelList());
            if (event.getReqTime()!=0){
                int time = event.getReqTime()+1;
                YRPresenter.instance().ForHistoryTimeChart(this, null, time);
            }
        }else {
            PrintTool.PrintLogD(getClass().getSimpleName(), String.format("历史日期%d，无分时数据", event.getReqTime()));
        }
    }


}