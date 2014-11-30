package com.example.arcamera;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.SurfaceHolder;
import android.opengl.GLSurfaceView.Renderer;

public class DisplayLayer extends GLSurfaceView implements Renderer{
private Bitmap mImage;
private String mediaPath = "file:///android_asset/images.jpeg";

	public DisplayLayer(Context context) {
		super(context);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		Log.v("murotani","onSurfaceCreated: " + gl.toString());
		
		FileInputStream in;
		try {
			in = new FileInputStream(mediaPath);
	
			Bitmap bmp = BitmapFactory.decodeStream(in);
			in.close();

			gl.glClear(DRAWING_CACHE_QUALITY_AUTO);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.v("murotani",e.getMessage());
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		Log.v("murotani","onSurfaceChanged: " + gl.toString());
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		Log.v("murotani","onDrawFrame: " + gl.toString());
	}

	
	

}
