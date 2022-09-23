package com.gzyslczx.yslc.events.yourui;

import com.gzyslczx.yslc.tools.yourui.HisTrendExtEntity;

public class YRFiveDayTimeEvent {

    private int ReqTime;
    private HisTrendExtEntity hisTrendExtEntity;

    public YRFiveDayTimeEvent(int reqTime, HisTrendExtEntity hisTrendExtEntity) {
        ReqTime = reqTime;
        this.hisTrendExtEntity = hisTrendExtEntity;
    }

    public int getReqTime() {
        return ReqTime;
    }

    public HisTrendExtEntity getHisTrendExtEntity() {
        return hisTrendExtEntity;
    }
}
