package com.gzyslczx.stockmarket.Response;

public class TokenRes {

    private int code;
    private String msg;
    private String token;
    private long endDate;

    public String getMsg() {
        return msg;
    }

    public String getToken() {
        return token;
    }

    public long getEndDate() {
        return endDate;
    }

    public boolean isSuccess(){
        if (code==0){
            return true;
        }
        return false;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }
}
