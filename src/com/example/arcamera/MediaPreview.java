package com.example.arcamera;

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
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MediaPreview extends GLSurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private MediaPlayer mMediaPlayer;
	//private String mediaPath = "http://115.30.5.212/frozen.mp4";
    //private String mediaPath = "http://beanflip.tjgt.jp/dev/adtex/movie1.mp4";
    //private String mediaPath = "http://115.30.5.212/neko.jpg";
	private String mediaPath = "https://www.youtube.com/watch?v=uDSi9q2phtY";
	
	public MediaPreview(Context context) {
		super(context);
	}
	
	public MediaPreview(Context context, AttributeSet attrs) {
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
		Log.v("murotani","surfaceCreated");
		
		
		Pattern p = Pattern.compile(".jpg|.png.|.gif");
        Matcher m = p.matcher(mediaPath);
        
        if(m.find()){
      
    	   try{
	    	   FileInputStream in = new FileInputStream(mediaPath);
	    	   
	    	   Bitmap bmp = BitmapFactory.decodeStream(in);
	    	   in.close();
	
	    	   Canvas c = holder.lockCanvas();
	    	   c.drawBitmap(bmp, 0, 0,null);
	    	   holder.unlockCanvasAndPost(c);
	    	   
    	   } catch (Exception e) {
    		   Log.v("murotani",e.getMessage());
    	   }catch(Error e){
    		   Log.v("murotani",e.getMessage());
    	   }
   
       }else if(true){
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
		Log.v("murotani","surfaceChanged");
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v("murotani","surfaceDestroyed");
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