package com.example.main;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.util.Log;
import android.view.View;
import android.view.View.*;

public class MenuContainer extends LinearLayout {
	Button bt;
	Button bt2;
	Button bt3;
	
	public MenuContainer.onClickListener mOnClickListner;
	
	public interface onClickListener{
		public void onClose();
		public void onGotoGLActivity();
		public void onGotoMainActivity();
	}
	
	public MenuContainer(Context context) {
		super(context);
		
		LinearLayout ll = new LinearLayout(context);
		ll.setOrientation(LinearLayout.VERTICAL);
		
		// TODO Auto-generated constructor stub
		android.view.ViewGroup.LayoutParams lp1 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
	
		bt = new Button(this.getContext());
		bt.setText("強制終了");
		bt.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	if(mOnClickListner != null){
		    		mOnClickListner.onClose();
		    	}
		    }
		});
		ll.addView(bt,lp1);
		
		bt2 = new Button(this.getContext());
		bt2.setText("GLレンダリングのテストページ");
		bt2.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	if(mOnClickListner != null){
		    		mOnClickListner.onGotoGLActivity();
		    	}
		    }
		});
		ll.addView(bt2,lp1);
		
		bt3 = new Button(this.getContext());
		bt3.setText("ウィンドウを閉じる");
		bt3.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	if(mOnClickListner != null){
		    		mOnClickListner.onGotoMainActivity();
		    	}
		    }
		});
		ll.addView(bt3,lp1);
		
		
		this.addView(ll);
	}

}
