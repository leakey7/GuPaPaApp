package com.gzyslczx.yslc.tools.conn;

import com.google.gson.Gson;
import com.gzyslczx.stockmarket.Request.StockTimeReq;
import com.gzyslczx.stockmarket.Request.TokenReq;
import com.gzyslczx.stockmarket.Response.StockTimeRes;
import com.gzyslczx.stockmarket.Response.TokenRes;
import com.gzyslczx.stockmarket.YRConnPath;
import com.gzyslczx.yslc.BaseActivity;
import com.gzyslczx.yslc.fragment.BaseFragment;
import com.gzyslczx.yslc.tools.DateTool;
import com.gzyslczx.yslc.tools.PrintTool;
import com.gzyslczx.yslc.tools.SpTool;
import com.yourui.sdk.message.entity.RequestSrvSync;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkConnOfYR {

    private volatile static NetWorkConnOfYR conn;
    private YRConnMode mode;

    private NetWorkConnOfYR() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(YRConnPath.MainPath)
                .build();
        mode = retrofit.create(YRConnMode.class);
    }

    public static NetWorkConnOfYR Create() {
        if (conn == null) {
            synchronized (NetWorkConnOfYR.class) {
                if (conn == null) {
                    conn = new NetWorkConnOfYR();
                }
            }
        }
        return conn;
    }

    //请求Token
    public void RequestStockToken(BaseActivity baseActivity, BaseFragment baseFragment){
        PrintTool.PrintLogD(getClass().getSimpleName(), "请求友睿Token");
        Observable<TokenRes> observable =  mode.RequestToken(YRConnPath.Origin, new TokenReq());
        observable = ConnTool.AddRetryReq(observable, getClass().getSimpleName());
        if (baseActivity!=null){
            observable = ConnTool.AddExtraReqOfAct(observable, baseActivity);
        }else if (baseFragment!=null){
            observable = ConnTool.AddExtraReqOfFrag(observable, baseFragment);
        }
        observable.subscribe(new Consumer<TokenRes>() {
            @Override
            public void accept(TokenRes tokenRes) throws Throwable {
                if (tokenRes.isSuccess()){
                    PrintTool.PrintLogD(getClass().getSimpleName(), String.format("获取YRToken成功:%s", new Gson().toJson(tokenRes)));
                    SpTool.SaveInfo(SpTool.YRToken, tokenRes.getToken());
                    SpTool.SaveInfo(SpTool.YRTokenTime, DateTool.instance().GetToday(DateTool.y_M_d_Hms));
                }else {
                    PrintTool.PrintLogD(getClass().getSimpleName(), String.format("获取YRToken失败:%s", tokenRes.isSuccess()));
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                PrintTool.PrintLogD(getClass().getSimpleName(), String.format("网络异常:%s", throwable.getMessage()));
            }
        });
    }

    //请求分时
    public void RequestStockTimeChart(BaseActivity baseActivity, BaseFragment baseFragment){
        StockTimeReq req = new StockTimeReq("600570", 0);
        Observable<StockTimeRes> observable = mode.RequestStockTime(SpTool.GetInfo(SpTool.YRToken), ConnPath.GuPpMain, req);
        observable = ConnTool.AddRetryReq(observable, getClass().getSimpleName());
        if (baseActivity!=null){
            observable = ConnTool.AddExtraReqOfAct(observable, baseActivity);
        }else if (baseFragment!=null){
            observable = ConnTool.AddExtraReqOfFrag(observable, baseFragment);
        }
        observable = ConnTool.AddTokenOverdue(observable, ConnTool.YR_TYPE);
        observable.subscribe(new Consumer<StockTimeRes>() {
            @Override
            public void accept(StockTimeRes stockTimeRes) throws Throwable {
                if (stockTimeRes.isSuccess()){

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Throwable {
                PrintTool.PrintLogD(getClass().getSimpleName(), String.format("网络异常:%s", throwable.getMessage()));
            }
        });
    }


}
