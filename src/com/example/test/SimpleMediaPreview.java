package com.example.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SimpleMediaPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private MediaPlayer mMediaPlayer;
	//private String mediaPath = "http://115.30.5.212/frozen.mp4";
    //private String mediaPath = "http://beanflip.tjgt.jp/dev/adtex/movie1.mp4";
    private String mediaPath = "http://115.30.5.212/neko.jpg";
	public SimpleMediaPreview(Context context) {
		super(context);
	}
	
	public SimpleMediaPreview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private void initialize(){
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	public void setPath(String path){
		this.mediaPath = path;
		initialize();
	}

	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Pattern p = Pattern.compile(".jpg|.png.|.gif");
        Matcher m = p.matcher(mediaPath);
        
        //	HTMLの場合はINVISIBLEなwebviewをフロントに置いて展開後にキャプチャできるかな？？？？
       //	静止画の場合
       if(m.find()){
      
    	   try{
	    	   //	データを取得
	    	   FileInputStream in = new FileInputStream(mediaPath);
	    	   
	    	   Bitmap bmp = BitmapFactory.decodeStream(in);
	    	   in.close();
	
	    	   //描画処理
	    	   Canvas c = holder.lockCanvas();
	    	   c.drawBitmap(bmp, 0, 0,null);
	    	   holder.unlockCanvasAndPost(c);
	    	   
    	   } catch (Exception e) {
    		   Log.v("murotani",e.getMessage());
    	   }catch(Error e){
    		   Log.v("murotani",e.getMessage());
    	   }
   
       //	動画の場合
       }else{
			mMediaPlayer = new MediaPlayer();
			try {
				mMediaPlayer.setDataSource(mediaPath);
				mMediaPlayer.setDisplay(mHolder);
				mMediaPlayer.prepare();	
				mMediaPlayer.start();
				
			} catch (Exception e) {
				Log.v("murotani",e.getMessage());
			}catch(Error e){
				Log.v("murotani",e.getMessage());
			}
       }
	}
       
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
		if(mMediaPlayer != null){
			mMediaPlayer.stop();
			mMediaPlayer = null;
		}
	}
	
	

	/*
	OnBufferingUpdateListener, OnCompletionListener,OnPreparedListener, OnVideoSizeChangedListener,
	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		Log.v("murotani","onVideoSizeChanged");
		
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.v("murotani","onPrepared");
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		Log.v("murotani","onCompletion");
	}

	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		Log.v("murotani","onBufferingUpdate");
		
	}*/
}