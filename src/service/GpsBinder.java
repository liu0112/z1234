package service;

import java.lang.reflect.Method;
import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class GpsBinder extends Binder implements IGpsBinder {
	private LocationManager locationManager;
	private Location location;
	private String provider;
	private Context mContext = null;

	public GpsBinder() {

	}

	@Override
	// 接口暴露方法
	public void bindService(Context ctx) {
		mContext = ctx;
		// 获取LocationManager服务
		locationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);

		// 如果未设置位置源，打开GPS设置界面
		openGPS();
		// 获取Location Provider
		getProvider();
		// 获取位置
		location = locationManager.getLastKnownLocation(provider);
		System.out.println("==============bindService");
		// 显示位置信息到文字标签
		updateWithNewLocation(location);
		// 注册监听器locationListener，第2、3个参数可以控制接收gps消息的频度以节省电力。第2个参数为毫秒，
		// 表示调用listener的周期，第3个参数为米,表示位置移动指定距离后就调用listener
		locationManager.requestLocationUpdates(provider, 2000, 10,
				locationListener);
	}

	private void openGPS() {
		// TODO Auto-generated method stub

		boolean bGPS = locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
		boolean bNetwork = locationManager
				.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
		Log.e(":::", "bGPS=" + bGPS + "bNetwork=" + bNetwork);
		if (bGPS || bNetwork) {
			Toast.makeText(mContext, "位置源已设置！", Toast.LENGTH_SHORT).show();
			return;
		}
		// Toast.makeText(this, "位置源未设置！", Toast.LENGTH_SHORT).show();
		((Activity) mContext).showDialog(0);

	}

	public void unbindService() {
		// 注销location监听器
		locationManager.removeUpdates(locationListener);
	}

	// 获取Location Provider
	private void getProvider() {
		// 构建位置查询条件
		Criteria criteria = new Criteria();
		// 查询精度：高
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		// 是否查询海拨：否
		criteria.setAltitudeRequired(false);
		// 是否查询方位角:否
		criteria.setBearingRequired(false);
		// 是否允许付费：是
		criteria.setCostAllowed(true);
		// 电量要求：低
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// 返回最合适的符合条件的provider，第2个参数为true说明,如果只有一个provider是有效的,则返回当前provider
		provider = locationManager.getBestProvider(criteria, true);
	}

	// Gps消息监听器
	private final LocationListener locationListener = new LocationListener() {
		// 位置发生改变后调用
		public void onLocationChanged(Location location) {
			System.out.println("=============onLocationChanged");
			updateWithNewLocation(location);
		}

		// provider被用户关闭后调用
		public void onProviderDisabled(String provider) {
			updateWithNewLocation(null);
		}

		// provider被用户开启后调用
		public void onProviderEnabled(String provider) {
		}

		// provider状态变化时调用
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

	};

	// Gps监听器调用，处理位置信息
	private void updateWithNewLocation(Location location) {
		System.out.println("=============updateWithNewLocation");
		// 利用反射机制调用mContext的locationChanged方法
		Class<?>[] types = new Class[] { Location.class }; // 这个方法有1个参数
		try {
			Method m = mContext.getClass().getMethod("locationChanged", types);
			if (m != null)
				m.invoke(mContext, location);
		} catch (Exception e) {
			Log.e(GpsBinder.class.getName(), e.toString());
		}
		Log.i(GpsBinder.class.getName(), "location ischanged:" + location);
	}

}
