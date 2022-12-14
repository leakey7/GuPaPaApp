package com.gzyslczx.stockmarket;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gzyslczx.stockmarket.adapter.StockTimeChartAdapter;

import java.text.DecimalFormat;

public class StockTimeChart extends BaseMainChart implements OnGesturesListener{

    private int TimeColor, GridColor, DottedColor, IndicatorColor, AvePriceColor, RealPriceColor, PrePriceColor, UpColor, DownColor;
    private Paint TimePaint, GridPaint, DottedPaint, IndicatorPaint, AvePricePaint, RealPricePaint, PrePricePaint, UpPaint, DownPaint;
    private float TimeSize, UpTextSize, DownTextSize;
    private int ItemSize;
    private DecimalFormat format;
    private final String AM_930 = "9:30";
    private final String AM_1030 = "10:30";
    private final String AM1130_PM1300 = "11:30/13:00";
    private final String PM_1400 = "14:00";
    private final String PM_1500 = "15:00";
    private final String ZeroGain = "0.00%";
    private StockTimeChartAdapter adapter;
    private float LongPressX=0, LongPressY=0;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (adapter!=null) {
            adapter.RemoveChart();
        }
    }

    public StockTimeChart(Context context) {
        super(context);
    }

    public StockTimeChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StockTimeChart);
        TimeColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartTimeColor, ContextCompat.getColor(getContext(), R.color.TimeColor));
        GridColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartGridLineColor, ContextCompat.getColor(getContext(), R.color.GridColor));
        DottedColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartDottedLineColor, ContextCompat.getColor(getContext(), R.color.DottedColor));
        IndicatorColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartIndicatorColor, ContextCompat.getColor(getContext(), R.color.IndicatorColor));
        AvePriceColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartAvePriceColor, ContextCompat.getColor(getContext(), R.color.AvePriceColor));
        RealPriceColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartRealPriceColor, ContextCompat.getColor(getContext(), R.color.RealPriceColor));
        PrePriceColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartPrePriceColor, ContextCompat.getColor(getContext(), R.color.PrePriceColor));
        UpColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartUpColor, ContextCompat.getColor(getContext(), R.color.UpColor));
        DownColor = typedArray.getColor(R.styleable.StockTimeChart_TimeChartDownColor, ContextCompat.getColor(getContext(), R.color.DownColor));
        TimeSize = sp2px(getContext(), (int) (typedArray.getDimension(R.styleable.StockTimeChart_TimeChartTimeSize, 10)));
        UpTextSize = sp2px(getContext(), (int) typedArray.getDimension(R.styleable.StockTimeChart_TimeChartUpTextSize, 10));
        DownTextSize = sp2px(getContext(), (int) typedArray.getDimension(R.styleable.StockTimeChart_TimeChartDownTextSize, 10));
        ItemSize = typedArray.getInt(R.styleable.StockTimeChart_TimeChartItemSize, 241);
        InitPaint();
        format = new DecimalFormat("#0.00");
        setGesturesListener(this);
        typedArray.recycle();
    }

    public StockTimeChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StockTimeChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getDataSize() {
        if (adapter!=null) {
            return adapter.getDataSize();
        }
        return 0;
    }


    /*
     * 初始化画笔
     *
     * */
    @Override
    public void InitPaint() {
        PrintLog("初始化画笔");
        //时间画笔
        TimePaint = new Paint();
        TimePaint.setColor(TimeColor);
        TimePaint.setTextSize(TimeSize);
        TimePaint.setStrokeWidth(1);
        TimePaint.setStyle(Paint.Style.FILL);
        //宫格画笔
        GridPaint = new Paint();
        GridPaint.setColor(GridColor);
        GridPaint.setStrokeWidth(1);
        GridPaint.setStyle(Paint.Style.FILL);
        //虚线画笔
        DottedPaint = new Paint();
        DottedPaint.setColor(DottedColor);
        DottedPaint.setStyle(Paint.Style.FILL);
        DottedPaint.setStrokeWidth(dp2px(getContext(), 1));
        DottedPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        //指示画笔
        IndicatorPaint = new Paint();
        IndicatorPaint.setColor(IndicatorColor);
        IndicatorPaint.setStrokeWidth(2);
        IndicatorPaint.setStyle(Paint.Style.FILL);
        //均价画笔
        AvePricePaint = new Paint();
        AvePricePaint.setColor(AvePriceColor);
        AvePricePaint.setStrokeWidth(4);
        AvePricePaint.setStyle(Paint.Style.FILL);
        //实价画笔
        RealPricePaint = new Paint();
        RealPricePaint.setColor(RealPriceColor);
        RealPricePaint.setStrokeWidth(4);
        RealPricePaint.setStyle(Paint.Style.FILL);
        //昨收价画笔
        PrePricePaint = new Paint();
        PrePricePaint.setColor(PrePriceColor);
        PrePricePaint.setStrokeWidth(1);
        PrePricePaint.setStyle(Paint.Style.FILL);
        //上涨画笔
        UpPaint = new Paint();
        UpPaint.setColor(UpColor);
        UpPaint.setTextSize(UpTextSize);
        UpPaint.setStrokeWidth(1);
        UpPaint.setStyle(Paint.Style.FILL);
        //下跌画笔
        DownPaint = new Paint();
        DownPaint.setColor(DownColor);
        DownPaint.setTextSize(DownTextSize);
        DownPaint.setStrokeWidth(1);
        DownPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void UpdateDraw(Canvas canvas) {
        PrintLog("更新视图");
        float TopOnAxis = 0; //顶部坐标
        float LeftOnAxis = 0; //左端坐标
        float RightOnAxis = LeftOnAxis+getMeasuredWidth(); //右端坐标
        float BtmOnAxis = TopOnAxis+getMeasuredHeight(); //底部坐标
        float TimeAreaHeight = TimeSize+dp2px(getContext(),2); //底部时间区域高度
        float BeforeTimeOnAxis = BtmOnAxis-TimeAreaHeight; //边框底部坐标
        float BeforeTimeOfHalf = BeforeTimeOnAxis / 2f; //分时图高度二分一
        float WidthOfHalf = RightOnAxis / 2f; //分时图宽度二分一
        float BeforeTimeOfQuarter = BeforeTimeOfHalf / 2f; //分时图高度四分一
        float WidthOfQuarter = WidthOfHalf / 2f; //分时图宽度四分一
        float ThreeInFourOfHeight = BeforeTimeOnAxis - BeforeTimeOfQuarter; //分时图四分三高度
        float ThreeInFourOfWidth = RightOnAxis - WidthOfQuarter; //分时图四分三宽度
        float ZeroGainOnYAxis = BeforeTimeOfHalf + TimePaint.measureText(ZeroGain.substring(0, 1))/2f; //0涨幅Y点
        DrawGrid(canvas, TopOnAxis, LeftOnAxis, BeforeTimeOnAxis, RightOnAxis,
                WidthOfHalf, WidthOfQuarter, BeforeTimeOfHalf, BeforeTimeOfQuarter, ThreeInFourOfHeight, ThreeInFourOfWidth); //绘制网格
        DrawBtmTime(canvas, LeftOnAxis, RightOnAxis, BtmOnAxis, WidthOfHalf, WidthOfQuarter, ThreeInFourOfWidth, ZeroGainOnYAxis); //绘制时间
        if (adapter!=null && adapter.getDataSize()>0){
            TimePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(format.format(adapter.getPrePrice()), LeftOnAxis, ZeroGainOnYAxis, TimePaint); //绘制昨收价
            float AveWidth = getMeasuredWidth() / (float)ItemSize; //平均宽度
            float AveHeight = BeforeTimeOnAxis / (adapter.getMaxValue()-adapter.getMinValue()); //平均高度
            try {
                DrawTimeChart(canvas, AveWidth, AveHeight); //绘制分时数据
                DrawMostValue(canvas, LeftOnAxis, TopOnAxis, RightOnAxis, BeforeTimeOnAxis); //绘制最值和最值幅度
            }catch (NullPointerException nullPointerException){
                PrintLog("TimeChartMode Is Null");
            }
        }
        if (isEnableLongPress() && isLongPress()){
            if (LongPressX<LeftOnAxis){
                LongPressX = LeftOnAxis;
            }else if (LongPressX>RightOnAxis){
                LongPressX = RightOnAxis;
            }
            if (LongPressY<TopOnAxis){
                LongPressY = TopOnAxis;
            }else if (LongPressY>BeforeTimeOnAxis){
                LongPressY = BeforeTimeOnAxis;
            }
            canvas.drawLine(LongPressX, TopOnAxis, LongPressX, BeforeTimeOnAxis, IndicatorPaint); //指示竖线
            canvas.drawLine(LeftOnAxis, LongPressY, RightOnAxis, LongPressY, IndicatorPaint); //指示横线
        }
    }

    @Override
    public int getItemSize() {
        return ItemSize;
    }

    @Override
    public void setItemSize(int itemSize) {
        ItemSize = itemSize;
    }

    /*
    * 网格
    * */
    private void DrawGrid(Canvas canvas, float top, float left, float btm, float right,
                          float widthHalf, float widthQuarter, float heightHalf, float heightQuarter,
                          float threeInFourOfHeight, float threeInFourOfWidth){
        PrintLog("绘制网格");
        GridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, right, btm, GridPaint);
        GridPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(left, top, right, top, GridPaint); //一横线
        canvas.drawLine(left, heightQuarter, right, heightQuarter, GridPaint); //二横线
        canvas.drawLine(left, heightHalf, right, heightHalf, DottedPaint); //中横线
        canvas.drawLine(left, threeInFourOfHeight, right, threeInFourOfHeight, GridPaint); //四横线
        canvas.drawLine(left, btm, right, btm, GridPaint); //五横线
        canvas.drawLine(left, top, left, btm, GridPaint); //一竖线
        canvas.drawLine(widthQuarter, top, widthQuarter, btm, GridPaint); //二竖线
        canvas.drawLine(widthHalf, top, widthHalf, btm, GridPaint); //中竖线
        canvas.drawLine(threeInFourOfWidth, top, threeInFourOfWidth, btm, GridPaint); //四竖线
        canvas.drawLine(right, top, right, btm, GridPaint); //五竖线
    }
    /*
    * 底部时间
    * */
    private void DrawBtmTime(Canvas canvas, float left, float right, float btm, float widthHalf, float widthQuarter, float threeInFourOfWidth, float zeroOnAxis){
        PrintLog("绘制时间轴");
        float TimeOnAxis = btm - dp2px(getContext(), 1);
        TimePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(AM_930, left, TimeOnAxis, TimePaint); // 9：30
        TimePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(AM_1030, widthQuarter, TimeOnAxis, TimePaint); // 10：30
        canvas.drawText(AM1130_PM1300, widthHalf, TimeOnAxis, TimePaint); // 11：30/13：00
        canvas.drawText(PM_1400, threeInFourOfWidth, TimeOnAxis, TimePaint); // 14：00
        TimePaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(PM_1500, right, TimeOnAxis, TimePaint); // 15：00
        canvas.drawText(ZeroGain, right, zeroOnAxis, TimePaint); //0.00%
    }
    /*
    * 分时图
    * */
    private void DrawTimeChart(Canvas canvas, float aveWidth, float aveHeight) throws NullPointerException{
        PrintLog("绘制分时数据");
        for (int i=0; i<adapter.getDataSize(); i++){
            float X = aveWidth*i; //X点
            float YOfAve = (adapter.getMaxValue()-adapter.getStockTimeAvePrice(i))*aveHeight; //均价Y点
            float YOfReal = (adapter.getMaxValue()-adapter.getStockTimeRealPrice(i))*aveHeight; //实价Y点
            if (i==0){
                //第一点为圆点
                canvas.drawCircle(X, YOfAve, 1, AvePricePaint);
                canvas.drawCircle(X, YOfReal, 1, RealPricePaint);
            }else {
                float LastX = aveWidth*(i-1);
                float LastYOfAve = (adapter.getMaxValue()-adapter.getStockTimeAvePrice(i-1))*aveHeight; //上一个均价Y点
                float LastYOfReal = (adapter.getMaxValue()-adapter.getStockTimeRealPrice(i-1))*aveHeight; //上一个实价Y点
                canvas.drawLine(LastX, LastYOfAve, X, YOfAve, AvePricePaint); //均价线
                canvas.drawLine(LastX, LastYOfReal, X, YOfReal, RealPricePaint); //实价线
            }
        }
    }
    /*
    * 绘制最值和最值幅度
    * */
    private void DrawMostValue(Canvas canvas, float left, float top, float right, float btm) throws NullPointerException{
        float MaxValueOnYAxis = top+UpTextSize;
        UpPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(format.format(adapter.getMaxValue()), left, MaxValueOnYAxis, UpPaint); //最大值价格
        String TopGain = String.format("%s%%", format.format(adapter.getMaxGain())); //最大值幅度
        UpPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(TopGain, right, MaxValueOnYAxis, UpPaint); //最大值幅度

        float MinValueOnYAxis = btm-sp2px(getContext(), 1);
        DownPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(format.format(adapter.getMinValue()), left, MinValueOnYAxis, DownPaint); //最小值价格
        String BtmGain = String.format("%s%%", format.format(adapter.getMinGain()));
        DownPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(BtmGain, right, MinValueOnYAxis, DownPaint); //最小值幅度
    }

    public void setAdapter(StockTimeChartAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setChart(this);
    }

    /*
    * 长按滑动
    * */
    @Override
    public void OnLongPressMove(float moveX, float moveY) {
        LongPressX = moveX;
        LongPressY = moveY;
        invalidate();
    }

    /*
    * 长按抬起
    * */
    @Override
    public void OnLongPressAfterUp(float upX, float upY) {
        LongPressX = upX;
        LongPressY = upY;
        invalidate();
    }

    @Override
    public void CancelLongPressBySingleClick() {
        invalidate();
    }

    /*
    * 单指滑动
    * */
    @Override
    public void OnSingleMove(float moveX, float moveY, float distanceX, float distanceY) {

    }

    /*
    * 双指缩放
    * */
    @Override
    public void OnZoom(float aX, float aY, float bX, float bY) {

    }
}