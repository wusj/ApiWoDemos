package org.wolink.android.apis.media;

import org.wolink.android.apis.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SoundPoolDemo extends Activity implements OnClickListener{
	Button play1;
	Button play2;
	SoundManager sm;
	
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.soundpooldemo);
        
        play1 = (Button)findViewById(R.id.play1);
        play2 = (Button)findViewById(R.id.play2);

        play1.setOnClickListener(this);
        play2.setOnClickListener(this);
        
        sm = SoundManager.getInstance();
        sm.initSounds(this);
        sm.addSound("1", R.raw.one, 350);
        sm.addSound("2", R.raw.two, 350);
        sm.addSound("3", R.raw.three, 350);
        sm.addSound("4", R.raw.four, 350);
        sm.addSound("5", R.raw.five, 350);
        sm.addSound("6", R.raw.six, 350);
        sm.addSound("7", R.raw.seven, 350);
        sm.addSound("8", R.raw.eight, 350);
        sm.addSound("9", R.raw.nine, 350);
        sm.addSound("0", R.raw.zero, 350);
        sm.addSound("AC", R.raw.ac, 350);
    }

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.play1:
			sm.playSound("1");
			break;
		case R.id.play2:
			String[] keys = new String[11];
			keys[0] = "2";
			keys[1] = "2";
			keys[2] = "3";
			keys[3] = "4";
			keys[4] = "0";
			keys[5] = "2";
			keys[6] = "7";
			keys[7] = "8";
			keys[8] = "6";
			keys[9] = "0";
			keys[10] = "AC";
			sm.playSeqSounds(keys);
			break;
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		sm.cleanup();
	}
}
