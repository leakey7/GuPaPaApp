package com.gzyslczx.yslc;

import android.app.Application;

import com.gzyslczx.yslc.tools.PrintTool;
import com.gzyslczx.yslc.tools.SpTool;
import com.gzyslczx.yslc.tools.yourui.RequestApi;
import com.yourui.sdk.message.YRMarket;

import cn.jiguang.api.utils.JCollectionAuth;
import cn.jiguang.verifysdk.api.JVerificationInterface;
import cn.jiguang.verifysdk.api.RequestCallback;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //冷启动极光推送
        JCollectionAuth.setAuth(this, false);
        //极光认证初始化
        JVerificationInterface.init(this, 10000, new RequestCallback<String>() {
            @Override
            public void onResult(int i, String s) {
                PrintTool.PrintLogD(getClass().getSimpleName(), String.format("Code=%d，Msg=%s", i, s));
            }
        });
        //初始化友睿行情
        YRMarket.getInstance().init(this);
        RequestApi.getInstance().initServer(this);
        //初始化SpTool
        SpTool.Init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        YRMarket.getInstance().destroy(true);
    }
}
