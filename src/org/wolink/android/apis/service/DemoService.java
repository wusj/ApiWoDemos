package org.wolink.android.apis.service;


import java.lang.ref.WeakReference;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DemoService extends Service {

	public DemoService() {
	}
	
    @Override
    public void onCreate() {
        super.onCreate();
        logMsg("onCreate");
    }
    
    @Override
    public void onDestroy() {
    	logMsg("onDestroy");
    	super.onDestroy();
    }
    
	@Override
	public IBinder onBind(Intent intent) {
		logMsg("onBind");
		return mBinder;
	}
    @Override
    public void onRebind(Intent intent) {
    	logMsg("onRebind");
    }	

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	logMsg("onStartCommand");
        return START_STICKY;    	
    }    
    
    @Override
    public boolean onUnbind(Intent intent) {
    	logMsg("onUnbind");
    	return true;
    }
	
	public boolean isConnected() {
		logMsg("isConnected");
		return true;
	}

    /*
     * By making this a static class with a WeakReference to the Service, we
     * ensure that the Service can be GCd even when the system process still
     * has a remote reference to the stub.
     */
    static class ServiceStub extends IDemoService.Stub {
        WeakReference<DemoService> mService;
        
        ServiceStub(DemoService service) {
            mService = new WeakReference<DemoService>(service);
        }

        public boolean isConnected() {
            return mService.get().isConnected();
        }
    }	
    
	private final IBinder mBinder = new ServiceStub(this);
	
	private void logMsg(String log) {
        Log.e("DemoService", log);
	}
}
