package org.wolink.android.apis.service;

import org.wolink.android.apis.R;
import org.wolink.android.apis.service.ServiceUtils.ServiceToken;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

public class ServiceDemoActivity extends Activity {
    private IDemoService mService = null;
    private ServiceToken mToken;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
    	super.onCreate(icicle);
    	
    	this.setContentView(R.layout.servicedemo);
    	findViewById(R.id.start_activity).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), ServiceDemoActivity2.class);
				startActivity(intent);
			}
		});
    }
    
    @Override
    public void onStart() {
    	super.onStart();
    	
    	mToken = ServiceUtils.bindToService(this, osc);
    }
    
    @Override
    public void onStop() {
    	ServiceUtils.unbindFromService(mToken);
        mService = null;    	
        
    	super.onStop();
    }
    
    private ServiceConnection osc = new ServiceConnection() {
        public void onServiceConnected(ComponentName classname, IBinder obj) {
            mService = IDemoService.Stub.asInterface(obj);
            try {
            	Log.e("ServiceDemo", "" + mService.isConnected());
            } catch (RemoteException ex) {
            	
            }
        }
        public void onServiceDisconnected(ComponentName classname) {
            mService = null;
        }
    };
}
