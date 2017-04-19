package com.itheima.oschina.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.itheima.oschina.R;

@SuppressWarnings("deprecation")
public class QuickOptionDialog extends Dialog implements android.view.View.OnClickListener {

	private ImageView mClose;

	public interface OnQuickOptionformClick {
		void onQuickOptionClick(int id);
	}

	private OnQuickOptionformClick mListener;

	private QuickOptionDialog(Context context, boolean flag, OnCancelListener listener) {
		super(context, flag, listener);
	}

	public QuickOptionDialog(Context context, int defStyle) {
		super(context, defStyle);
		View contentView = getLayoutInflater().inflate(R.layout.dialog_quick_option, null);
		contentView.findViewById(R.id.ly_quick_option_text).setOnClickListener(this);
		contentView.findViewById(R.id.ly_quick_option_album).setOnClickListener(this);
		contentView.findViewById(R.id.ly_quick_option_photo).setOnClickListener(this);
		mClose = (ImageView) contentView.findViewById(R.id.iv_close);

		Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.quick_option_close);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);

		mClose.startAnimation(operatingAnim);

		mClose.setOnClickListener(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		contentView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				QuickOptionDialog.this.dismiss();
				return true;
			}
		});
		super.setContentView(contentView);
	}

	public QuickOptionDialog(Context context) {
		this(context, R.style.quick_option_dialog);
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		getWindow().setGravity(Gravity.BOTTOM);

		WindowManager m = getWindow().getWindowManager();
		Display d = m.getDefaultDisplay();
		WindowManager.LayoutParams p = getWindow().getAttributes();
		p.width = d.getWidth();
		getWindow().setAttributes(p);
	}

	public void setOnQuickOptionformClickListener(OnQuickOptionformClick lis) {
		mListener = lis;
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
		case R.id.iv_close:
			dismiss();
			break;
		default:
			break;
		}
		if (mListener != null) {
			mListener.onQuickOptionClick(id);
		}
		dismiss();
	}

}