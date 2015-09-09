package com.towery.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.poi.R;
import com.towery.beans.SetUp;
import com.towery.beans.TaskInfo;
import com.towery.beans.userinfo;
import com.towery.database.DataBaseHandler;
import com.towery.manager.MediadataManager;
import com.towery.manager.TaskInfoManager;
import com.towery.manager.UserManager;
import com.towery.utils.Keys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {

	private Button but1;
	private Button but2;
	private Spinner sp1;
	private Spinner sp2;
	private String userid = "";
	private UserManager userManager;
	private List<userinfo> list_userinfo;
	private ArrayList<String> list_sp1_username;
	private ArrayAdapter<String> adapter_sp1;
	private ArrayList<String> list_sp2_taskname;
	private ArrayAdapter<String> adapter_sp2;
	private SharedPreferences sharedPreferences;
	private Editor edit;
	private TaskInfoManager taskInfoManager;
	private List<TaskInfo> list_taskinfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		init();
		initframe();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		finish();
	}

	// 初始化
	public void init() {
		but1 = (Button) findViewById(R.id.button1_main);
		but2 = (Button) findViewById(R.id.button2_main);
		sp1 = (Spinner) findViewById(R.id.spinner1_main);
		sp2 = (Spinner) findViewById(R.id.spinner2_main);
		// 本地存储键
		sharedPreferences = MainActivity.this.getSharedPreferences(Keys.SP,
				MODE_PRIVATE);
		edit = sharedPreferences.edit();
		// 数据库获取账号信息
		userManager = new UserManager(MainActivity.this);
		list_userinfo = userManager.query();
		// 数据库获取任务信息
		taskInfoManager = new TaskInfoManager(MainActivity.this);
		list_taskinfo = taskInfoManager.query();
		list_sp1_username = new ArrayList<String>();
		for (int i = 0; i < list_userinfo.size(); i++) {
			list_sp1_username.add(list_userinfo.get(i).getUsername());
		}
		adapter_sp1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_sp1_username);
		adapter_sp1
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter_sp1);
		list_sp2_taskname = new ArrayList<String>();
		for (int i = 0; i < list_taskinfo.size(); i++) {
			list_sp2_taskname.add(list_taskinfo.get(i).getTaskname());
		}
		adapter_sp2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_sp2_taskname);
		adapter_sp2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter_sp2);
		if (list_taskinfo.get(0).getTasktype() != "") {
			edit.putString(Keys.SP_TASKTYPE, list_taskinfo.get(0).getTasktype());
			edit.putString(Keys.SP_TASKID, list_taskinfo.get(0).getTaskid());

		}
		if (list_userinfo.get(0).getUsername() != "") {
			edit.putString(Keys.SP_USERNAME, list_userinfo.get(0).getUsername());
		}
		userid = list_userinfo.get(0).getUserid();
	}

	// 事件操作
	public void initframe() {
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				edit.commit();
				DataBaseHandler dataBaseHandler = new DataBaseHandler(
						MainActivity.this);
				ArrayList<SetUp> query = dataBaseHandler.query();
				int j = 0;
				for (int i = 0; i < query.size(); i++) {
					if (query.get(i).getUserid() == userid) {
						j = 1;
					}
				}
				if (j == 0) {
					ArrayList<SetUp> arrayList = new ArrayList<SetUp>();
					SetUp setUp = new SetUp();
					setUp.setUserid(userid);
					setUp.setDjms("0");
					setUp.setTzms("1");
					setUp.setDwrwq("1");
					setUp.setXzbz("0");
					setUp.setXsbz("1");
					setUp.setQhrwdw("1");
					setUp.setXspz("0");
					setUp.setSjkgx("0");
					setUp.setSjksj("0");
					arrayList.add(setUp);
					dataBaseHandler.insert(arrayList);
				}
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ZhuActivity.class);
				startActivity(intent);
			}
		});
		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();

			}
		});
		sp1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int id,
					long arg3) {
				// TODO Auto-generated method stub
				edit.putString(Keys.SP_USERNAME, list_userinfo.get(id)
						.getUsername());
				userid = list_userinfo.get(id).getUserid();
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		sp2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int id,
					long arg3) {
				// TODO Auto-generated method stub
				System.out.println("======="
						+ list_taskinfo.get(id).getTasktype());
				edit.putString(Keys.SP_TASKTYPE, list_taskinfo.get(id)
						.getTasktype());
				edit.putString(Keys.SP_TASKID, list_taskinfo.get(id)
						.getTaskid());
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
