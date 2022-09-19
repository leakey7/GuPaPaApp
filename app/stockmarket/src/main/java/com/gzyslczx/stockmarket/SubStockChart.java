package com.gzyslczx.stockmarket;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class SubStockChart extends BaseChart{

    private int UpColor, DownColor, GridColor, IndicatorColor, EqualColor;
    private Paint UpPaint, DownPaint, GridPaint, IndicatorPaint, EqualPaint;
    private int SubType;
    private BaseChart MainChart;

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
        SubType = typedArray.getInt(R.styleable.SubStockChart_SubType, SubStockType.Volume_Type);
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
        IndicatorPaint.setStrokeWidth(1);
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
        float ItemInterval = dp2px(getContext(), 2);
        DrawGridView(canvas, LeftOnAxis, TopOnAxis, RightOnAxis, BtmOnAxis, HalfOfViewHeight, HalfOfViewWidth); //绘制网格
        if (getMainChart()!=null && SubType==SubStockType.Volume_Type){
            float AveWidthOfItem = getMeasuredWidth() / (float) getItemSize();
            float AveHeightOfItem = getMeasuredHeight() / getAdapter().getTimeChartMode().getMaxVolume();

        }
    }

    @Override
    public int getItemSize() {
        if (MainChart!=null){
            return MainChart.getItemSize();
        }
        return 1;
    }

    public void setMainChart(BaseChart mainChart) {
        MainChart = mainChart;
    }

    public BaseChart getMainChart() {
        return MainChart;
    }

    /*
    * 绘制网格
    * */
    private void DrawGridView(Canvas canvas, float left, float top, float right, float btm, float halfOfHeight, float halfOfWidth){
        GridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, right, btm, GridPaint); //边框
        GridPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(left, halfOfHeight, right, halfOfHeight, GridPaint); //中横线
        canvas.drawLine(halfOfWidth, top, halfOfWidth, btm, GridPaint); //中竖线
    }

    /*
    * 绘制成交量副图
    * */
    private void DrawSubOfVolume(Canvas canvas, float aveWidthOfItem, float aveHeightOfItem){

    }

}
