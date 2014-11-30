package com.example.arcamera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class MySurfaceViewCallback implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private Context mContext;

    MySurfaceViewCallback(Context context){
        mContext = context;
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
        Canvas mCanvas = holder.lockCanvas();
        float right = (float)mCanvas.getWidth();
        float bottom = (float)mCanvas.getHeight();
        RectF mRectF = new RectF(0.0F,0.0F,right,bottom);
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        mCanvas.drawBitmap(image, null, mRectF, null);
        holder.unlockCanvasAndPost(mCanvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        this.holder = holder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        holder = null;
    }
}