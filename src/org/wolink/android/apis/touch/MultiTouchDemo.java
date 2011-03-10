package org.wolink.android.apis.touch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MultiTouchDemo extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new PaintView(this));

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setColor(0xFFFF0000);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeJoin(Paint.Join.ROUND);
        mPaint1.setStrokeCap(Paint.Cap.ROUND);
        mPaint1.setStrokeWidth(12);    
        
        mPaint2 = new Paint();
        mPaint2.setAntiAlias(true);
        mPaint2.setDither(true);
        mPaint2.setColor(0xFF0000FF);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setStrokeJoin(Paint.Join.ROUND);
        mPaint2.setStrokeCap(Paint.Cap.ROUND);
        mPaint2.setStrokeWidth(12);       
    }
    
    private Paint       mPaint1, mPaint2;
   
    public class PaintView extends View {
        
        private Bitmap  mBitmap;
        private Canvas  mCanvas;
        private Path    mPath1, mPath2;
        private Paint   mBitmapPaint;
        
        public PaintView(Context c) {
            super(c);
            
            mBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            mPath1 = new Path();
            mPath2 = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        }
        
        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(0xFFAAAAAA);
            
            //canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
            
            //canvas.drawPath(mPath1, mPaint1);
            //canvas.drawPath(mPath2, mPaint2);
            canvas.drawCircle(mX1, mY1, 5, mPaint1);
            canvas.drawCircle(mX2, mY2, 5, mPaint2);
        }
        
        private float mX1, mY1, mX2, mY2;
        private static final float TOUCH_TOLERANCE = 4;
        
        private void touch_start(float x, float y) {
            mPath1.reset();
            mPath1.moveTo(x, y);
            mX1 = x;
            mY1 = y;
        }
        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX1);
            float dy = Math.abs(y - mY1);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath1.quadTo(mX1, mY1, (x + mX1)/2, (y + mY1)/2);
                mX1 = x;
                mY1 = y;
            }
        }
        private void touch_up() {
            mPath1.lineTo(mX1, mY1);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath1, mPaint1);
            // kill this so we don't double draw
            mPath1.reset();
        }

        private void touch2_start(float x, float y) {
            mPath2.reset();
            mPath2.moveTo(x, y);
            mX2 = x;
            mY2 = y;
        }
        private void touch2_move(float x, float y) {
            float dx = Math.abs(x - mX2);
            float dy = Math.abs(y - mY2);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath2.quadTo(mX2, mY2, (x + mX2)/2, (y + mY2)/2);
                mX2 = x;
                mY2 = y;
            }
        }
        private void touch2_up() {
            mPath2.lineTo(mX2, mY2);
            // commit the path to our offscreen
            mCanvas.drawPath(mPath2, mPaint2);
            // kill this so we don't double draw
            mPath2.reset();
        }
        
        @Override
        public boolean onTouchEvent(MotionEvent event) {
        	int pointer = event.getPointerCount();
        	int index = event.getActionIndex();
        	int id = event.getPointerId(index);
        	Log.e("Touch", "pointer = " + pointer + " index = " + index + " id = " + id);
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    Log.e("Touch", "ACTION_DOWN");
                    touch_start(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                	Log.e("Touch", "ACTION_POINTER_DOWN");
                	if (id == 0) {
                		touch_start(event.getX(0), event.getY(0));
                	} else {
                		touch2_start(event.getX(1), event.getY(1));
                	}
                	break;
                case MotionEvent.ACTION_MOVE:
                	Log.e("Touch", "ACTION_MOVE");
                	if (pointer == 2) {
                		Log.e("Touch", "x1 = " + event.getX(0) + " y1 = " + event.getY(0) + " x2 = " 
                				+ event.getX(1) + " y2 = "+ event.getY(1));
                		touch_move(event.getX(0), event.getY(0));
                		touch2_move(event.getX(1), event.getY(1));
                	} else {
                		touch_move(event.getX(), event.getY());
                	}
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                   	if (id == 0) {
                		touch_up();
                	} else {
                		touch2_up();
                	}
                    break;
                default:
                	Log.e("Touch", "UNKNOWN");
                	break;
            }
            this.invalidate();
            return true;
        }
    }	
}
