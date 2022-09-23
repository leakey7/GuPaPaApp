package com.gzyslczx.stockmarket;

public interface OnGesturesListener {

    void OnLongPressMove(float moveX, float moveY); //长按滑动

    void OnLongPressAfterUp(float upX, float upY); //长按结束抬起

    void CancelLongPressBySingleClick(); //单击取消长按

    void OnSingleMove(float moveX, float moveY, float distanceX, float distanceY); //单指滑动

    void OnZoom(float aX, float aY, float bX, float bY); //缩放：a代表A指，b代表B指

}
