package com.gzyslczx.yslc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.gzyslczx.yslc.tools.PrintTool;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

public abstract class BaseActivity<T extends ViewBinding> extends RxAppCompatActivity {

    T mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PrintTool.PrintLogD(getClass().getSimpleName(), "onCreate");
        InitParentLayout();
        InitView();
//        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onDestroy");
//        EventBus.getDefault().unregister(this);
    }

    //初始化父布局
    abstract void InitParentLayout();
    //初始化子控件
    abstract void InitView();

}
