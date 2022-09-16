package com.gzyslczx.yslc;

import android.content.Intent;
import android.view.View;

import com.gzyslczx.yslc.databinding.ActivityHomeBinding;

public class HomeActivity extends BaseActivity<ActivityHomeBinding> implements View.OnClickListener {

    @Override
    void InitParentLayout() {
        mViewBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(mViewBinding.getRoot());
    }

    @Override
    void InitView() {
        mViewBinding.Test.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Test){
            startActivity(new Intent(this, StockMarketActivity.class));
        }
    }
}