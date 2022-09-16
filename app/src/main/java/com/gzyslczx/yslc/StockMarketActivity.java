package com.gzyslczx.yslc;

import com.gzyslczx.yslc.databinding.ActivityStockMarketBinding;
import com.gzyslczx.yslc.presenter.YRPresenter;

public class StockMarketActivity extends BaseActivity<ActivityStockMarketBinding> {


    @Override
    void InitParentLayout() {
        mViewBinding = ActivityStockMarketBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());
    }

    @Override
    void InitView() {
        YRPresenter.instance().ForYRToken(this, null);
    }
}