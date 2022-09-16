package com.gzyslczx.stockmarket.Request;

public class StockTimeReq {

    private String stockCode;
    private int date;

    public StockTimeReq(String stockCode, int date) {
        this.stockCode = stockCode;
        this.date = date;
    }
}
