package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GpsService extends Service {

	private GpsBinder binder = new GpsBinder();

	@Override
	public void onCreate() {
		System.out.println("============onCreate");
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generatedmethod stub
		System.out.println("============onDestroy");
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generatedmethod stub
		System.out.println("==========start");
		super.onStart(intent, startId);
//		binder.bindService();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generatedmethod stub
		System.out.println("==========onUnbind");
		binder.unbindService();
		return true;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generatedmethod stub
		System.out.println("==========onBind"+binder.toString());
		return binder;
	}

}
