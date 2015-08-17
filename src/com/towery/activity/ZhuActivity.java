package com.towery.activity;

import java.util.ArrayList;
import java.util.List;

import service.GpsService;
import service.IGpsBinder;

import com.example.poi.R;
import com.example.poi.R.layout;
import com.example.poi.R.menu;
import com.towery.beans.SetUp;
import com.towery.beans.userinfo;
import com.towery.database.DataBaseHandler;
import com.towery.database.SDDataBaseHandler;
import com.towery.database.SQLiteHelper;

import android.R.string;
import android.hardware.Camera.ShutterCallback;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class ZhuActivity extends Activity {

	private Button but1;
	private Button but2;
	private ImageButton imgbut1;
	private IGpsBinder mBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhu);
		final ServiceConnection connection = new ServiceConnection() {

			

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				// TODO Auto-generated method stub
				mBinder = (IGpsBinder) service;
				if (mBinder != null) {
					mBinder.bindService(ZhuActivity.this);
				}

			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				// TODO Auto-generated method stub
				
			}

		};
		Intent i = new Intent(ZhuActivity.this, GpsService.class);
		ZhuActivity.this.bindService(i, connection, Context.BIND_AUTO_CREATE);
		

		// SDDataBaseHandler sdhandler = new
		// SDDataBaseHandler(ZhuActivity.this);
		// ArrayList<userinfo> sdlist = new ArrayList<userinfo>();
		// userinfo userinfo = new userinfo();
		// userinfo.setRecNo("0");
		// userinfo.setUserid("0");
		// userinfo.setUsername("0");
		// userinfo.setUserpswd("0");
		// sdlist.add(userinfo);
		// sdhandler.insert(sdlist);
		// List<userinfo> sdlist2 = sdhandler.query();
		// System.out.println("=======" + sdlist2.toString());
		// DataBaseHandler handler = new DataBaseHandler(ZhuActivity.this);
		// ArrayList<SetUp> list = new ArrayList<SetUp>();
		// SetUp setUp = new SetUp();
		// setUp.setDjms("djms");
		// setUp.setTzms("tzms");
		// setUp.setDwrwq("dwrwq");
		// setUp.setXzbz("xzbz");
		// setUp.setXsbz("xsbz");
		// setUp.setQhrwdw("qhrwdw");
		// setUp.setXspz("xspz");
		// setUp.setSjkgx("sukgx");
		// setUp.setSjkgx("sujgx");
		// list.add(setUp);
		// handler.insert(list);
		// ArrayList<SetUp> list2 = handler.query();
		// System.out.println("==" + list2.toString());
		but1 = (Button) findViewById(R.id.button1_zhu);
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindow1(v);
			}
		});
		but2 = (Button) findViewById(R.id.button2_zhu);
		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindow2(v);
			}
		});
		imgbut1 = (ImageButton) findViewById(R.id.imageButton1_zhu);
		imgbut1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("==============");
//				Intent intent = new Intent(ZhuActivity.this, GpsService.class);				
//				startService(intent);
				mBinder.bindService(ZhuActivity.this);
			}
		});
	}

	public void showPopupWindow1(View view) {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow1, null);
		ListView listview = (ListView) contentView
				.findViewById(R.id.listView1_pp1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		adapter.add("点击模式");
		adapter.add("拖拽模式");
		adapter.add("定位任务区");
		adapter.add("旋转标注");
		adapter.add("显示标注");
		adapter.add("切换任务定位");
		adapter.add("显示配置");
		adapter.add("数据库更新");
		adapter.add("数据库升级");
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(ZhuActivity.this, "" + position + id,
						Toast.LENGTH_SHORT).show();
			}
		});
		final PopupWindow popupWindow1 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow1.setTouchable(true);
		popupWindow1.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
				// ��������true�Ļ���touch�¼���������
				// ���غ�
				// PopupWindow��onTouchEvent�������ã��������ⲿ�����޷�dismiss
			}
		});
		// �������PopupWindow�ı����������ǵ���ⲿ������Back���޷�dismiss����
		popupWindow1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.t0127c45a8e3188f139));
		popupWindow1.setWidth(680);
		popupWindow1.showAsDropDown(view);
	}

	public void showPopupWindow2(View view) {

		View contentView2 = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView2,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list2 = (ListView) contentView2
				.findViewById(R.id.listView1_pp2);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		adapter2.add("定位任务区");
		adapter2.add("采集");
		adapter2.add("编辑");
		adapter2.add("任务列表");
		adapter2.add("完成");
		adapter2.add("退出");
		list2.setAdapter(adapter2);
		list2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:

					break;
				case 1:
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					startActivity(i2);
					popupWindow2.dismiss();
					break;
				case 2:

					break;
				case 3:

					break;
				default:
					break;
				}

			}
		});

		popupWindow2.setTouchable(true);
		popupWindow2.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		popupWindow2.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.t0127c45a8e3188f139));
		popupWindow2.setWidth(580);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhu, menu);
		return true;
	}

	public void locationChanged(Location loc) {
		System.out.println("=============+");
		updateWithNewLocation(loc);
	}

	// Gps监听器调用，处理位置信息
	private void updateWithNewLocation(Location location) {
		String latLongString;
		
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "纬度:" + lat + "/n经度:" + lng;
		} else {
			latLongString = "无法获取地理信息";
		}
		System.out.println("================"+ "您当前的位置是:/n" + latLongString);
		Toast.makeText(ZhuActivity.this, "您当前的位置是:/n" + latLongString, Toast.LENGTH_SHORT).show();
		
	}
}
