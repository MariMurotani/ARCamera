package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	private int iWidth;
	private int iHeight;
	private Context mContext;
	
	public MySurfaceView(Context context) {
		super(context);
		this.mContext = mContext;
		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
	}
	
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {			//描画処理
			
		Canvas c = holder.lockCanvas();
		
		Paint p = new Paint();
		p.setColor(Color.RED);
		
		c.drawRect(0, 0, this.iWidth ,this.iHeight ,p);
		
		holder.unlockCanvasAndPost(c);
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
