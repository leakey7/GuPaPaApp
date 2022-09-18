package com.gzyslczx.stockmarket.Request;

public class StockTimeReq {

    private String stockCode;
    private int mlDate;

    public StockTimeReq(String stockCode, int date) {
        this.stockCode = stockCode;
        this.mlDate = date;
    }
}
