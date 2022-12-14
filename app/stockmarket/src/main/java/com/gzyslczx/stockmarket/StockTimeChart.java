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
     * ???????????????
     *
     * */
    @Override
    public void InitPaint() {
        PrintLog("???????????????");
        //????????????
        TimePaint = new Paint();
        TimePaint.setColor(TimeColor);
        TimePaint.setTextSize(TimeSize);
        TimePaint.setStrokeWidth(1);
        TimePaint.setStyle(Paint.Style.FILL);
        //????????????
        GridPaint = new Paint();
        GridPaint.setColor(GridColor);
        GridPaint.setStrokeWidth(1);
        GridPaint.setStyle(Paint.Style.FILL);
        //????????????
        DottedPaint = new Paint();
        DottedPaint.setColor(DottedColor);
        DottedPaint.setStyle(Paint.Style.FILL);
        DottedPaint.setStrokeWidth(dp2px(getContext(), 1));
        DottedPaint.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        //????????????
        IndicatorPaint = new Paint();
        IndicatorPaint.setColor(IndicatorColor);
        IndicatorPaint.setStrokeWidth(2);
        IndicatorPaint.setStyle(Paint.Style.FILL);
        //????????????
        AvePricePaint = new Paint();
        AvePricePaint.setColor(AvePriceColor);
        AvePricePaint.setStrokeWidth(4);
        AvePricePaint.setStyle(Paint.Style.FILL);
        //????????????
        RealPricePaint = new Paint();
        RealPricePaint.setColor(RealPriceColor);
        RealPricePaint.setStrokeWidth(4);
        RealPricePaint.setStyle(Paint.Style.FILL);
        //???????????????
        PrePricePaint = new Paint();
        PrePricePaint.setColor(PrePriceColor);
        PrePricePaint.setStrokeWidth(1);
        PrePricePaint.setStyle(Paint.Style.FILL);
        //????????????
        UpPaint = new Paint();
        UpPaint.setColor(UpColor);
        UpPaint.setTextSize(UpTextSize);
        UpPaint.setStrokeWidth(1);
        UpPaint.setStyle(Paint.Style.FILL);
        //????????????
        DownPaint = new Paint();
        DownPaint.setColor(DownColor);
        DownPaint.setTextSize(DownTextSize);
        DownPaint.setStrokeWidth(1);
        DownPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void UpdateDraw(Canvas canvas) {
        PrintLog("????????????");
        float TopOnAxis = 0; //????????????
        float LeftOnAxis = 0; //????????????
        float RightOnAxis = LeftOnAxis+getMeasuredWidth(); //????????????
        float BtmOnAxis = TopOnAxis+getMeasuredHeight(); //????????????
        float TimeAreaHeight = TimeSize+dp2px(getContext(),2); //????????????????????????
        float BeforeTimeOnAxis = BtmOnAxis-TimeAreaHeight; //??????????????????
        float BeforeTimeOfHalf = BeforeTimeOnAxis / 2f; //????????????????????????
        float WidthOfHalf = RightOnAxis / 2f; //????????????????????????
        float BeforeTimeOfQuarter = BeforeTimeOfHalf / 2f; //????????????????????????
        float WidthOfQuarter = WidthOfHalf / 2f; //????????????????????????
        float ThreeInFourOfHeight = BeforeTimeOnAxis - BeforeTimeOfQuarter; //????????????????????????
        float ThreeInFourOfWidth = RightOnAxis - WidthOfQuarter; //????????????????????????
        float ZeroGainOnYAxis = BeforeTimeOfHalf + TimePaint.measureText(ZeroGain.substring(0, 1))/2f; //0??????Y???
        DrawGrid(canvas, TopOnAxis, LeftOnAxis, BeforeTimeOnAxis, RightOnAxis,
                WidthOfHalf, WidthOfQuarter, BeforeTimeOfHalf, BeforeTimeOfQuarter, ThreeInFourOfHeight, ThreeInFourOfWidth); //????????????
        DrawBtmTime(canvas, LeftOnAxis, RightOnAxis, BtmOnAxis, WidthOfHalf, WidthOfQuarter, ThreeInFourOfWidth, ZeroGainOnYAxis); //????????????
        if (adapter!=null && adapter.getDataSize()>0){
            TimePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(format.format(adapter.getPrePrice()), LeftOnAxis, ZeroGainOnYAxis, TimePaint); //???????????????
            float AveWidth = getMeasuredWidth() / (float)ItemSize; //????????????
            float AveHeight = BeforeTimeOnAxis / (adapter.getMaxValue()-adapter.getMinValue()); //????????????
            try {
                DrawTimeChart(canvas, AveWidth, AveHeight); //??????????????????
                DrawMostValue(canvas, LeftOnAxis, TopOnAxis, RightOnAxis, BeforeTimeOnAxis); //???????????????????????????
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
            canvas.drawLine(LongPressX, TopOnAxis, LongPressX, BeforeTimeOnAxis, IndicatorPaint); //????????????
            canvas.drawLine(LeftOnAxis, LongPressY, RightOnAxis, LongPressY, IndicatorPaint); //????????????
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
    * ??????
    * */
    private void DrawGrid(Canvas canvas, float top, float left, float btm, float right,
                          float widthHalf, float widthQuarter, float heightHalf, float heightQuarter,
                          float threeInFourOfHeight, float threeInFourOfWidth){
        PrintLog("????????????");
        GridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, right, btm, GridPaint);
        GridPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(left, top, right, top, GridPaint); //?????????
        canvas.drawLine(left, heightQuarter, right, heightQuarter, GridPaint); //?????????
        canvas.drawLine(left, heightHalf, right, heightHalf, DottedPaint); //?????????
        canvas.drawLine(left, threeInFourOfHeight, right, threeInFourOfHeight, GridPaint); //?????????
        canvas.drawLine(left, btm, right, btm, GridPaint); //?????????
        canvas.drawLine(left, top, left, btm, GridPaint); //?????????
        canvas.drawLine(widthQuarter, top, widthQuarter, btm, GridPaint); //?????????
        canvas.drawLine(widthHalf, top, widthHalf, btm, GridPaint); //?????????
        canvas.drawLine(threeInFourOfWidth, top, threeInFourOfWidth, btm, GridPaint); //?????????
        canvas.drawLine(right, top, right, btm, GridPaint); //?????????
    }
    /*
    * ????????????
    * */
    private void DrawBtmTime(Canvas canvas, float left, float right, float btm, float widthHalf, float widthQuarter, float threeInFourOfWidth, float zeroOnAxis){
        PrintLog("???????????????");
        float TimeOnAxis = btm - dp2px(getContext(), 1);
        TimePaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(AM_930, left, TimeOnAxis, TimePaint); // 9???30
        TimePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(AM_1030, widthQuarter, TimeOnAxis, TimePaint); // 10???30
        canvas.drawText(AM1130_PM1300, widthHalf, TimeOnAxis, TimePaint); // 11???30/13???00
        canvas.drawText(PM_1400, threeInFourOfWidth, TimeOnAxis, TimePaint); // 14???00
        TimePaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(PM_1500, right, TimeOnAxis, TimePaint); // 15???00
        canvas.drawText(ZeroGain, right, zeroOnAxis, TimePaint); //0.00%
    }
    /*
    * ?????????
    * */
    private void DrawTimeChart(Canvas canvas, float aveWidth, float aveHeight) throws NullPointerException{
        PrintLog("??????????????????");
        for (int i=0; i<adapter.getDataSize(); i++){
            float X = aveWidth*i; //X???
            float YOfAve = (adapter.getMaxValue()-adapter.getStockTimeAvePrice(i))*aveHeight; //??????Y???
            float YOfReal = (adapter.getMaxValue()-adapter.getStockTimeRealPrice(i))*aveHeight; //??????Y???
            if (i==0){
                //??????????????????
                canvas.drawCircle(X, YOfAve, 1, AvePricePaint);
                canvas.drawCircle(X, YOfReal, 1, RealPricePaint);
            }else {
                float LastX = aveWidth*(i-1);
                float LastYOfAve = (adapter.getMaxValue()-adapter.getStockTimeAvePrice(i-1))*aveHeight; //???????????????Y???
                float LastYOfReal = (adapter.getMaxValue()-adapter.getStockTimeRealPrice(i-1))*aveHeight; //???????????????Y???
                canvas.drawLine(LastX, LastYOfAve, X, YOfAve, AvePricePaint); //?????????
                canvas.drawLine(LastX, LastYOfReal, X, YOfReal, RealPricePaint); //?????????
            }
        }
    }
    /*
    * ???????????????????????????
    * */
    private void DrawMostValue(Canvas canvas, float left, float top, float right, float btm) throws NullPointerException{
        float MaxValueOnYAxis = top+UpTextSize;
        UpPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(format.format(adapter.getMaxValue()), left, MaxValueOnYAxis, UpPaint); //???????????????
        String TopGain = String.format("%s%%", format.format(adapter.getMaxGain())); //???????????????
        UpPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(TopGain, right, MaxValueOnYAxis, UpPaint); //???????????????

        float MinValueOnYAxis = btm-sp2px(getContext(), 1);
        DownPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(format.format(adapter.getMinValue()), left, MinValueOnYAxis, DownPaint); //???????????????
        String BtmGain = String.format("%s%%", format.format(adapter.getMinGain()));
        DownPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(BtmGain, right, MinValueOnYAxis, DownPaint); //???????????????
    }

    public void setAdapter(StockTimeChartAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setChart(this);
    }

    /*
    * ????????????
    * */
    @Override
    public void OnLongPressMove(float moveX, float moveY) {
        LongPressX = moveX;
        LongPressY = moveY;
        invalidate();
    }

    /*
    * ????????????
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
    * ????????????
    * */
    @Override
    public void OnSingleMove(float moveX, float moveY, float distanceX, float distanceY) {

    }

    /*
    * ????????????
    * */
    @Override
    public void OnZoom(float aX, float aY, float bX, float bY) {

    }
}