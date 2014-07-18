package com.thuongnh.ntpclock.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zelic on 7/18/14.
 * This class is used to render a clock view. Can be extended to have more helpful properties (background, colors, ...)
 */
public class ClockView extends View {

    private static final String TAG = ClockView.class.getSimpleName();

    //Paints objects for the components
    Paint circlePaint;
    Paint secondPaint;
    Paint minutePaint;
    Paint hourPaint;

    //Time components
    int hour;
    int minute;
    int second;

    public ClockView(Context context) {
        super(context);
        initView();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    protected void initView(){
        //Paint for the outside circle
        circlePaint = getCommonPaint();
        circlePaint.setStrokeWidth(10);
        //Paint for second hand
        secondPaint = getCommonPaint();
        secondPaint.setStrokeWidth(2);
        //Paint for minute hand
        minutePaint = getCommonPaint();
        minutePaint.setStrokeWidth(5);
        //Paint for hour hand
        hourPaint = getCommonPaint();
        hourPaint.setStrokeWidth(10);
    }

    /**
     * Helper method for initiating paint object
     * @return
     */
    protected Paint getCommonPaint(){
        Paint ret = new Paint();
        ret.setStyle(Paint.Style.STROKE);
        ret.setColor(Color.WHITE);
        ret.setDither(true);
        ret.setAntiAlias(true);
        ret.setStrokeWidth(5);
        return ret;
    }

    /**
     * Set time components and update clock view
     * @param hour
     * @param minute
     * @param second
     */
    public void setTime(int hour, int minute, int second) {
        this.second = second;
        this.minute = minute;
        this.hour = hour;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int radius = width/2;
        if (radius > (height/2)) {
            radius = height/2;
        }

        //Length for the hands
        int secondRadius = (int)(radius*0.8);
        int minuteRadius = (int)(radius*0.8);
        int hourRadius  = (int)(radius*0.6);

        //Calculate radian for the angles of each hand
        double secondRadian = Math.toRadians(second * 360 / 60 - 90);
        double minuteRadian = Math.toRadians(minute*360/60-90);
        double hourRadian = Math.toRadians(hour*360/12 + minute/60 -90);

        //Calculate the end point of each hand
        int xSecond = (int)(secondRadius*Math.cos(secondRadian)+radius);
        int ySecond = (int)(secondRadius*Math.sin(secondRadian)+radius);
        int xMinute = (int)(minuteRadius*Math.cos(minuteRadian)+radius);
        int yMinute = (int)(minuteRadius*Math.sin(minuteRadian)+radius);
        int xHour = (int)(hourRadius*Math.cos(hourRadian)+radius);
        int yHour = (int)(hourRadius*Math.sin(hourRadian)+radius);

        //Draw the clock
        canvas.drawCircle(radius, radius, radius-20, circlePaint);
        canvas.drawLine(radius, radius, xSecond, ySecond, secondPaint);
        canvas.drawLine(radius, radius, xMinute, yMinute, minutePaint);
        canvas.drawLine(radius, radius, xHour, yHour, hourPaint);
    }

}
