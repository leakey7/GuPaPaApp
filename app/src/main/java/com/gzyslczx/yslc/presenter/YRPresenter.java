package com.gzyslczx.yslc.presenter;

import com.gzyslczx.yslc.BaseActivity;
import com.gzyslczx.yslc.fragment.BaseFragment;
import com.gzyslczx.yslc.tools.conn.NetWorkConnOfYR;

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


    public void ForYRToken(BaseActivity baseActivity, BaseFragment baseFragment){
        NetWorkConnOfYR.Create().RequestStockToken(baseActivity, baseFragment);
    }

}
