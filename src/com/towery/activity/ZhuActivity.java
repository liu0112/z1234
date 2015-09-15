package com.towery.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import service.GpsService;
import service.IGpsBinder;
import com.example.poi.R;
import com.towery.beans.CollectData;
import com.towery.beans.TaskInfo;
import com.towery.database.DataBaseHandler;
import com.towery.manager.CollectDataManager;
import com.towery.manager.TaskInfoManager;
import com.towery.utils.Keys;
import com.towery.utils.Utils;
import com.towery.utils.WMTSDisplay;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
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
import com.easymap.android.maps.v3.EzMap;
import com.easymap.android.maps.v3.EzMap.OnStatusChangeListener;
import com.easymap.android.maps.v3.EzMap.OnStatusChangeListener.STATUS;
import com.easymap.android.maps.v3.MapView;
import com.easymap.android.maps.v3.geometry.GeoPoint;
import com.easymap.android.maps.v3.layers.ogc.WMTSLayer;


public class ZhuActivity extends Activity implements OnStatusChangeListener {

	private Button but1;
	private Button but2;
	private ImageButton imgbut1;
	private IGpsBinder mBinder;
	private SharedPreferences sharedPreferences;
	private Dialog dialog;
	private CollectDataManager collectDataManager;
	private List<CollectData> query;
	private Editor edit;
	private String GPSoperation;
	private int widch = 200;
	private MapView mapView;
	private TaskInfoManager taskInfoManager;
	private DataBaseHandler dataBaseHandler;
	private String taskid;
	private TextView text1;
	private List<TaskInfo> taskinfoquery;
	private String taskname;
	private EzMap ezMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhu);

		mapView = (MapView) findViewById(R.id.MapView);
		if (mapView != null) {
			// 初始化许可，初始化许可包含两种方式，详细请看下述初始化许可
			try {
				InputStream is = this.getAssets().open(
						"EzServiceClient4Android.lic");
				mapView.initLicenseAsDevelopement(is);
			} catch (IOException e) {
				e.printStackTrace();

			}
			mapView.setOnStatusChangeListener(this);
			mapView.onCreate(savedInstanceState);

		}
//
//		init();
//		initframe();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		init();
//		initframe();
	}

	@Override
	public void onStatusChanged(STATUS status) {
		// TODO Auto-generated method stub
		if(status!=STATUS.INITIALIZED ){
			return;
		}
		
		ezMap = mapView.getMap();
		if(ezMap == null) {
			return;
		}
		//设置初始级别
		ezMap.zoomTo(12, false);
		//设置中心
		ezMap.centerAt(new GeoPoint(503371.925546077, 313907.323431658), false);
		WMTSLayer xxzyzx = WMTSDisplay.getLayer(WMTSDisplay.LAYER_SHILIANG);
		ezMap.addLayer(xxzyzx);
		ezMap.refreshMap();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mapView.onDestroy();
		super.onDestroy();
		// 页面消失GPS停止工作，
		if (mBinder != null) {
			this.unbindService(connection);
			mBinder = null;// 释放mBinder,防止重复解绑定
		}
	}

	@Override
	public void onLowMemory() {
		mapView.onLowMemory();
		super.onLowMemory();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		// if(this.locationDispaly!=null && this.locationDispaly.isStarted()){
		// this.locationDispaly.stop(false);
		// }
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		// if(this.locationDispaly!=null && !this.locationDispaly.isStarted()){
		// this.locationDispaly.start();
		// }
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mapView.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 创建退出对话框
			AlertDialog isExit = new AlertDialog.Builder(this).create();
			// 设置对话框标题
			isExit.setTitle("系统提示");
			// 设置对话框消息
			isExit.setMessage("确定要退出吗？编辑操作是否保存");
			// 添加选择按钮并注册监听
			isExit.setButton("退出", listener);
			isExit.setButton2("取消", listener);
			// 显示对话框
			isExit.show();

		}

		return false;

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "退出"按钮退出程序
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框

				break;

			default:
				break;
			}
		}
	};
	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "退出"按钮退出程序
				ContentValues values = new ContentValues();
				values.put("taskfinish", Utils.putbyte("已完成"));
				taskInfoManager.update(values, "taskid=?",
						new String[] { sharedPreferences.getString(
								Keys.SP_TASKID, "") });
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框

				break;

			default:
				break;
			}
		}
	};

	// 初始化
	public void init() {

		Intent i = new Intent(ZhuActivity.this, GpsService.class);
		ZhuActivity.this.bindService(i, connection, Context.BIND_AUTO_CREATE);
		text1 = (TextView) findViewById(R.id.textView1_zhu);
		but1 = (Button) findViewById(R.id.button1_zhu);
		but2 = (Button) findViewById(R.id.button2_zhu);

		imgbut1 = (ImageButton) findViewById(R.id.imageButton1_zhu);
		sharedPreferences = ZhuActivity.this.getSharedPreferences(Keys.SP,
				MODE_PRIVATE);
		taskid = sharedPreferences.getString(Keys.SP_TASKID, "");
		edit = sharedPreferences.edit();
		dataBaseHandler = new DataBaseHandler(ZhuActivity.this);
		collectDataManager = new CollectDataManager(ZhuActivity.this);
		taskInfoManager = new TaskInfoManager(ZhuActivity.this);
		query = collectDataManager.query(new String[] { "questionid" },
				"taskid", sharedPreferences.getString(Keys.SP_TASKID, ""),
				"questionid DESC");
		taskinfoquery = taskInfoManager.query();
		for (int j = 0; j < taskinfoquery.size(); j++) {
			if (taskinfoquery.get(j).getTaskid().equalsIgnoreCase(taskid)) {
				text1.setText(taskinfoquery.get(j).getTaskname());
				taskname = taskinfoquery.get(j).getTaskname();
			}
		}
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
				String taskName = sharedPreferences.getString(Keys.SP_TASKTYPE,
						"");
				// showPopupWindowZD(v);
				if ("主动PDA".equalsIgnoreCase(taskName)) {
					showPopupWindowZD(v);
				} else if ("被动PDA".equalsIgnoreCase(taskName)) {
					showPopupWindowBD(v);
				} else if ("废弃地址".equalsIgnoreCase(taskName)) {
					showPopupWindowFQ(v);
				} else if ("质检".equalsIgnoreCase(taskName)) {
					showPopupWindowZJ(v);
				} else {
					Toast.makeText(ZhuActivity.this, "任务选取失败",
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		imgbut1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mBinder.bindService(ZhuActivity.this);
			}
		});
	}

	// bindserviceconnection
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

	// 高级弹出菜单
	public void showPopupWindow1(View view) {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow1, null);
		ListView listview = (ListView) contentView
				.findViewById(R.id.listView1_pp1);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ZhuActivity.this, android.R.layout.simple_list_item_1);
		final PopupWindow popupWindow1 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
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
			public void onItemClick(AdapterView<?> parent, View view, int arg2,
					long id) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub

				if (adapter.getItem(arg2) == "点击模式") {

				} else if (adapter.getItem(arg2) == "拖拽模式") {

				} else if (adapter.getItem(arg2) == "定位任务区") {

				} else if (adapter.getItem(arg2) == "旋转标注") {

				} else if (adapter.getItem(arg2) == "显示标注") {

				} else if (adapter.getItem(arg2) == "切换任务定位") {

				} else if (adapter.getItem(arg2) == "显示配置") {

				} else if (adapter.getItem(arg2) == "数据库更新") {

				} else if (adapter.getItem(arg2) == "数据库升级") {

				}
				popupWindow1.dismiss();

			}
		});
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
		popupWindow1.setWidth(widch);
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
					QuestionidName();
					edit.commit();
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					i2.putExtra("x", "0000000");
					i2.putExtra("y", "111111111111");
					i2.putExtra("type", "ZHU");
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.ZJ_ZJDZ) {
					Intent i = new Intent(ZhuActivity.this, DZActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.ZJ_BJ) {
					Intent i = new Intent(ZhuActivity.this, BJActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.ZJ_RWLB) {
					Intent i = new Intent(ZhuActivity.this, RWLBActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.ZJ_WC) {
					AlertDialog dialog = new AlertDialog.Builder(
							ZhuActivity.this).create();
					// 设置对话框标题
					dialog.setTitle("任务完成");
					// 设置对话框消息
					dialog.setMessage(taskname);
					// 添加选择按钮并注册监听
					dialog.setButton("确定", listener2);
					dialog.setButton2("取消", listener2);
					// 显示对话框
					dialog.show();
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
		popupWindow2.setWidth(widch);
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
					QuestionidName();
					edit.commit();
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					i2.putExtra("x", "0000000");
					i2.putExtra("y", "111111111111");
					i2.putExtra("type", "ZHU");
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.FQ_FQDZ) {
					Intent i = new Intent(ZhuActivity.this, DZActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.FQ_BJ) {
					Intent i = new Intent(ZhuActivity.this, BJActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.FQ_RWLB) {
					Intent i = new Intent(ZhuActivity.this, RWLBActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.FQ_WC) {
					AlertDialog dialog = new AlertDialog.Builder(
							ZhuActivity.this).create();
					// 设置对话框标题
					dialog.setTitle("任务完成");
					// 设置对话框消息
					dialog.setMessage(taskname);
					// 添加选择按钮并注册监听
					dialog.setButton("确定", listener2);
					dialog.setButton2("取消", listener2);
					// 显示对话框
					dialog.show();

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
		popupWindow2.setWidth(widch);
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
					QuestionidName();
					edit.commit();
					Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
					i2.putExtra("x", "0000000");
					i2.putExtra("y", "111111111111");
					i2.putExtra("type", "ZHU");
					startActivity(i2);
				} else if (adapter.getItem(arg2) == Keys.BD_BDDZ) {
					Intent i = new Intent(ZhuActivity.this, DZActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.BD_BJ) {
					Intent i = new Intent(ZhuActivity.this, BJActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.BD_RWLB) {
					Intent i = new Intent(ZhuActivity.this, RWLBActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.BD_WC) {
					AlertDialog dialog = new AlertDialog.Builder(
							ZhuActivity.this).create();
					// 设置对话框标题
					dialog.setTitle("任务完成");
					// 设置对话框消息
					dialog.setMessage(taskname);
					// 添加选择按钮并注册监听
					dialog.setButton("确定", listener2);
					dialog.setButton2("取消", listener2);
					// 显示对话框
					dialog.show();
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
		popupWindow2.setWidth(widch);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	// 主动PDA
	public void showPopupWindowZD(final View view) {

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
					QuestionidName();
					edit.commit();
					GPSoperation = Keys.ZJ_CJ;
					mBinder.bindService(ZhuActivity.this);
				} else if (adapter.getItem(arg2) == Keys.ZD_BJ) {
					Intent i = new Intent(ZhuActivity.this, BJActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.ZD_RWLB) {
					Intent i = new Intent(ZhuActivity.this, RWLBActivity.class);
					startActivity(i);
				} else if (adapter.getItem(arg2) == Keys.ZD_WC) {

					AlertDialog dialog = new AlertDialog.Builder(
							ZhuActivity.this).create();
					// 设置对话框标题
					dialog.setTitle("任务完成");
					// 设置对话框消息
					dialog.setMessage(taskname);
					// 添加选择按钮并注册监听
					dialog.setButton("确定", listener2);
					dialog.setButton2("取消", listener2);
					// 显示对话框
					dialog.show();
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
		popupWindow2.setWidth(widch);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	// 地址命名
	public void QuestionidName() {
		String name;
		String taskName = sharedPreferences.getString(Keys.SP_TASKTYPE, "");
		if (query == null) {
			if ("主动PDA".equalsIgnoreCase(taskName)) {
				edit.putString(Keys.SP_QUESTIONID, "ATI001");
			} else if ("被动PDA".equalsIgnoreCase(taskName)) {
				edit.putString(Keys.SP_QUESTIONID, "1000");
			} else if ("废弃地址".equalsIgnoreCase(taskName)) {
				edit.putString(Keys.SP_QUESTIONID, "1000");
			} else if ("质检".equalsIgnoreCase(taskName)) {

			} else {
				Toast.makeText(ZhuActivity.this, "任务选取失败", Toast.LENGTH_SHORT)
						.show();
			}
		} else {
			String questionid = query.get(0).getQuestionid();
			if ("主动PDA".equalsIgnoreCase(taskName)) {
				String spStr[] = questionid.split("I");
				int intname = Integer.parseInt(spStr[1]) + 1;
				if (intname < 10) {
					name = "ATI00" + intname;
				} else if (intname < 100) {
					name = "ATI0" + intname;
				} else {
					name = "ATI" + intname;
				}
				edit.putString(Keys.SP_QUESTIONID, name);
			} else if ("被动PDA".equalsIgnoreCase(taskName)) {
				int intname = Integer.parseInt(questionid) + 1;
				edit.putString(Keys.SP_QUESTIONID, String.valueOf(intname));
			} else if ("废弃地址".equalsIgnoreCase(taskName)) {
				int intname = Integer.parseInt(questionid) + 1;
				edit.putString(Keys.SP_QUESTIONID, String.valueOf(intname));
			} else if ("质检".equalsIgnoreCase(taskName)) {

			} else {
				Toast.makeText(ZhuActivity.this, "任务选取失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.zhu, menu);
		return true;
	}

	// 反射监听
	public void locationChanged(Location loc) {
		updateWithNewLocation(loc);
	}

	// Gps监听器调用，处理位置信息
	private void updateWithNewLocation(Location location) {
		if (GPSoperation == Keys.ZD_CJ) {
			Intent i2 = new Intent(ZhuActivity.this, CJActivity.class);
			String latLongString;
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				i2.putExtra("GPSy", "" + lat);
				i2.putExtra("GPSx", "" + lng);
				i2.putExtra("GPStime", "" + location.getTime());
			} else {
				latLongString = "无法获取地理信息";
				Toast.makeText(ZhuActivity.this, latLongString,
						Toast.LENGTH_SHORT).show();
			}
			i2.putExtra("type", "ZHU");
			i2.putExtra("x", "10000000");
			i2.putExtra("y", "111111111111");
			GPSoperation = "";
			startActivity(i2);
		} else {
			String latLongString;
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				latLongString = "纬度:" + lat + "/n经度:" + lng;
			} else {
				latLongString = "无法获取地理信息";
			}
			// Toast.makeText(ZhuActivity.this, "您当前的位置是:/n" + latLongString,
			// Toast.LENGTH_SHORT).show();
		}

	}

	// 反射监听
	public void locationChanged1(Location loc) {
		updateWithNewLocation1(loc);
	}

	private void updateWithNewLocation1(Location location) {

		String latLongString;
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "纬度:" + lat + "/n经度:" + lng;
		} else {
			latLongString = "无法获取地理信息";
		}
		Toast.makeText(ZhuActivity.this, "您当前的位置是:/n" + latLongString,
				Toast.LENGTH_SHORT).show();

	}

}
