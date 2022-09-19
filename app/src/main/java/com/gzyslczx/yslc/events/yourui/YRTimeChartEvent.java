package com.gzyslczx.yslc.events.yourui;

import com.gzyslczx.yslc.tools.yourui.TrendExtEntity;

public class YRTimeChartEvent {

    private boolean success;
    private TrendExtEntity entity;

    public YRTimeChartEvent(boolean success, TrendExtEntity entity) {
        this.success = success;
        this.entity = entity;
    }

    public boolean isSuccess() {
        return success;
    }

    public TrendExtEntity getEntity() {
        return entity;
    }
}
