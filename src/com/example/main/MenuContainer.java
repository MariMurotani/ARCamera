package com.example.main;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import android.view.View.*;

public class MenuContainer extends FrameLayout implements OnClickListener{
	
	public MenuContainer.onClickListener mOnClickListner;
	public interface onClickListener{
		public void onClose();
	}
	public MenuContainer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		android.view.ViewGroup.LayoutParams lp1 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,100);
		
		Button bt = new Button(this.getContext());
		bt.setText("ウィンドウを閉じる");
		bt.setOnClickListener(this);
		
		this.addView(bt,lp1);
	}

	/**
	 * ボタンのおんクリック
	 * */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(mOnClickListner != null){
			this.mOnClickListner.onClose();
		}
	}

}
