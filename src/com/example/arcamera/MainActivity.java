package com.example.arcamera;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.os.Build;


/***
 * http://seesaawiki.jp/w/moonlight_aska/d/%A5%AB%A5%E1%A5%E9%A4%F2%C0%A9%B8%E6%A4%B9%A4%EB
 * */
public class MainActivity extends Activity {
	Context mContext;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //	このクラスのviewをカメラに設定
        setContentView(new CameraPreview(this));
        
        
        
        //mContext = getApplicationContext();
        //setContentView(R.layout.activity_main);
        
        /*mGLSurfaceView = (GLSurfaceView)findViewById(R.id.MyGLSurfaceView);
        mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        mGLSurfaceView.getHolder().setFormat( PixelFormat.TRANSLUCENT );
        mGLSurfaceView.setRenderer(new CubeRenderer(true));
        
        mMySurfaceViewCallback = new MySurfaceViewCallback(mContext);
        mSurfaceView = (SurfaceView)findViewById(R.id.MySurfaceView);
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(mMySurfaceViewCallback);
        */
	}
}