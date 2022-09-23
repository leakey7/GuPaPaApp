package com.gzyslczx.yslc.presenter;

import android.text.TextUtils;

import com.gzyslczx.yslc.BaseActivity;
import com.gzyslczx.yslc.events.yourui.YRTokenUpdateEvent;
import com.gzyslczx.yslc.fragment.BaseFragment;
import com.gzyslczx.yslc.tools.DateTool;
import com.gzyslczx.yslc.tools.PrintTool;
import com.gzyslczx.yslc.tools.SpTool;
import com.gzyslczx.yslc.tools.conn.NetWorkConnOfYR;

import org.greenrobot.eventbus.EventBus;

public class YRPresenter extends BasePresenter{

    private static volatile YRPresenter presenter;

    public static YRPresenter instance(){
        if (presenter==null){
            synchronized (YRPresenter.class){
                if (presenter==null){
                    presenter = new YRPresenter();
                }
            }
        }
        return presenter;
    }


    //请求Token
    public void ForYRToken(BaseActivity baseActivity, BaseFragment baseFragment){
        String time = SpTool.GetInfo(SpTool.YRTokenTime);
        if (TextUtils.isEmpty(time)){
            PrintTool.PrintLogD(getClass().getSimpleName(), "YRTokenTime Null");
            NetWorkConnOfYR.Create().RequestStockToken(baseActivity, baseFragment);
            return;
        }
        boolean state = false;
        try {
            state = (!DateTool.instance().OverTimeNow(time, 6));
            if (!state){
                EventBus.getDefault().post(new YRTokenUpdateEvent(SpTool.GetInfo(SpTool.YRToken)));
                PrintTool.PrintLogD(getClass().getSimpleName(), "YRToken有效");
            }
        } catch (Exception e) {
            e.printStackTrace();
            PrintTool.PrintLogD(getClass().getSimpleName(), "Token旧日期异常");
        }
        if (state){
            PrintTool.PrintLogD(getClass().getSimpleName(), "YRTokenTime Over Time");
            NetWorkConnOfYR.Create().RequestStockToken(baseActivity, baseFragment);
        }
    }

    //请求分时数据
    public void ForYRTimeChart(BaseActivity baseActivity, BaseFragment baseFragment){
        NetWorkConnOfYR.Create().RequestStockTimeChart(3, "600570", baseActivity, baseFragment);
    }

    //请求历史分时数据
    public void ForHistoryTimeChart(BaseActivity baseActivity, BaseFragment baseFragment, int reqTime){
        NetWorkConnOfYR.Create().RequestFiveDayTimeChart("600570", baseActivity, baseFragment, reqTime);
    }


}
