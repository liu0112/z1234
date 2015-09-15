package com.towery.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.example.poi.R;
import com.example.poi.R.id;
import com.towery.beans.CollectData;
import com.towery.manager.CollectDataManager;
import com.towery.manager.MediadataManager;
import com.towery.utils.Keys;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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

public class BJActivity extends Activity {

	private int ListPos = 0;
	private ListView listview;
	private MediadataManager mediadataManager;
	private MyAdpter myAdpter;
	private CollectDataManager collectDataManager;
	private List<CollectData> query;
	private SharedPreferences sharedPreferences;
	private String taskid;
	private Editor edit;
	private int i = -1;
	private Button but1;
	private Button but2;
	private TextView text1;
	private TextView text2;
	private String orderBy = "questionid DESC";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_bj);

	}

	@Override
	protected void onStart() {

		// TODO Auto-generated method stub
		super.onStart();
		if (edit == null) {
			System.out.println("---------------------");
			init();
			initframe();
		} else {
			query = collectDataManager.query(null, "taskid", taskid, orderBy);
			if (query == null) {
				return;
			}
			myAdpter.setSelectItem(-1);
			myAdpter.notifyDataSetChanged();
		}

	}

	// 初始化
	private void init() {
		listview = (ListView) findViewById(R.id.listView1_bj);
		but1 = (Button) findViewById(R.id.button1_bj);
		but2 = (Button) findViewById(R.id.button2_bj);
		text1 = (TextView) findViewById(R.id.textView1_bj);
		text2 = (TextView) findViewById(R.id.textView2_bj);
		sharedPreferences = BJActivity.this.getSharedPreferences(Keys.SP,
				MODE_PRIVATE);
		taskid = sharedPreferences.getString(Keys.SP_TASKID, "");
		edit = sharedPreferences.edit();
		collectDataManager = new CollectDataManager(BJActivity.this);
		mediadataManager = new MediadataManager(BJActivity.this);
		query = collectDataManager.query(null, "taskid", taskid,
				"questionid DESC");
		if (query == null) {
			return;
		}
		myAdpter = new MyAdpter();
		listview.setAdapter(myAdpter);
	}

	// 处理事件
	private void initframe() {
		// TODO Auto-generated method stub
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i < 0) {
					Toast.makeText(BJActivity.this, "请选择对象……",
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
		text1.setOnClickListener(new OnClickListener() {
			private int ibut = 0;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ibut == 0) {
					query = collectDataManager.query(null, "taskid", taskid,
							"questionid");
					orderBy = "questionid";
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 1;
				} else {
					query = collectDataManager.query(null, "taskid", taskid,
							"questionid DESC");
					orderBy = "questionid DESC";
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 0;
				}
				i = -1;
			}
		});
		text2.setOnClickListener(new OnClickListener() {
			private int ibut = 0;

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				if (ibut == 0) {
					query = collectDataManager.query(null, "taskid", taskid,
							"remark");
					orderBy = "remark";
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 1;
				} else {
					query = collectDataManager.query(null, "taskid", taskid,
							"remark DESC");
					orderBy = "remark DESC";
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 0;
				}
				i = -1;

			}
		});
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					ListPos = listview.getFirstVisiblePosition(); // ListPos记录当前可见的List顶端的一行的位置
				}

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				myAdpter.setSelectItem(arg2);
				myAdpter.notifyDataSetInvalidated();
				i = arg2;

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
				BJActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.BJ_BJ);
		adapter.add(Keys.BJ_DW);
		adapter.add(Keys.BJ_SC);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (adapter.getItem(arg2) == Keys.BJ_BJ) {
					edit.putString(Keys.SP_QUESTIONID, query.get(i)
							.getQuestionid());
					edit.commit();
					Intent intent = new Intent(BJActivity.this,
							CJActivity.class);
					intent.putExtra("type", "BJ");
					intent.putExtra("x", query.get(i).getX());
					intent.putExtra("y", query.get(i).getY());
					intent.putExtra("remark", query.get(i).getRemark());
					startActivity(intent);
				} else if (adapter.getItem(arg2) == Keys.BJ_DW) {
					Intent intent = new Intent(BJActivity.this,
							ZhuActivity.class);
					startActivity(intent);
				} else if (adapter.getItem(arg2) == Keys.BJ_SC) {
					String path = Keys.SDURL + "POI/photos/" + taskid + "/"
							+ query.get(i).getQuestionid();
					File file = new File(path);
					delete(file);
					mediadataManager.delete("questionid", new String[] { query
							.get(i).getQuestionid() });
					collectDataManager.delete("questionid",
							new String[] { query.get(i).getQuestionid() });
					query.remove(i);
					if (query == null) {
						return;
					}
					myAdpter.notifyDataSetChanged();
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

	// 删除文件夹
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bj, menu);
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
				view = (LinearLayout) LayoutInflater.from(BJActivity.this)
						.inflate(R.layout.list_item_bj, null);
			} else {
				view = (LinearLayout) convertView;
			}
			TextView text1 = (TextView) view
					.findViewById(R.id.textView1_list_item_bj);
			TextView text2 = (TextView) view
					.findViewById(R.id.textView2_list_item_bj);
			text1.setText(query.get(position).getQuestionid());
			text2.setText(query.get(position).getRemark());

			if (position == selectItem) {
				view.setBackgroundColor(Color.RED);
				text1.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
				text2.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
			} else {
				view.setBackgroundColor(Color.TRANSPARENT);
				text1.setBackgroundResource(R.drawable.bj_border_ivory);
				text2.setBackgroundResource(R.drawable.bj_border_ivory);
			}
			// if (i == position) {
			// text1.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
			// text2.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
			// } else {
			// text1.setBackgroundResource(R.drawable.bj_border_ivory);
			// text2.setBackgroundResource(R.drawable.bj_border_ivory);
			// }
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
