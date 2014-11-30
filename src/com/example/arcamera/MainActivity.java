package com.example.arcamera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.os.Build;


/***
 * http://seesaawiki.jp/w/moonlight_aska/d/%A5%AB%A5%E1%A5%E9%A4%F2%C0%A9%B8%E6%A4%B9%A4%EB
 * */
public class MainActivity extends Activity {
	
	private MediaPreview mPreview;
    private SurfaceHolder holder;
    private DisplayLayer dp1;
    private MediaPreview mp1;
    private MediaPreview mp2;
    private CameraPreview cp1;
    
    View rectview;
    
	Context mContext;
	
	// test
	private SurfaceView mSurfaceView;
    private SurfaceHolder mHolder;
    private MySurfaceViewCallback mMySurfaceViewCallback;
    private GLSurfaceView mGLSurfaceView;
    // test
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(new MediaPreview(this));
		setContentView(R.layout.activity_main);
		
		
        /*mGLSurfaceView = new GLSurfaceView(mContext);
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.getHolder().setFormat( PixelFormat.TRANSLUCENT );
        mGLSurfaceView.setRenderer(new MyRenderer( ));
        */
        /*mMySurfaceViewCallback = new MySurfaceViewCallback(mContext);
        mSurfaceView = new SurfaceView(mContext);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(mMySurfaceViewCallback);
		*/
		
        android.view.ViewGroup.LayoutParams lp1 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,200);
		android.view.ViewGroup.LayoutParams lp2 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		
		LinearLayout root = (LinearLayout)findViewById(R.id.layout);
		FrameLayout layer1 = (FrameLayout)new FrameLayout(mContext);
		root.addView(layer1,new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		
		try{
			String path = "http://115.30.5.212/frozen.mp4";
			mp1 = new MediaPreview(this.getApplicationContext());
			cp1 = new CameraPreview(this.getApplicationContext(),getCamDegree()+90);
			dp1 = new DisplayLayer(this.getApplicationContext());

			mp1.setPath(path);
			
			layer1.addView(mp1,lp1);
			layer1.addView(cp1,lp2);
			//layer1.addView(dp1,lp2);
			
			//root.addView(mLayer,lp2);
			
			mp1.setVisibility(View.VISIBLE);
			cp1.setVisibility(View.VISIBLE);
			//dp1.setZOrderOnTop(true);
			//dp1.setVisibility(View.VISIBLE);
			
		}catch(Exception e){
			Log.v("murotani",e.getMessage());
		}catch(Error e){
			Log.v("murotani",e.getMessage());
		}
		
		
	}
	
	private int getCamDegree(){
		int rotation = getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
		return degrees;
	}
}