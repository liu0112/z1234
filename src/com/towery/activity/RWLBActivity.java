package com.towery.activity;

import java.util.List;

import com.example.poi.R;
import com.example.poi.R.layout;
import com.example.poi.R.menu;
import com.towery.activity.BJActivity.MyAdpter;
import com.towery.beans.TaskInfo;
import com.towery.manager.TaskInfoManager;
import com.towery.utils.Keys;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class RWLBActivity extends Activity {

	private ListView list;
	private Button but1;
	private Button but2;
	private SharedPreferences sharedPreferences;
	private Editor edit;
	private TaskInfoManager infoManager;
	private List<TaskInfo> query;
	private int i = -1;
	private MyAdpter myAdpter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_rwlb);
		init();
		initframe();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		list.setAdapter(myAdpter);
	}

	private void init() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.listView1_rw);
		but1 = (Button) findViewById(R.id.button1_rw);
		but2 = (Button) findViewById(R.id.button2_rw);
		sharedPreferences = RWLBActivity.this.getSharedPreferences(Keys.SP,
				MODE_PRIVATE);
		edit = sharedPreferences.edit();
		infoManager = new TaskInfoManager(RWLBActivity.this);
		query = infoManager.query();
		myAdpter = new MyAdpter();
		i=0;
		myAdpter.setSelectItem(0);
		list.setAdapter(myAdpter);
	}

	private void initframe() {
		// TODO Auto-generated method stub
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i < 0) {
					Toast.makeText(RWLBActivity.this, "请选择对象……",
							Toast.LENGTH_SHORT).show();
				} else {
					showPopupWindow(v);
				}
			}
		});
		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				i = arg2;
				myAdpter.setSelectItem(arg2);
				myAdpter.notifyDataSetInvalidated();
			}
		});
	}

	public void showPopupWindow(final View view) {

		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list = (ListView) contentView.findViewById(R.id.listView1_pp2);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				RWLBActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.BJ_BJ);
		adapter.add(Keys.BJ_DW);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (adapter.getItem(arg2) == Keys.BJ_BJ) {
					edit.putString(Keys.SP_TASKID, query.get(i).getTaskid());
					edit.putString(Keys.SP_TASKTYPE, query.get(i).getTasktype());
					edit.commit();
					finish();
				} else if (adapter.getItem(arg2) == Keys.BJ_DW) {
					Intent intent = new Intent(RWLBActivity.this,
							ZhuActivity.class);
					startActivity(intent);
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
		popupWindow2.setWidth(360);
		popupWindow2.showAsDropDown(view, 0, 3);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rwlb, menu);
		return true;
	}

	class MyAdpter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return query.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			final LinearLayout view;
			if (convertView == null) {
				view = (LinearLayout) LayoutInflater.from(RWLBActivity.this)
						.inflate(R.layout.list_item_rw, null);
			} else {
				view = (LinearLayout) convertView;
			}
			TextView text1 = (TextView) view
					.findViewById(R.id.textView1_item_rw);
			TextView text2 = (TextView) view
					.findViewById(R.id.textView2_item_rw);
			TextView text3 = (TextView) view
					.findViewById(R.id.textView3_item_rw);
			TextView text4 = (TextView) view
					.findViewById(R.id.textView4_item_rw);
			text1.setText(query.get(position).getTaskname());
			text2.setText(query.get(position).getTasktype());
			text3.setText(query.get(position).getTaskdesc());
			text4.setText(query.get(position).getTaskfinish());
			if (selectItem == position) {
				view.setBackgroundColor(Color.GRAY);
				text1.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
				text2.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
				text3.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
				text4.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
			} else if (query.get(position).getTaskfinish().equalsIgnoreCase("未完成")) {
				view.setBackgroundColor(Color.TRANSPARENT);
				text1.setBackgroundResource(R.drawable.bj_border_red);
				text2.setBackgroundResource(R.drawable.bj_border_red);
				text3.setBackgroundResource(R.drawable.bj_border_red);
				text4.setBackgroundResource(R.drawable.bj_border_red);
			}else {
				view.setBackgroundColor(Color.TRANSPARENT);
				text1.setBackgroundResource(R.drawable.bj_border_green);
				text2.setBackgroundResource(R.drawable.bj_border_green);
				text3.setBackgroundResource(R.drawable.bj_border_green);
				text4.setBackgroundResource(R.drawable.bj_border_green);
			}
			
			
			
			
			
			
			
			// text1.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// // TODO Auto-generated method stub
			// i = position;
			// onStart();
			// }
			// });
			// text2.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// i = position;
			// onStart();
			// }
			// });
			return view;
		}
		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
		}

		private int selectItem = -1;
	}
}
