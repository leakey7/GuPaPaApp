package com.gzyslczx.yslc;

import com.gzyslczx.yslc.databinding.ActivityStockMarketBinding;
import com.gzyslczx.yslc.events.yourui.YRTokenUpdateEvent;
import com.gzyslczx.yslc.presenter.YRPresenter;
import com.gzyslczx.yslc.tools.PrintTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class StockMarketActivity extends BaseActivity<ActivityStockMarketBinding> {


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
        YRPresenter.instance().ForYRToken(this, null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnUpdateToken(YRTokenUpdateEvent event){
        PrintTool.PrintLogD(getClass().getSimpleName(), String.format("有效Token:%s", event.getToken()));
        YRPresenter.instance().ForYRTimeChart(this, null, 0);
    }

}