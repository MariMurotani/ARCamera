package com.example.main;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.example.test.R;

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

public class OverLaySurfaceView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
	private int iWidth;
	private int iHeight;
    
	private int iPositionTop = 0;   //表示位置(TOP:Y座標)
	private int iPositionLeft = 0;  //表示位置(LEFT:X座標)
	
	private long lTime = 0;
	private long lLapTime = 0;
	
	private Context mContext;
	
	//スレッドの生成
    private SurfaceHolder mHolder;
    private Thread mLooper;
    private Timer mTimer;
    
    //本来リソース管理するもの
    private Bitmap mImage;
    
    private int iTimerSpan = 100;	//	タイマー呼び出しタイミング  60fps(Frames Per Second)
    
	public OverLaySurfaceView(Context context) {
		super(context);
		this.mContext = context;
		
		SurfaceHolder holder = this.getHolder();
		holder.setFormat(PixelFormat.TRANSLUCENT);
		holder.addCallback(this);
		this.setZOrderOnTop(true);
		
		mImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
	}
	
	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ SurfaceHolder.Callback ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 
	 * */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
	    //スレッド処理を開始
	    if(mLooper != null ){
	    	lTime = System.currentTimeMillis();
	        mLooper.start();
	    }
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {			//描画処理
		this.mHolder = holder;
		this.mLooper = new Thread(this);
		
		this.mTimer = new Timer();
        this.mTimer.scheduleAtFixedRate(new OverLaySurfaceView.myTimerTask(),0,iTimerSpan);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mLooper = null;
	}
	
	public void doDraw(){
	    //Canvasの取得(マルチスレッド環境対応のためLock)
	    Canvas canvas = mHolder.lockCanvas();
	 
	    Paint paint = new Paint();
	    
	    //描画処理(Lock中なのでなるべく早く)
	    canvas.drawBitmap(mImage, this.iPositionLeft, this.iPositionTop, paint);
	 
	    //LockしたCanvasを解放、ほかの描画処理スレッドがあればそちらに。
	    mHolder.unlockCanvasAndPost(canvas);
	}
	
	/**
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ SurfaceHolder.Callback ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 
	 * */
	
	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ SurfaceView ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 
	 * */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.iWidth = w;
		this.iHeight = h;
	}
	/**
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ SurfaceView ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 
	 * */
	
	
	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Runnable ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 
	 * */
	@Override
	public void run() {
		while (mLooper != null) {
			
			//描画処理
			//doDraw();
			
			//位置更新処理
			//処理落ちによるスローモーションをさけるため現在時刻を取得
			long delta = System.currentTimeMillis() - lTime;
			this.lTime = System.currentTimeMillis();
			
			//画面の縦移動が終わるまでの時間計測(一定であることが期待値)
			this.lLapTime = this.lTime;
			//Log.v("murotani","iLapTime: " + this.lLapTime);
		}
	}
	
	/**
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ Runnable ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 
	 * */
	
	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ TimerTask ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 
	 * */
	private class myTimerTask extends TimerTask{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (mTimer == null) {
				return;
			}
			
			iPositionTop += 10;
			iPositionLeft = 0;
			if(iHeight < iPositionTop){
				iPositionTop = 0;
			}
			doDraw();
		}
	}
	/**
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ TimerTask ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 
	 * */
}