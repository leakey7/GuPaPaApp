package com.gzyslczx.yslc.tools.conn;

import android.util.Log;

import com.gzyslczx.yslc.BaseActivity;
import com.gzyslczx.yslc.fragment.BaseFragment;
import com.gzyslczx.yslc.tools.PrintTool;
import com.gzyslczx.yslc.tools.SpTool;
import com.trello.rxlifecycle4.android.ActivityEvent;
import com.trello.rxlifecycle4.android.FragmentEvent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ConnTool {
    public static final int GB_TYPE=0;
    public static final int FT_TYPE=1;
    public static final int YR_TYPE=2;

    /*
     * 附加重试请求
     * */
    public static Observable AddRetryReq(Observable observable, String TAG) {
        return observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Throwable {
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Throwable {
                        if (throwable instanceof IOException) {
                            Log.d(TAG, String.format("发生网络异常:%s,3s后重试请求", throwable.getMessage()));
                            return Observable.just(1).delay(3, TimeUnit.SECONDS);
                        }
                        Log.d(TAG, String.format("发生未知异常:%s", throwable.getMessage()));
                        return Observable.error(new Throwable(
                                String.format("发生未知异常:%s", throwable.getMessage())));
                    }
                });
            }
        });
    }

    /*
     * 添加常规项--Activity
     * */
    public static Observable AddExtraReqOfAct(Observable observable, BaseActivity activity) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /*
     * 添加常规项--Fragment
     * */
    public static Observable AddExtraReqOfFrag(Observable observable, BaseFragment fragment) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY));
    }

    /*
    * 添加Token过期检测
    * type:0=GuBb；1=FundTong；2=YR
    * */
    public static Observable AddTokenOverdue(Observable observable, int type){
        return observable.filter(new Predicate() {
            @Override
            public boolean test(Object o) throws Throwable {
                if (type==GB_TYPE){
                    //股扒扒过滤
                }else if (type==FT_TYPE){
                    //基金通过滤
                }else if (type==YR_TYPE){
                    //友睿过滤
                    String YRTTime =  SpTool.GetInfo(SpTool.YRTokenTime);
                    PrintTool.PrintLogD(getClass().getSimpleName(), String.format("友睿过滤，Token时效:%s", YRTTime));
                    return true;
                }
                return false;
            }
        });
    }


}
