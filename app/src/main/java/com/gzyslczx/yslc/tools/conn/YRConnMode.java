package com.gzyslczx.yslc.tools.conn;

import com.gzyslczx.stockmarket.Request.StockTimeReq;
import com.gzyslczx.stockmarket.Request.TokenReq;
import com.gzyslczx.stockmarket.Response.StockTimeRes;
import com.gzyslczx.stockmarket.Response.TokenRes;
import com.gzyslczx.stockmarket.YRConnPath;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface YRConnMode {

    //Token
    @POST(YRConnPath.TokenPath)
    Observable<TokenRes> RequestToken(@Header(YRConnPath.Origin) String origin, @Body TokenReq body);

    //分时
    @POST(YRConnPath.StockTimePath)
    Observable<StockTimeRes> RequestStockTime(@HeaderMap Map<String, String> map, @Body StockTimeReq body);

}
