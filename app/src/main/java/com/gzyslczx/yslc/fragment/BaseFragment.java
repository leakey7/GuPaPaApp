package com.gzyslczx.yslc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.gzyslczx.yslc.tools.PrintTool;
import com.trello.rxlifecycle4.components.support.RxFragment;

public abstract class BaseFragment<T extends ViewBinding> extends RxFragment {

    T mViewBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PrintTool.PrintLogD(getClass().getSimpleName(), "onCreate");
        return InitLayout(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PrintTool.PrintLogD(getClass().getSimpleName(), "onDestroy");
    }

    public abstract View InitLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    public abstract void InitView();

}
