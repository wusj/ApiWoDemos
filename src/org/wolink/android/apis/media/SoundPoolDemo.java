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
        sm.addSound("1", R.raw.one);
    }

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.play1:
			sm.playSound("1");
			break;
		case R.id.play2:
			String[] keys = new String[2];
			keys[0] = "1";
			keys[1] = "1";
			sm.playSeqSounds(keys);
			break;
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		sm.cleanup();
	}
}
