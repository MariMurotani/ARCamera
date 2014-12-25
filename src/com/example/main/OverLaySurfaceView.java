package com.example.main;

import java.util.Timer;
import java.util.TimerTask;

import com.example.test.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    
    private int iTimerSpan = 4000;	//	タイマー呼び出しタイミング  60fps(Frames Per Second) 300とかにするとスレッドが遅れるのでsyncroniezdでスキップされる
    
    //	文字列描画用のパラメータ
    private int iPosTextLeft = 10;
	private int iPosTextTop = 100;
	private int iPosVSpan = 60;
	private int iTextCounter = 0;
	private int iSize = 22;
	private String[] strText = new String[4];
	private int iByougaCounter = 0;
    
	private int iPhase = 0;
	
	public OverLaySurfaceView(Context context) {
		super(context);
		this.mContext = context;
		
		SurfaceHolder holder = this.getHolder();
		holder.setFormat(PixelFormat.TRANSLUCENT);
		holder.addCallback(this);
		this.setZOrderOnTop(true);
		
		mImage = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
	
		strText[0] = "Let it go, let it go";
		strText[1] = "Turn away and slam the door";
		strText[2] = "I don't care";
		strText[3] = "what they're going to say";
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
	
	//	一部だけ書き換えるロジックを入れたら、描画が前後する AとBの処理実行時間が違うためだと推測
	public synchronized void doDraw() {
		//Canvasの取得(マルチスレッド環境対応のためLock)
	    Canvas canvas = mHolder.lockCanvas();
	    
	    //		A.テキストは4回に一回描画する
	    //if(iByougaCounter % 4 == 0){
	    if(true){
	    	// 	キャンバスをいったん全てクリア
		    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		    //drawDroidOnCanavs(canvas,(int)iWidth - this.mImage.getWidth());
		    drawMojisOnCanvas(canvas);
	    }
	    //		B.右端のドロイドくんは毎回描画する
	    else{
	    	//	    	描画する画像の一部分をバッファーする場合にはその領域だけクリアする
		    Paint paintClear = new Paint();
		    //paintClear.setColor(Color.CYAN);
		    paintClear.setColor(Color.TRANSPARENT);
		    paintClear.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		    
		    //	計算がおかしい気がする float扱えないのか
		    //int left, int top, int right, int bottom
		    canvas.drawRect(iWidth-this.mImage.getWidth(),0,iWidth,this.getHeight(), paintClear);
		    drawDroidOnCanavs(canvas,(int)this.getWidth() - this.mImage.getWidth());
		    
	    }
    	iByougaCounter++;
	    
	    //LockしたCanvasを解放、ほかの描画処理スレッドがあればそちらに。
	    mHolder.unlockCanvasAndPost(canvas);
	    
	}
	   
	/**
	 * droidをキャンバスに描く
	 * @param canvas
	 */
	private void drawDroidOnCanavs(Canvas canvas,int left){
		 //　droidくんを描画
		 Paint paint = new Paint();
		 canvas.drawBitmap(mImage, left, this.iPositionTop, paint);
	}
	
	/**
	 * 文字列をキャンバスに描画する
	 * @param canvas
	 */
	private void drawMojisOnCanvas(Canvas canvas){
		
		Log.v("murotani","drawMojisOnCanvas: " + this.iTextCounter);
		
		// 縁取り色
	    Paint paintFuti = getFutiPaint();
	    // 文字色
	    Paint paintMoji = getMojiPaint();
	    
	    //	縁取り色を先に描画
	    for(int i = 0; i < this.strText.length ; i++){
	    	canvas.drawText(this.strText[i], this.iPosTextLeft, this.iPosTextTop+(iPosVSpan*i), paintFuti);
	    }
	    
	    //	文字を描画
	    for(int i = 0; i < this.strText.length ; i++){
	    	if(i <= this.iTextCounter){
	    		canvas.drawText(this.strText[i], this.iPosTextLeft, this.iPosTextTop+(iPosVSpan*i), paintMoji);
	    	}
	    }
	    
	    //	 行数カウンタをリセット
	    this.iTextCounter++;
	    if(this.strText.length < this.iTextCounter){
	    	this.iTextCounter = 0;
	    }
	    
	}
	
	private Paint getFutiPaint(){
		Paint paint = new Paint();
	    paint.setAntiAlias(true);                    // アンチエイリアス
	    paint.setStrokeWidth(5.0f);                // 描画の幅
	    paint.setColor(Color.WHITE);             // 縁取り色のセット
	    paint.setAlpha(0x77);                        // アルファ値をセット
	    paint.setTextSize(this.iSize);           // テキストサイズ
	    paint.setTextAlign(Align.LEFT);          // 左寄せ
	    paint.setStyle(Paint.Style.STROKE);
	    return paint;
	}
	
	private Paint getMojiPaint(){
		Paint paint = new Paint();
	    paint.setAntiAlias(true);
	    paint.setStrokeWidth(0);
	    paint.setColor(Color.BLACK);
	    paint.setTextSize(this.iSize);
	    paint.setTextAlign(Align.LEFT);
	    paint.setStyle(Paint.Style.FILL);
	    return paint;

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
			try{
				doDraw();
			}catch(Exception e){
				Log.v("nmurotani","call doDraw: " + e.getMessage());
			}catch(Error e){
				Log.v("nmurotani","call doDraw: " + e.getMessage());
			}
		}
	}
	
	/**
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ TimerTask ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 
	 * */
}