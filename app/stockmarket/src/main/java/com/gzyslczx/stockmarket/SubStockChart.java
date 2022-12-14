package com.gzyslczx.stockmarket;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class SubStockChart extends BaseSubChart{

    private int UpColor, DownColor, GridColor, IndicatorColor, EqualColor;
    private Paint UpPaint, DownPaint, GridPaint, IndicatorPaint, EqualPaint;
    private int SubType;
    private float LongPressX, LongPressY;

    public SubStockChart(Context context) {
        super(context);
    }

    public SubStockChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubStockChart);
        UpColor = typedArray.getColor(R.styleable.SubStockChart_SubUpColor, ContextCompat.getColor(getContext(), R.color.UpColor));
        DownColor = typedArray.getColor(R.styleable.SubStockChart_SubDownColor, ContextCompat.getColor(getContext(), R.color.DownColor));
        GridColor = typedArray.getColor(R.styleable.SubStockChart_SubGridColor, ContextCompat.getColor(getContext(), R.color.GridColor));
        IndicatorColor = typedArray.getColor(R.styleable.SubStockChart_SubIndicatorColor, ContextCompat.getColor(getContext(), R.color.IndicatorColor));
        EqualColor = typedArray.getColor(R.styleable.SubStockChart_SubEqualColor, ContextCompat.getColor(getContext(), R.color.PrePriceColor));
        SubType = typedArray.getInt(R.styleable.SubStockChart_SubType, SubStockType.TimeVolume_Type);
        InitPaint();
        typedArray.recycle();
    }

    public SubStockChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public SubStockChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void InitPaint() {
        PrintLog("???????????????");
        //????????????
        UpPaint = new Paint();
        UpPaint.setColor(UpColor);
        UpPaint.setStrokeWidth(1);
        UpPaint.setStyle(Paint.Style.FILL);
        //????????????
        DownPaint = new Paint();
        DownPaint.setColor(DownColor);
        DownPaint.setStrokeWidth(1);
        DownPaint.setStyle(Paint.Style.FILL);
        //????????????
        GridPaint = new Paint();
        GridPaint.setColor(GridColor);
        GridPaint.setStrokeWidth(1);
        GridPaint.setStyle(Paint.Style.FILL);
        //????????????
        IndicatorPaint = new Paint();
        IndicatorPaint.setColor(IndicatorColor);
        IndicatorPaint.setStrokeWidth(2);
        IndicatorPaint.setStyle(Paint.Style.FILL);
        //????????????
        EqualPaint = new Paint();
        EqualPaint.setColor(EqualColor);
        EqualPaint.setStrokeWidth(1);
        EqualPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void UpdateDraw(Canvas canvas) {
        PrintLog("????????????");
        float TopOnAxis = 0; //????????????
        float LeftOnAxis = 0; //????????????
        float RightOnAxis = LeftOnAxis+getMeasuredWidth(); //????????????
        float BtmOnAxis = TopOnAxis+getMeasuredHeight(); //????????????
        float HalfOfViewHeight = getMeasuredHeight() / 2f; //????????????????????????
        float HalfOfViewWidth = getMeasuredWidth() / 2f; //????????????????????????
        DrawGridView(canvas, LeftOnAxis, TopOnAxis, RightOnAxis, BtmOnAxis, HalfOfViewHeight, HalfOfViewWidth); //????????????
        if (SubType==SubStockType.TimeVolume_Type){
            if (getMainChart()!=null && getTimeChartMode()!=null && getMainChart().getDataSize()!=0){
                PrintLog("???????????????????????????");
                float AveWidthOfItem = getMeasuredWidth() / (float) getItemSize(); //??????????????????
                float AveHeightOfItem = getMeasuredHeight() / (float) getTimeChartMode().getMaxVolume(); //???????????????
                float ItemInterval = AveWidthOfItem*0.2f; //??????
                DrawSubOfTimeVolume(canvas, LeftOnAxis, BtmOnAxis, AveWidthOfItem, AveHeightOfItem, ItemInterval);
            }
        }

        //???????????????
        if (getMainChart()!=null && getMainChart().isEnableLongPress() && getMainChart().isLongPress()){
            if (LongPressX < LeftOnAxis){
                LongPressX = LeftOnAxis;
            }else if (LongPressX > RightOnAxis){
                LongPressX = RightOnAxis;
            }
            canvas.drawLine(LongPressX, TopOnAxis, LongPressX, BtmOnAxis, IndicatorPaint); //????????????
        }

    }

    @Override
    public int getItemSize() {
        if (getMainChart()!=null){
            return getMainChart().getItemSize();
        }
        return 1;
    }

    @Override
    public void setItemSize(int itemSize) {
        if (getMainChart()!=null){
            getMainChart().setItemSize(itemSize);
        }
    }

    /*
    * ????????????
    * */
    private void DrawGridView(Canvas canvas, float left, float top, float right, float btm, float halfOfHeight, float halfOfWidth){
        GridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, right, btm, GridPaint);
        GridPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(left, top, right, top, GridPaint); //?????????
        canvas.drawLine(left, halfOfHeight, right, halfOfHeight, GridPaint); //?????????
        canvas.drawLine(left, btm, right, btm, GridPaint); //?????????
        canvas.drawLine(left, top, left, btm, GridPaint); //?????????
        canvas.drawLine(halfOfWidth, top, halfOfWidth, btm, GridPaint); //?????????
        canvas.drawLine(right, top, right, btm, GridPaint); //?????????
    }

    /*
    * ?????????????????????
    * */
    private void DrawSubOfTimeVolume(Canvas canvas, float left, float btm,
                                     float aveWidthOfItem, float aveHeightOfItem, float interval){
        for (int i=0; i<getMainChart().getDataSize(); i++){
            float itemLeft = left+aveWidthOfItem*i+interval;
            float itemRight = left+aveWidthOfItem*(i+1)-interval;
            float itemTop = btm-aveHeightOfItem * (float) getTimeChartMode().getStockTimeVolume(i);
            if (i==0){
                float PrePrice = getTimeChartMode().getPrePrice();
                if (PrePrice>getTimeChartMode().getStockTimeRealPrice(i)){
                    //????????????????????????
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, DownPaint);
                }else if (PrePrice<getTimeChartMode().getStockTimeRealPrice(i)){
                    //????????????????????????
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, UpPaint);
                }else {
                    //????????????????????????
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, EqualPaint);
                }
            }else {
                if (getTimeChartMode().getStockTimeRealPrice(i-1)>getTimeChartMode().getStockTimeRealPrice(i)){
                    //????????????
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, DownPaint);
                }else if (getTimeChartMode().getStockTimeRealPrice(i-1)<getTimeChartMode().getStockTimeRealPrice(i)){
                    //????????????
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, UpPaint);
                }else {
                    //????????????????????????
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, EqualPaint);
                }
            }
        }
    }

    @Override
    public void NoticeSubUpdate() {
        //????????????????????????
        PrintLog("????????????????????????");
        invalidate();
    }

    /*
    * ????????????
    * */
    @Override
    public void LongPressMove(float moveX, float moveY) {
        LongPressX = moveX;
        LongPressY = moveY;
        invalidate();
    }

    /*
    * ??????????????????
    * */
    @Override
    public void CancelLongPress() {
        invalidate();
    }

    /*
    * ????????????
    * */
    @Override
    public void SingleMove(float moveX, float moveY, float distanceX, float distanceY) {

    }

    /*
    * ??????
    * */
    @Override
    public void Zoom(float aX, float aY, float bX, float bY) {

    }
}
