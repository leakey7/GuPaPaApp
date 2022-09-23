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
        PrintLog("初始化画笔");
        //上涨画笔
        UpPaint = new Paint();
        UpPaint.setColor(UpColor);
        UpPaint.setStrokeWidth(1);
        UpPaint.setStyle(Paint.Style.FILL);
        //下跌画笔
        DownPaint = new Paint();
        DownPaint.setColor(DownColor);
        DownPaint.setStrokeWidth(1);
        DownPaint.setStyle(Paint.Style.FILL);
        //宫格画笔
        GridPaint = new Paint();
        GridPaint.setColor(GridColor);
        GridPaint.setStrokeWidth(1);
        GridPaint.setStyle(Paint.Style.FILL);
        //指示画笔
        IndicatorPaint = new Paint();
        IndicatorPaint.setColor(IndicatorColor);
        IndicatorPaint.setStrokeWidth(2);
        IndicatorPaint.setStyle(Paint.Style.FILL);
        //等价画笔
        EqualPaint = new Paint();
        EqualPaint.setColor(EqualColor);
        EqualPaint.setStrokeWidth(1);
        EqualPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void UpdateDraw(Canvas canvas) {
        PrintLog("更新视图");
        float TopOnAxis = 0; //顶部坐标
        float LeftOnAxis = 0; //左端坐标
        float RightOnAxis = LeftOnAxis+getMeasuredWidth(); //右端坐标
        float BtmOnAxis = TopOnAxis+getMeasuredHeight(); //底部坐标
        float HalfOfViewHeight = getMeasuredHeight() / 2f; //分时图高度二分一
        float HalfOfViewWidth = getMeasuredWidth() / 2f; //分时图宽度二分一
        DrawGridView(canvas, LeftOnAxis, TopOnAxis, RightOnAxis, BtmOnAxis, HalfOfViewHeight, HalfOfViewWidth); //绘制网格
        if (SubType==SubStockType.TimeVolume_Type){
            if (getMainChart()!=null && getTimeChartMode()!=null && getMainChart().getDataSize()!=0){
                PrintLog("更新分时成交量附图");
                float AveWidthOfItem = getMeasuredWidth() / (float) getItemSize(); //每项平均占宽
                float AveHeightOfItem = getMeasuredHeight() / (float) getTimeChartMode().getMaxVolume(); //每单位高度
                float ItemInterval = AveWidthOfItem*0.2f; //间隔
                DrawSubOfTimeVolume(canvas, LeftOnAxis, BtmOnAxis, AveWidthOfItem, AveHeightOfItem, ItemInterval);
            }
        }

        //绘制指示线
        if (getMainChart()!=null && getMainChart().isEnableLongPress() && getMainChart().isLongPress()){
            if (LongPressX < LeftOnAxis){
                LongPressX = LeftOnAxis;
            }else if (LongPressX > RightOnAxis){
                LongPressX = RightOnAxis;
            }
            canvas.drawLine(LongPressX, TopOnAxis, LongPressX, BtmOnAxis, IndicatorPaint); //竖指示线
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
    * 绘制网格
    * */
    private void DrawGridView(Canvas canvas, float left, float top, float right, float btm, float halfOfHeight, float halfOfWidth){
        GridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, right, btm, GridPaint);
        GridPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(left, top, right, top, GridPaint); //顶横线
        canvas.drawLine(left, halfOfHeight, right, halfOfHeight, GridPaint); //中横线
        canvas.drawLine(left, btm, right, btm, GridPaint); //底横线
        canvas.drawLine(left, top, left, btm, GridPaint); //左竖线
        canvas.drawLine(halfOfWidth, top, halfOfWidth, btm, GridPaint); //中竖线
        canvas.drawLine(right, top, right, btm, GridPaint); //右竖线
    }

    /*
    * 绘制成交量副图
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
                    //成交价小于昨收价
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, DownPaint);
                }else if (PrePrice<getTimeChartMode().getStockTimeRealPrice(i)){
                    //成交价大于昨收价
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, UpPaint);
                }else {
                    //成交价等于昨收价
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, EqualPaint);
                }
            }else {
                if (getTimeChartMode().getStockTimeRealPrice(i-1)>getTimeChartMode().getStockTimeRealPrice(i)){
                    //成交价降
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, DownPaint);
                }else if (getTimeChartMode().getStockTimeRealPrice(i-1)<getTimeChartMode().getStockTimeRealPrice(i)){
                    //成交价升
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, UpPaint);
                }else {
                    //成交价等于昨收价
                    canvas.drawRect(itemLeft, itemTop, itemRight, btm, EqualPaint);
                }
            }
        }
    }

    @Override
    public void NoticeSubUpdate() {
        //主图通知副图更新
        PrintLog("主图通知副图更新");
        invalidate();
    }

    /*
    * 长按滑动
    * */
    @Override
    public void LongPressMove(float moveX, float moveY) {
        LongPressX = moveX;
        LongPressY = moveY;
        invalidate();
    }

    /*
    * 单击取消长按
    * */
    @Override
    public void CancelLongPress() {
        invalidate();
    }

    /*
    * 单指滑动
    * */
    @Override
    public void SingleMove(float moveX, float moveY, float distanceX, float distanceY) {

    }

    /*
    * 缩放
    * */
    @Override
    public void Zoom(float aX, float aY, float bX, float bY) {

    }
}
