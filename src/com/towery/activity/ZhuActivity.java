package com.towery.activity;

import java.util.ArrayList;
import java.util.List;

import service.GpsService;
import service.IGpsBinder;
import com.example.poi.R;
import com.towery.beans.SetUp;
import com.towery.beans.userinfo;
import com.towery.database.DataBaseHandler;
import com.towery.database.SDDataBaseHandler;
import com.towery.utils.Keys;

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

		init();
		initframe();
//		 SDDataBaseHandler sdhandler = new
//		 SDDataBaseHandler(ZhuActivity.this);
//		 ArrayList<userinfo> sdlist = new ArrayList<userinfo>();
//		 userinfo userinfo = new userinfo();
//		 userinfo.setRecNo("0");
//		 userinfo.setUserid("0");
//		 userinfo.setUsername("0");
//		 userinfo.setUserpswd("0");
//		 sdlist.add(userinfo);
//		 sdhandler.insert(sdlist);
//		 List<userinfo> sdlist2 = sdhandler.query1();
//		 System.out.println("=======" + sdlist2.toString());
//		 DataBaseHandler handler = new DataBaseHandler(ZhuActivity.this);
//		 ArrayList<SetUp> list = new ArrayList<SetUp>();
//		 SetUp setUp = new SetUp();
//		 setUp.setDjms("djms");
//		 setUp.setTzms("tzms");
//		 setUp.setDwrwq("dwrwq");
//		 setUp.setXzbz("xzbz");
//		 setUp.setXsbz("xsbz");
//		 setUp.setQhrwdw("qhrwdw");
//		 setUp.setXspz("xspz");
//		 setUp.setSjkgx("sukgx");
//		 setUp.setSjkgx("sujgx");
//		 list.add(setUp);
//		 handler.insert(list);
//		 ArrayList<SetUp> list2 = handler.query();
//		 System.out.println("==" + list2.toString());

	}

	// 初始化
	public void init() {
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
		but1 = (Button) findViewById(R.id.button1_zhu);
		but2 = (Button) findViewById(R.id.button2_zhu);
		imgbut1 = (ImageButton) findViewById(R.id.imageButton1_zhu);
	}
// 事件操作
	public void initframe() {
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindow1(v);
			}
		});

		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopupWindowBD(v);
			}
		});

		imgbut1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("==============");
				mBinder.bindService(ZhuActivity.this);
			}
		});
	}

	// 高级弹出菜单
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
			}
		});
		popupWindow1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_popwindow));
		popupWindow1.setWidth(250);
		popupWindow1.showAsDropDown(view);
	}

	// 被动PDA弹出菜单
	public void showPopupWindowZJ(View view) {

		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list = (ListView) contentView.findViewById(R.id.listView1_pp2);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.ZJ_POILB);
		adapter.add(Keys.ZJ_DWRWQ);
		adapter.add(Keys.ZJ_CJ);
		adapter.add(Keys.ZJ_ZJDZ);
		adapter.add(Keys.ZJ_BJ);
		adapter.add(Keys.ZJ_RWLB);
		adapter.add(Keys.ZJ_WC);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (adapter.getItem(arg2) == Keys.ZJ_POILB) {

				} else if (adapter.getItem(arg2) == Keys.ZJ_DWRWQ) {

				} else if (adapter.getItem(arg2) == Keys.ZJ_CJ) {
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.ZJ_ZJDZ) {

				} else if (adapter.getItem(arg2) == Keys.ZJ_BJ) {

				} else if (adapter.getItem(arg2) == Keys.ZJ_RWLB) {

				} else if (adapter.getItem(arg2) == Keys.ZJ_WC) {

				}
				popupWindow2.dismiss();

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
				R.drawable.bg_popwindow));
		popupWindow2.setWidth(220);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	// 废弃PDA弹出菜单
	public void showPopupWindowFQ(View view) {

		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list = (ListView) contentView.findViewById(R.id.listView1_pp2);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.FQ_POILB);
		adapter.add(Keys.FQ_DWRWQ);
		adapter.add(Keys.FQ_CJ);
		adapter.add(Keys.FQ_FQDZ);
		adapter.add(Keys.FQ_BJ);
		adapter.add(Keys.FQ_RWLB);
		adapter.add(Keys.FQ_WC);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (adapter.getItem(arg2) == Keys.FQ_POILB) {

				} else if (adapter.getItem(arg2) == Keys.FQ_DWRWQ) {

				} else if (adapter.getItem(arg2) == Keys.FQ_CJ) {
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.FQ_FQDZ) {

				} else if (adapter.getItem(arg2) == Keys.FQ_BJ) {

				} else if (adapter.getItem(arg2) == Keys.FQ_RWLB) {

				} else if (adapter.getItem(arg2) == Keys.FQ_WC) {

				}
				popupWindow2.dismiss();

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
				R.drawable.bg_popwindow));
		popupWindow2.setWidth(220);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	// 被动PDA弹出菜单
	public void showPopupWindowBD(View view) {

		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list = (ListView) contentView.findViewById(R.id.listView1_pp2);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.BD_POILB);
		adapter.add(Keys.BD_DWRWQ);
		adapter.add(Keys.BD_CJ);
		adapter.add(Keys.BD_BDDZ);
		adapter.add(Keys.BD_BJ);
		adapter.add(Keys.BD_RWLB);
		adapter.add(Keys.BD_WC);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				if (adapter.getItem(arg2) == Keys.BD_POILB) {

				} else if (adapter.getItem(arg2) == Keys.BD_DWRWQ) {

				} else if (adapter.getItem(arg2) == Keys.BD_CJ) {
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.BD_BDDZ) {

				} else if (adapter.getItem(arg2) == Keys.BD_BJ) {

				} else if (adapter.getItem(arg2) == Keys.BD_RWLB) {

				} else if (adapter.getItem(arg2) == Keys.BD_WC) {

				}
				popupWindow2.dismiss();

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
				R.drawable.bg_popwindow));
		popupWindow2.setWidth(220);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	// 主动PDA
	public void showPopupWindowZD(View view) {

		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list = (ListView) contentView.findViewById(R.id.listView1_pp2);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.ZD_DWRWQ);
		adapter.add(Keys.ZD_CJ);
		adapter.add(Keys.ZD_BJ);
		adapter.add(Keys.ZD_RWLB);
		adapter.add(Keys.ZD_WC);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (adapter.getItem(arg2) == Keys.ZD_DWRWQ) {

				} else if (adapter.getItem(arg2) == Keys.ZD_CJ) {
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.ZD_BJ) {

				} else if (adapter.getItem(arg2) == Keys.ZD_RWLB) {

				} else if (adapter.getItem(arg2) == Keys.ZD_WC) {

				}
				popupWindow2.dismiss();
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
				R.drawable.bg_popwindow));
		popupWindow2.setWidth(220);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhu, menu);
		return true;
	}
    //反射监听
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
		System.out.println("================" + "您当前的位置是:/n" + latLongString);
		Toast.makeText(ZhuActivity.this, "您当前的位置是:/n" + latLongString,
				Toast.LENGTH_SHORT).show();

	}
}
