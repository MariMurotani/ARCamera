package com.example.test;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class OverLaySurfaceViewGL extends GLSurfaceView implements SurfaceHolder.Callback{
	private int iWidth;
	private int iHeight;
	private Context mContext;
	
	public OverLaySurfaceViewGL(Context context) {
		super(context);
		this.mContext = context;
		
		SurfaceHolder holder = this.getHolder();
		holder.setFormat(PixelFormat.TRANSLUCENT);
		holder.addCallback(this);
		this.setZOrderOnTop(true);
		//OverLaySurfaceViewCallback OverLaySurfaceViewCallback = new OverLaySurfaceViewCallback(this.getApplicationContext());
		//SurfaceHolder overLayHolder = OverLaySurfaceView.getHolder();
		
		// ここで半透明にする
		//overLayHolder.setFormat(PixelFormat.TRANSLUCENT);
		//overLayHolder.addCallback(OverLaySurfaceViewCallback);
		//OverLaySurfaceView.setZOrderOnTop(true);
		
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		// TODO 自動生成されたメソッド・スタブ
		Canvas mCanvas = holder.lockCanvas();

		Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
		
		Random r = new Random();
		int nw = r.nextInt(this.iWidth);
		int nh = r.nextInt(this.iHeight);
		
		mCanvas.drawBitmap(image, nw, nh, null);

		holder.unlockCanvasAndPost(mCanvas);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {			//描画処理
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.iWidth = w;
		this.iHeight = h;
	}

}
