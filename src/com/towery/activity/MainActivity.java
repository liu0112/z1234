package com.towery.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.poi.R;
import com.towery.beans.userinfo;
import com.towery.manager.UserManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
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
	private UserManager userManager;
	private List<userinfo> list_user;
	private ArrayList<String> list_sp1_username;
	private ArrayAdapter<String> adapter_sp1;
	private ArrayList<String> list_sp2_taskname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		init();
		initframe();
	}
//初始化
	public void init() {
		but1 = (Button) findViewById(R.id.button1_main);
		but2 = (Button) findViewById(R.id.button2_main);
		sp1 = (Spinner) findViewById(R.id.spinner1_main);
		sp2 = (Spinner) findViewById(R.id.spinner2_main);
		//数据库获取账号信息
		userManager = new UserManager(MainActivity.this);
		list_user = userManager.query();
		list_sp1_username = new ArrayList<String>();
		for (int i = 0; i < list_user.size(); i++) {
			list_sp1_username.add(list_user.get(i).getUsername());
		}
		adapter_sp1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_sp1_username);
		adapter_sp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter_sp1);
		list_sp2_taskname = new ArrayList<String>();
		list_sp2_taskname.add("主动PDA");
		list_sp2_taskname.add("被动PDA");
		list_sp2_taskname.add("废弃地址");
		list_sp2_taskname.add("质检");
	}
// 事件操作
	public void initframe() {
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ZhuActivity.class);
				startActivity(intent);
				System.out.println("");

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
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int id, long arg3) {
				// TODO Auto-generated method stub
				System.out.println("======="+list_sp1_username.get(id));
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
