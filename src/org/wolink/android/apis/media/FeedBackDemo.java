package org.wolink.android.apis.media;

import org.wolink.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class FeedBackDemo extends Activity implements 
	OnClickListener, OnLongClickListener{
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.feedbackdemo);
        findViewById(R.id.btn_default).setOnClickListener(this);
        findViewById(R.id.btn_default).setOnLongClickListener(this);
        findViewById(R.id.btn_disable).setOnClickListener(this);
        findViewById(R.id.btn_disable).setOnLongClickListener(this);
        findViewById(R.id.btn_custom).setOnClickListener(this);
        findViewById(R.id.btn_custom).setOnLongClickListener(this);
    }

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_default:
		case R.id.btn_disable:
			//nothing
			break;
		case R.id.btn_custom:
			v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY, 
					HapticFeedbackConstants.FLAG_IGNORE_VIEW_SETTING | HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING);
			break;
		}
	}

	public boolean onLongClick(View v) {
		switch(v.getId()) {
		case R.id.btn_default:
		case R.id.btn_disable:
			//nothing
			return true;
		case R.id.btn_custom:
			v.playSoundEffect(SoundEffectConstants.CLICK);
			return true;
		}
		return false;
	}

}
