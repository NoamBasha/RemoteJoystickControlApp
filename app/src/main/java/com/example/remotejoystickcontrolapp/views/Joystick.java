package com.example.remotejoystickcontrolapp.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Joystick extends View {

    interface JoystickListener {
        void onChange(double x, double y) throws InterruptedException;
    }

    private final Paint paint = new Paint();
    private double outerCircleX;
    private double outerCircleY;
    public double outerCircleR;
    private double userX = 0;
    private double userY = 0;
    public JoystickListener joystickListener;

    public Joystick(Context context) {
        super(context);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Joystick(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setAntiAlias(true);

        // Outer and Inner circle height and width
        double innerCircleX;
        this.outerCircleX = innerCircleX = (float)getWidth() / 2;
        double innerCircleY;
        this.outerCircleY = innerCircleY = (float)getHeight() / 2;

        // Outer Circle
        paint.setColor(Color.BLACK);
        this.outerCircleR = (float)getWidth() / 4;
        canvas.drawCircle((float)this.outerCircleX, (float)this.outerCircleY, (float)this.outerCircleR, paint);

        // Inner Circle
        paint.setColor(Color.GRAY);
        double innerCircleR = (float) getWidth() / 8;
        // Drawing while moving the inner circle
        canvas.drawCircle((float)(innerCircleX + userX), (float)(innerCircleY + userY), (float) innerCircleR, paint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                this.touchMove(motionEvent);
                return true;
            case MotionEvent.ACTION_UP:
                this.upMove();
                return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    // When the joystick is touched
    private void touchMove(MotionEvent motionEvent) {
        // If the joystick went too far, do not move the joystick
        if (distFromOuterCircleCenter(motionEvent.getX(), motionEvent.getY()) <= this.outerCircleR ) {
            this.userX = motionEvent.getX() - getWidth() / 2.0;
            this.userY = motionEvent.getY() - getHeight()  / 2.0;
            try {
                joystickListener.onChange(this.userX, this.userY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        invalidate();
    }

    // Measures distance from x, y to the center of the outer circle
    private double distFromOuterCircleCenter(double x, double y) {
        double xDiff = x - this.outerCircleX;
        double xDiffSquaredDouble = xDiff * xDiff;
        double yDiff = y - this.outerCircleY;
        double yDiffSquaredDouble = yDiff * yDiff;
        return Math.sqrt(xDiffSquaredDouble + yDiffSquaredDouble);
    }

    // When the user stops touching the joystick
    private void upMove() {
        // Resetting joystick to starting point
        this.userX = 0;
        this.userY = 0;
        try {
            joystickListener.onChange(this.userX, this.userY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();
    }
}
