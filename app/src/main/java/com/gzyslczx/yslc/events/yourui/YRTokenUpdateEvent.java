package com.gzyslczx.yslc.events.yourui;

import com.gzyslczx.yslc.tools.PrintTool;

public class YRTokenUpdateEvent {

    private String token;

    public YRTokenUpdateEvent(String token) {
        PrintTool.PrintLogD(getClass().getSimpleName(), "Have YRToken");
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
