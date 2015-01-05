package com.example.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import com.example.test.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GLActivity extends Activity{
	private Context mContext;
    private SurfaceHolder holder;
    LinearLayout ll;
    RelativeLayout rl;
    FrameLayout fl;
    android.view.ViewGroup.LayoutParams lp2;
    private SimpleMediaPreview mp1;
    private CameraPreview cp1;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this.getApplicationContext();
		
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		Display dp = wm.getDefaultDisplay();
		
		setContentView(R.layout.activity_main3);
		android.view.ViewGroup.LayoutParams lp1 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,dp.getHeight()/3);
		android.view.ViewGroup.LayoutParams lp2 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,dp.getHeight()/3*2);
		android.view.ViewGroup.LayoutParams lp3 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		android.view.ViewGroup.LayoutParams lp4 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		
		fl = (FrameLayout)findViewById(R.id.layout3);
			
		try{
				this.ll = new LinearLayout(mContext);
				this.ll.setOrientation(LinearLayout.VERTICAL);
				
		 	    //this.rl = new RalitiveLayout(mContext);
				
				//ll.addView(this.ll ,lp3);
				
				//	オーバーレイのプレビュー
				OverLaySurfaceViewGL over = new OverLaySurfaceViewGL(mContext);
				//fl.addView(over,lp2);
				ll.addView(over,lp2);
				
				MenuContainer menupre = new MenuContainer(mContext);
				menupre.mOnClickListner = new MenuContainer.onClickListener() {
					@Override
					public void onClose() {
						// TODO Auto-generated method stub
						System.exit(RESULT_OK);
					}
					@Override
					public void onGotoGLActivity() {
						// TODO Auto-generated method stub
						Intent i = new Intent(getApplicationContext(),GLActivity.class);
						startActivity(i);
					}
					@Override
					public void onGotoMainActivity() {
						// TODO Auto-generated method stub
						finish();
					}
				};
				ll.addView(menupre,lp4);
				this.addContentView(ll,lp3);
		}catch(Exception e){
			Log.v("murotani","exception: "+ e.getMessage());
		}catch(Error e){
			Log.v("murotani","error: " + e.getMessage());
		}
		/*ll.addView(child,lp3)
		
		setContentView(R.layout.activity_main2);

		Context mContext = getApplicationContext();

		// 背景になるSurfaceView
		MySurfaceView bkSurfaceview = (MySurfaceView) findViewById(R.id.BackSurfaceView);
		SurfaceHolder holder = bkSurfaceview.getHolder();

		// オーバーレイするSurfaceView
		SurfaceView OverLaySurfaceView = (SurfaceView) findViewById(R.id.OverLaySurfaceView);

		OverLaySurfaceViewCallback OverLaySurfaceViewCallback = new OverLaySurfaceViewCallback(mContext);

		SurfaceHolder overLayHolder = OverLaySurfaceView.getHolder();
		// ここで半透明にする
		overLayHolder.setFormat(PixelFormat.TRANSLUCENT);
		overLayHolder.addCallback(OverLaySurfaceViewCallback);
		*/
	}
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode != KeyEvent.KEYCODE_BACK){
			return super.onKeyDown(keyCode, event);
		}else{
			try{
				this.finishActivity(1);
				super.onDestroy();
			}catch(Exception e){
				Log.v("murotani",e.getMessage());
			}
			return false;
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
