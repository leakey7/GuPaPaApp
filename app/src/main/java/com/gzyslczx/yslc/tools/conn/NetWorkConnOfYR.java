package com.gzyslczx.yslc.tools.conn;

import com.google.gson.Gson;
import com.gzyslczx.stockmarket.Request.StockTimeReq;
import com.gzyslczx.stockmarket.Request.TokenReq;
import com.gzyslczx.stockmarket.Response.StockTimeRes;
import com.gzyslczx.stockmarket.Response.TokenRes;
import com.gzyslczx.stockmarket.YRConnPath;
import com.gzyslczx.yslc.BaseActivity;
import com.gzyslczx.yslc.events.yourui.YRTokenUpdateEvent;
import com.gzyslczx.yslc.fragment.BaseFragment;
import com.gzyslczx.yslc.tools.DateTool;
import com.gzyslczx.yslc.tools.PrintTool;
import com.gzyslczx.yslc.tools.SpTool;
import com.gzyslczx.yslc.tools.yourui.CodeTypeTool;
import com.gzyslczx.yslc.tools.yourui.RequestApi;
import com.yourui.sdk.message.YRMarket;
import com.yourui.sdk.message.client.YRMarketConfig;
import com.yourui.sdk.message.entity.RequestSrvSync;
import com.yourui.sdk.message.use.Stock;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    //轮询
    public Observable<Long> RequestInterval(long second){
        Observable<Long> observable = Observable.interval(0, second, TimeUnit.SECONDS);
        return observable;
    }

    //请求Token
    public void RequestStockToken(BaseActivity baseActivity, BaseFragment baseFragment){
        PrintTool.PrintLogD(getClass().getSimpleName(), "请求友睿Token");
        Observable<TokenRes> observable =  mode.RequestToken(YRConnPath.MainPath, new TokenReq());
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
                    EventBus.getDefault().post(new YRTokenUpdateEvent(tokenRes.getToken()));
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
    public void RequestStockTimeChart(long second, String stockCode, BaseActivity baseActivity, BaseFragment baseFragment, int date){
        int codeType = CodeTypeTool.MatchingCodeType(stockCode);
        if (codeType!=-1) {
            Stock stock = new Stock(stockCode, codeType);
            Observable observable = RequestInterval(second);
            observable = ConnTool.AddTokenOverdue(observable,ConnTool.YR_TYPE);
            if (baseActivity!=null){
                observable = ConnTool.AddExtraReqOfAct(observable, baseActivity);
            }else if (baseFragment!=null){
                observable = ConnTool.AddExtraReqOfFrag(observable, baseFragment);
            }
            observable.subscribe(new Consumer() {
                @Override
                public void accept(Object o) throws Throwable {
                    RequestApi.getInstance().loadTrend(stock, null);
                }
            });
        }else {
            PrintTool.PrintLogD(getClass().getSimpleName(), "股票类型匹配失败");
        }
    }


}
