package com.example.main;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

import com.example.test.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.opengl.EGLConfig;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;

public class OverLaySurfaceViewGL extends GLSurfaceView implements Renderer{
	private int iWidth = -1;
	private int iHeight = -1;
    
	private int iPositionTop = 0;   //表示位置(TOP:Y座標)
	private int iPositionLeft = 100;  //表示位置(LEFT:X座標)
	
	private Context mContext;
	


	//2次元スプライト
    SampleSprite droid_img = new SampleSprite();
 
	public OverLaySurfaceViewGL(Context context) {
		super(context);
		this.mContext = context;
		
		try{
			this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
			//this.setEGLContextClientVersion(2);
			this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
			this.setRenderer(this);
		}catch(Exception e){
			Log.v(Const.logTag,e.getMessage());
			
		}catch(Error e){
			Log.v(Const.logTag,e.getMessage());
		}
	}

	/**
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ Renderer ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 
	 * */
    public void onSurfaceCreated(GL10 gl,javax.microedition.khronos.egl.EGLConfig config){
    	try{
	        //背景色をクリア
	        gl.glClearColor(1.0f, 0.0f, 0.0f, 0.8f);
	        //gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	        
	        //ディザを無効化
	        gl.glDisable(GL10.GL_DITHER);
	        //深度テストを有効化
	        gl.glEnable(GL10.GL_DEPTH_TEST);
	        //テクスチャ機能ON
	        gl.glEnable(GL10.GL_TEXTURE_2D);
	        //透明可能に
	        gl.glEnable(GL10.GL_ALPHA_TEST);
	        //ブレンド可能に
	        gl.glEnable(GL10.GL_BLEND);
	        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	        
	        droid_img.setTexture(gl,this.mContext.getResources(),R.drawable.ic_launcher);
	        
    	}catch(Exception e){
    		Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
    	}catch(Error e){
    		Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
    	}
    }

    public void onDrawFrame(GL10 gl) {
    	// 描画用バッファをクリア
    	try{
	        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	     	iPositionTop=100;
		    //droid_img.pos_x = (float)0;
	        //droid_img.pos_y = (float)0;
	        //droid_img.texX = 0;
	        //droid_img.texY = 0;
	        droid_img.draw(gl);
	        
		}catch(Exception e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}catch(Error e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}
	}
   

    public void onSurfaceChanged(GL10 gl, int width, int height) {
    	try{
			/*gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);//プロジェクションモードに設定
			GLU.gluOrtho2D(gl, 0.0f, width, 0.0f, height);//平行投影用のパラメータをセット
			*/
    		gl.glViewport(0, 0, this.iWidth, this.iHeight);
			gl.glMatrixMode(GL10.GL_PROJECTION);//プロジェクションモードに設定
			GLU.gluOrtho2D(gl, 0.0f, this.iWidth, 0.0f, this.iHeight);//平行投影用のパラメータをセット
		}catch(Exception e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}catch(Error e){
			Log.v(Const.logTag,"onSurfaceCreated" + e.getMessage());
		}
    }
    
    
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		this.iWidth = w;
		this.iHeight = h;
	}
}