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

public class FiveDayTimeChart extends BaseMainChart implements OnGesturesListener{

    private int TimeColor, GridColor, DottedColor, IndicatorColor, AvePriceColor, RealPriceColor, PrePriceColor, UpColor, DownColor;
    private Paint TimePaint, GridPaint, DottedPaint, IndicatorPaint, AvePricePaint, RealPricePaint, PrePricePaint, UpPaint, DownPaint;
    private float TimeSize, UpTextSize, DownTextSize;
    private int ItemSize;
    private final String ZeroGain = "0.00%";
    private DecimalFormat format;
    private StockTimeChartAdapter adapter;

    public StockTimeChartAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(StockTimeChartAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setChart(this);

    }

    public FiveDayTimeChart(Context context) {
        super(context);
    }

    public FiveDayTimeChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FiveDayTimeChart);
        TimeColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartTimeColor, ContextCompat.getColor(getContext(), R.color.TimeColor));
        GridColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartGridLineColor, ContextCompat.getColor(getContext(), R.color.GridColor));
        DottedColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartDottedLineColor, ContextCompat.getColor(getContext(), R.color.DottedColor));
        IndicatorColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartIndicatorColor, ContextCompat.getColor(getContext(), R.color.IndicatorColor));
        AvePriceColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartAvePriceColor, ContextCompat.getColor(getContext(), R.color.AvePriceColor));
        RealPriceColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartRealPriceColor, ContextCompat.getColor(getContext(), R.color.RealPriceColor));
        PrePriceColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartPrePriceColor, ContextCompat.getColor(getContext(), R.color.PrePriceColor));
        UpColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartUpColor, ContextCompat.getColor(getContext(), R.color.UpColor));
        DownColor = typedArray.getColor(R.styleable.FiveDayTimeChart_FiveChartDownColor, ContextCompat.getColor(getContext(), R.color.DownColor));
        TimeSize = sp2px(getContext(), (int) (typedArray.getDimension(R.styleable.FiveDayTimeChart_FiveChartTimeSize, 10)));
        UpTextSize = sp2px(getContext(), (int) typedArray.getDimension(R.styleable.FiveDayTimeChart_FiveChartUpTextSize, 10));
        DownTextSize = sp2px(getContext(), (int) typedArray.getDimension(R.styleable.FiveDayTimeChart_FiveChartDownTextSize, 10));
        ItemSize = typedArray.getInt(R.styleable.FiveDayTimeChart_FiveChartItemSize, 241);
        InitPaint();
        format = new DecimalFormat("#0.00");
        setGesturesListener(this);
        typedArray.recycle();
    }

    public FiveDayTimeChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FiveDayTimeChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

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
        float LeftOnAxis = 0;
        float TopOnAxis = 0;
        float RightOnAxis = LeftOnAxis+getMeasuredWidth();
        float BtmOnAxis = TopOnAxis+getMeasuredHeight();
        float TimeAreaHeight = TimeSize+dp2px(getContext(),2); //????????????????????????
        float BeforeTimeOnAxis = BtmOnAxis-TimeAreaHeight; //??????????????????
        float OneFifthWidth = getMeasuredWidth()/5f; //??????????????????
        float BeforeTimeOfHalf = BeforeTimeOnAxis / 2f; //???????????????
        DrawGrid(canvas, LeftOnAxis, TopOnAxis, RightOnAxis, BeforeTimeOnAxis, OneFifthWidth, BeforeTimeOfHalf); //????????????
        float ZeroGainOnYAxis = BeforeTimeOfHalf + TimePaint.measureText(ZeroGain.substring(0, 1))/2f; //0??????Y???
        TimePaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(ZeroGain, RightOnAxis, ZeroGainOnYAxis, TimePaint); //0.00%
        float DayOfAveWidth = OneFifthWidth / ItemSize; //???????????????????????????



    }

    /*
    * ????????????
    * */
    private void DrawGrid(Canvas canvas, float left, float top, float right, float btm, float oneFifthWidth, float halfHeight){
        GridPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(left, top, right, btm, GridPaint); //????????????
        GridPaint.setStyle(Paint.Style.FILL);
        float TwoFifthWidth = left+oneFifthWidth*2f; //??????????????????
        float ThreeFifthWidth = oneFifthWidth*3f; //??????????????????
        float FourFifthWidth = oneFifthWidth*4f; //??????????????????
        canvas.drawLine(left, top, left, btm, GridPaint); //?????????
        canvas.drawLine(oneFifthWidth, top, oneFifthWidth, btm, GridPaint); //?????????
        canvas.drawLine(TwoFifthWidth, top, TwoFifthWidth, btm, GridPaint); //?????????
        canvas.drawLine(ThreeFifthWidth, top, ThreeFifthWidth, btm, GridPaint); //?????????
        canvas.drawLine(FourFifthWidth, top, FourFifthWidth, btm, GridPaint); //?????????
        canvas.drawLine(right, top, right, btm, GridPaint); //?????????
        float beforeTimeOfQuarter = halfHeight/2f; //??????????????????
        float threeInFourOfHeight = btm - beforeTimeOfQuarter; //????????????????????????
        canvas.drawLine(left, top, right, top, GridPaint); //?????????
        canvas.drawLine(left, beforeTimeOfQuarter, right, beforeTimeOfQuarter, GridPaint); //?????????
        canvas.drawLine(left, halfHeight, right, halfHeight, DottedPaint); //?????????
        canvas.drawLine(left, threeInFourOfHeight, right, threeInFourOfHeight, GridPaint); //?????????
        canvas.drawLine(left, btm, right, btm, GridPaint); //?????????
    }

    @Override
    public int getItemSize() {
        return ItemSize;
    }

    @Override
    public void setItemSize(int itemSize) {
        this.ItemSize = itemSize;
    }

    @Override
    public int getDataSize() {
        return 0;
    }

    @Override
    public void OnLongPressMove(float moveX, float moveY) {

    }

    @Override
    public void OnLongPressAfterUp(float upX, float upY) {

    }

    @Override
    public void CancelLongPressBySingleClick() {

    }

    @Override
    public void OnSingleMove(float moveX, float moveY, float distanceX, float distanceY) {

    }

    @Override
    public void OnZoom(float aX, float aY, float bX, float bY) {

    }
}
