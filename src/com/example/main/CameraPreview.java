package com.example.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

//public class mCameraPreview extends SurfaceView implements SurfacemHolder.Callback {
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback ,Camera.PreviewCallback{
	private Context mContext;
    private SurfaceHolder mHolder;
    protected Camera mCamera;
 
    private int degrees;
	private byte[] mFrameBuffer;
	
	int PREVIEW_HEIGHT;
	int PREVIEW_WIDTH;
	
    CameraPreview(Context context,int degrees) {
        super(context);
        this.mContext = context;
        this.degrees = degrees;
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        this.mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.mHolder.setFormat( PixelFormat.TRANSLUCENT );
        
        PREVIEW_WIDTH = 480;
        PREVIEW_HEIGHT = 800;
        
        Log.v("muorotani",String.valueOf(PREVIEW_WIDTH));
        Log.v("muorotani",String.valueOf(PREVIEW_HEIGHT));
        
    }
 
    @Override
    public void surfaceCreated(SurfaceHolder mHolder) {
        // カメラの初期化  
        try {  
            
        	mCamera = mCamera.open();
        	mCamera.setDisplayOrientation(this.degrees);
            
        	Log.v("murotani","camera size: " + this.getWidth() + " , " + this.getHeight());
        	this.setCameraParams();
            
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(this);
           
        } catch (Exception e) {  
        	Log.v("murotani",e.getMessage());
        }  
    }
    
    private Parameters setCameraParams(){
    	Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(this.getWidth() ,this.getHeight());
        parameters.setPreviewFormat(ImageFormat.NV21);
        //parameters.setAntibanding(Camera.Parameters.ANTIBANDING_60HZ);
        //parameters.setWhiteBalance(Camera.Parameters.WHITE_BALANCE_WARM_FLUORESCENT);
        parameters.setColorEffect(Camera.Parameters.EFFECT_SEPIA);
        mCamera.setParameters(parameters);
        return parameters;
    }
    @Override
    public void surfaceChanged(SurfaceHolder mHolder, int format, int width, int height) {
    	// カメラのプレビュー開始  
    	try{
    		this.setCameraParams();
    		mCamera.startPreview();
	          
    	}catch(Exception e){
    		Log.v("murotani",e.getMessage());
    	}catch(Error e){
    		Log.v("murotani",e.getMessage());
    	}
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder mHolder) {
        //mCamera.stopPreview();
        //mCamera.release();
    }
    
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		/// 読み込む範囲
	    /*int previewWidth = camera.getParameters().getPreviewSize().width;
	    int previewHeight = camera.getParameters().getPreviewSize().height;
	    // プレビューデータから Bitmap を生成 
	    BitmapFactory.Options bitmapFatoryOptions = new BitmapFactory.Options();
	    bitmapFatoryOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
	    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, bitmapFatoryOptions);
	    
	    if(bmp != null){
	        // SurfaceViewにBitmapをただ描画する
		    try{
			    Canvas canvas = mHolder.lockCanvas();
		        canvas.drawBitmap(bmp, 0, 0, null);
		        Paint paint = new Paint();
		        paint.setColor(Color.RED);
		        paint.setStyle(Style.STROKE);
		        
		        canvas.drawLine(0, 0,100, 100, paint);
		        
		        mHolder.unlockCanvasAndPost(canvas);
		    }catch(Exception e){
		    	Log.v("murotani",e.getMessage());
		    }catch(Error e){
		    	Log.v("murotani",e.getMessage());
		    }
	    }
        //mCamera.addCallbackBuffer(mFrameBuffer);
         */
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}
}