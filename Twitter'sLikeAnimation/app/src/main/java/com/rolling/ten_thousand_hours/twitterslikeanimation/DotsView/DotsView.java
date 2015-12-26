package com.rolling.ten_thousand_hours.twitterslikeanimation.DotsView;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Property;
import android.view.View;

import com.rolling.ten_thousand_hours.twitterslikeanimation.Utils.Utils;

/**
 * DotsView
 * Created by 10000_hours on 2015/12/26.
 */
public class DotsView extends View{

    private static final int DOTS_COUNT = 7;
    private static final int OUTER_DOTS_POSITION_ANGLE = 51;

    private static final int COLOR_1 = 0xffffc107;
    private static final int COLOR_2 = 0xffff9800;
    private static final int COLOR_3 = 0xffff5722;
    private static final int COLOR_4 = 0xfff44336;

    private final  Paint[] dotPaints = new Paint[4];

    private int centerX;
    private int centerY;

    private float maxOuterDotsRadius;
    private float maxInnerDotsRadius;

    private float maxDotSize;

    private float currentProgress = 0;

    private float currentRadius1 = 0;
    private float currentDotSize1 = 0;

    private float currentRadius2 = 0;
    private float currentDotSize2 = 0;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    public DotsView(Context context) {
        super(context);
        init();
    }

    private void init() {
        for (int i = 0; i < dotPaints.length; i++) {
            dotPaints[i] = new Paint();
            dotPaints[i].setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxDotSize = 20;
        maxOuterDotsRadius = w / 2 - maxDotSize * 2;
        maxInnerDotsRadius = 0.8f * maxOuterDotsRadius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawInnerDotsFrame(canvas);
        drawOuterDotsFrame(canvas);
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i <DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius2 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + currentRadius2 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize2, dotPaints[i % dotPaints.length]);
        }
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i <DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + currentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize1, dotPaints[i % dotPaints.length]);
        }
    }

    public void setCurrentProgress (float currentProgress) {
        this.currentProgress = currentProgress;

        updateInnerDotsPosition();
        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();

        postInvalidate();
    }

    public float getCurrentProgress () {
        return currentProgress;
    }

    private void updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentDotSize1 = (float) Utils.mapValueFromRangeToRange(
                    currentProgress, 0, 0.3f, 0, maxOuterDotsRadius * 0.8f);
        }  else {
            this.currentRadius1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.3f, 1f, 0.8f * maxOuterDotsRadius, maxOuterDotsRadius);
        }

        if (currentProgress < 0.7) {
            this.currentDotSize1 = maxDotSize;
        } else {
            this.currentDotSize1 = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.7f, 1f, maxDotSize, 0);
        }
    }

    private void updateInnerDotsPosition() {

        if (currentProgress < 0.3f) {
            this.currentRadius2 = (float) Utils.mapValueFromRangeToRange(
                    currentProgress, 0, 0.3f, 0, maxInnerDotsRadius);
        } else {
            this.currentRadius2 = maxInnerDotsRadius;
        }

        if (currentProgress < 0.2) {
            this.currentDotSize2 = maxDotSize;
        } else if (currentProgress < 0.5) {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange(
                    currentProgress, 0.2f, 0.5f, maxDotSize, 0.3 * maxDotSize);
        } else {
            this.currentDotSize2 = (float) Utils.mapValueFromRangeToRange(
                    currentProgress, 0.5f, 1f, maxDotSize * 0.3f, 0);}
    }

    private void updateDotsPaints () {
        if (currentProgress < 0.5f) {
            float progress = (float) Utils.mapValueFromRangeToRange(
                    currentProgress, 0, 0.5f, 0, 1f);
            dotPaints[0].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_1,COLOR_2));
            dotPaints[1].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_2, COLOR_3));
            dotPaints[2].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_3, COLOR_4));
            dotPaints[3].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_4, COLOR_1));
        } else {
            float progress = (float) Utils.mapValueFromRangeToRange(
                    currentProgress, 0.5f, 1f, 0, 1f);
            dotPaints[1].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_2, COLOR_3));
            dotPaints[2].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_3, COLOR_4));
            dotPaints[3].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_4, COLOR_1));
            dotPaints[4].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_1,COLOR_2));
        }
    }

    private void updateDotsAlpha () {
        float progress = (float) Utils.clamp(currentProgress, 0.6f, 1f);
        int alpha = (int) Utils.mapValueFromRangeToRange(progress, 0.6f, 1f, 255, 0);
        dotPaints[0].setAlpha(alpha);
        dotPaints[1].setAlpha(alpha);
        dotPaints[2].setAlpha(alpha);
        dotPaints[3].setAlpha(alpha);
    }

    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };
}
