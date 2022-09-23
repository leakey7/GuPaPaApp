package com.gzyslczx.stockmarket;

public interface MainSubChartLink {

    void NoticeSubUpdate();

    void LongPressMove(float moveX, float moveY);

    void CancelLongPress();

    void SingleMove(float moveX, float moveY, float distanceX, float distanceY);

    void Zoom(float aX, float aY, float bX, float bY);

}
