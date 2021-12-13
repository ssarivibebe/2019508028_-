package com.jh.afinal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PieView extends View {     // 커스텀 차트 뷰

    private double valuePositive = 50;      // 값 1, 받은 요청 건수
    private double valueNegative = 50;      // 값 2, 보낸 요청 건수
    // 값 1, 값 2 를 파이 차트로 그 비율을 보여줌

    // 그리기 페인트
    private final Paint paintPositive = new Paint();
    private final Paint paintNegative = new Paint();
    private final Paint paintTextPositive = new Paint();
    private final Paint paintTextNegative = new Paint();
    private final Paint paintLightText = new Paint();
    private final Paint paintCardPositive = new Paint();
    private final Paint paintCardNegative = new Paint();
    private final Rect drawingRect = new Rect();


    public PieView(Context context) {
        super(context);
        init();
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paintPositive.setColor(0xFF2FDED7);
        paintPositive.setStyle(Paint.Style.STROKE);
        paintPositive.setStrokeWidth(200f);
        paintPositive.setStrokeCap(Paint.Cap.ROUND);

        paintNegative.setColor(0xFFD9D9D9);
        paintNegative.setStyle(Paint.Style.STROKE);
        paintNegative.setStrokeWidth(200f);

        paintTextPositive.setColor(0xFF333333);
        paintTextPositive.setTextSize(60f);
        paintTextPositive.setTextAlign(Paint.Align.CENTER);
        paintTextPositive.setFakeBoldText(true);

        paintTextNegative.setColor(0xFF333333);
        paintTextNegative.setTextSize(50f);
        paintTextNegative.setTextAlign(Paint.Align.CENTER);
        paintTextNegative.setFakeBoldText(true);

        paintLightText.setColor(0xFFEEEEEE);
        paintLightText.setTextSize(40f);
        paintLightText.setTextAlign(Paint.Align.CENTER);
        paintLightText.setFakeBoldText(true);

        paintCardPositive.setColor(0xFF2FB4B5);
        paintCardPositive.setStyle(Paint.Style.STROKE);
        paintCardPositive.setStrokeWidth(50f);
        paintCardPositive.setStrokeCap(Paint.Cap.ROUND);

        paintCardNegative.setColor(0xFF767673);
        paintCardNegative.setStyle(Paint.Style.STROKE);
        paintCardNegative.setStrokeWidth(50f);
        paintCardNegative.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setPositiveValue(double value) {
        valuePositive = value;
        invalidate();
    }

    public void setNegativeValue(double value) {
        valueNegative = value;
        invalidate();
    }

    private double getPositiveRatio() {
        if (valueNegative + valuePositive == 0) {
            return 0;
        }
        return valuePositive / (valueNegative + valuePositive);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        getDrawingRect(drawingRect);

        final float centerX = drawingRect.centerX();
        final float centerY = drawingRect.centerY();
        final float smallestWidth = Math.min(drawingRect.width(), drawingRect.height());
        final float arcRadius = smallestWidth / 2.0f - 150;

        // Negative 호
        canvas.drawArc(
                centerX - arcRadius,
                centerY - arcRadius,
                centerX + arcRadius,
                centerY + arcRadius,
                0, 360, false, paintNegative
        );

        if (valuePositive + valueNegative == 0) {
            return;
        }

        // Positive 호
        final float sweep = (float) (360 * getPositiveRatio());
        if (valuePositive > 0) {
            canvas.drawArc(
                    centerX - arcRadius,
                    centerY - arcRadius,
                    centerX + arcRadius,
                    centerY + arcRadius,
                    -90,
                    sweep,
                    false,
                    paintPositive
            );
        }

        final float midAnglePositive = (-90 + sweep / 2) * (float) (Math.PI / 180);
        final float midAngleNegative = midAnglePositive + (float) Math.PI;
        final float positiveTextX = centerX + arcRadius * (float) Math.cos(midAnglePositive);
        final float positiveTextY = centerY + arcRadius * (float) Math.sin(midAnglePositive);
        final float negativeTextX = centerX + arcRadius * (float) Math.cos(midAngleNegative);
        final float negativeTextY = centerY + arcRadius * (float) Math.sin(midAngleNegative);

        // Positive 텍스트
        if (valuePositive > 0) {
            canvas.drawText(
                    "받은 요청",
                    positiveTextX,
                    positiveTextY,
                    paintTextPositive
            );
        }

        // Negative 텍스트
        if (valueNegative > 0) {
            canvas.drawText(
                    "보낸 요청",
                    negativeTextX,
                    negativeTextY,
                    paintTextNegative
            );
        }

        // Positive 카드

        final float positiveCardY = positiveTextY + paintTextPositive.getTextSize();
        if (valuePositive > 0) {
            canvas.drawLine(
                    positiveTextX - 80,
                    positiveCardY,
                    positiveTextX + 80,
                    positiveCardY,
                    paintCardPositive
            );

            canvas.drawText(Math.round(valuePositive) + "건",
                    positiveTextX,
                    positiveCardY + paintLightText.getTextSize() / 2 - 7,
                    paintLightText
            );
        }

        // Negative 카드

        final float negativeCardY = negativeTextY + paintTextNegative.getTextSize() * 2;
        if (valueNegative > 0) {
            canvas.drawLine(
                    negativeTextX - 80,
                    negativeCardY,
                    negativeTextX + 80,
                    negativeCardY,
                    paintCardNegative
            );

            canvas.drawText(Math.round(valueNegative) + "건",
                    negativeTextX,
                    negativeCardY + paintLightText.getTextSize() / 2 - 7,
                    paintLightText
            );
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (onClickListener != null) {
                onClickListener.onClick(valuePositive, valueNegative);
            }
            return true;
        }
        return false;
    }

    public interface OnClickListener {
        void onClick(double positive, double negative);
    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}











