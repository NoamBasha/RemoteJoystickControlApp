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

interface JoystickListener {
    void onChange(double x, double y) throws InterruptedException;
}

public class Joystick extends View {

    private final Paint paint = new Paint();
    private float outerCircleX;
    private float outerCircleY;
    private float outerCircleR;
    private float innerCircleX;
    private float innerCircleY;
    private float innerCircleR;
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
        this.outerCircleX = this.innerCircleX = (float)getWidth() / 2;
        this.outerCircleY = this.innerCircleY = (float)getHeight() / 2;

        // Outer Circle
        paint.setColor(Color.BLACK);
        this.outerCircleR = (float)getWidth() / 4;
        canvas.drawCircle(this.outerCircleX, this.outerCircleY, this.outerCircleR, paint);

        // Inner Circle
        paint.setColor(Color.GRAY);
        this.innerCircleR = (float)getWidth() / 8;
        canvas.drawCircle(this.innerCircleX, this.innerCircleY, this.innerCircleR, paint);
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
                this.upMove(motionEvent);
                return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    // When the joystick is touched
    private void touchMove(MotionEvent motionEvent) {

        float userX = motionEvent.getX();
        float userY = motionEvent.getY();

        // If the joystick went too far, do not move the joystick
        if (distFromOuterCircleCenter(userX, userY) <= this.outerCircleR ) {
            this.innerCircleX = userX;
            this.innerCircleY = userY;
            try {
                joystickListener.onChange(userX, userY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        invalidate();
    }

    // Measures distance from x, y to the center of the outer circle
    private float distFromOuterCircleCenter(float x, float y) {
        float xDiff = x - this.outerCircleX;
        float xDiffSquaredDouble = xDiff * xDiff;
        float yDiff = y - this.outerCircleY;
        float yDiffSquaredDouble = yDiff * yDiff;
        return (float)Math.sqrt(xDiffSquaredDouble + yDiffSquaredDouble);
    }

    // When the user stops touching the joystick
    private void upMove(MotionEvent motionEvent) {
        // Resetting starting point
        this.innerCircleX = this.outerCircleX;
        this.innerCircleY = this.outerCircleY;
        try {
            joystickListener.onChange(this.innerCircleX, this.innerCircleY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();
    }
}
