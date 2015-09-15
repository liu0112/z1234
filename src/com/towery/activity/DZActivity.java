package com.towery.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.Window;
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

import com.example.poi.R;
import com.towery.beans.CollectData;
import com.towery.manager.CollectDataManager;
import com.towery.utils.Keys;
import com.towery.utils.Utils;

public class DZActivity extends Activity {

	private ListView listview;
	private int i;
	private Button but1;
	private Button but2;
	private SharedPreferences sharedPreferences;
	private String taskid;
	private CollectDataManager collectDataManager;
	private List<CollectData> query;
	private MyAdpter myAdpter;
	private TextView text1;
	private TextView text2;
	private String taskname;
	private Editor edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dz);
		init();
		initframe();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	private void init() {
		// TODO Auto-generated method stub
		listview = (ListView) findViewById(R.id.listView1_dz);
		but1 = (Button) findViewById(R.id.button1_dz);
		but2 = (Button) findViewById(R.id.button2_dz);
		text1 = (TextView) findViewById(R.id.textView1_dz);
		text2 = (TextView) findViewById(R.id.textView2_dz);
		sharedPreferences = DZActivity.this.getSharedPreferences(Keys.SP,
				MODE_PRIVATE);
		edit = sharedPreferences.edit();
		taskname = sharedPreferences.getString(Keys.SP_TASKTYPE, "");
		taskid = sharedPreferences.getString(Keys.SP_TASKID, "");
		collectDataManager = new CollectDataManager(DZActivity.this);
		query = collectDataManager.query(null, "taskid", taskid, "answer DESC");
		if (query == null) {
			return;
		}
		myAdpter = new MyAdpter();
		listview.setAdapter(myAdpter);
	}

	private void initframe() {
		// TODO Auto-generated method stub
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i < 0) {
					Toast.makeText(DZActivity.this, "请选择对象……",
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
							"content");
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 1;
				} else {
					query = collectDataManager.query(null, "taskid", taskid,
							"content DESC");
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
							"answer");
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 1;
				} else {
					query = collectDataManager.query(null, "taskid", taskid,
							"answer DESC");
					myAdpter.setSelectItem(-1);
					myAdpter.notifyDataSetInvalidated();
					ibut = 0;
				}
				i = -1;
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

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

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			ContentValues values = new ContentValues();
			Intent intent = new Intent();
			String str = null;
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// 核实有 已废弃

				if ("被动PDA".equalsIgnoreCase(taskname)) {
					str = "核实有";
					values.put("answer", Utils.putbyte(str));
					collectDataManager.update(values, "questionid=?",
							new String[] { query.get(i).getQuestionid() });
					CollectData collectData = query.get(i);
					collectData.setAnswer(str);
					query.remove(i);
					query.add(i, collectData);
					myAdpter.notifyDataSetInvalidated();
					intent.setClass(DZActivity.this, CJActivity.class);
					edit.putString(Keys.SP_QUESTIONID, query.get(i)
							.getQuestionid());
					intent.putExtra("type", "ZHU");
					intent.putExtra("x", "10000000");
					intent.putExtra("y", "111111111111");
					startActivity(intent);
				} else if ("废弃地址".equalsIgnoreCase(taskname)) {
					str = "已废弃";
					values.put("answer", Utils.putbyte(str));
					collectDataManager.update(values, "questionid=?",
							new String[] { query.get(i).getQuestionid() });
					CollectData collectData = query.get(i);
					collectData.setAnswer(str);
					query.remove(i);
					query.add(i, collectData);
					myAdpter.notifyDataSetInvalidated();
				} else if ("质检".equalsIgnoreCase(taskname)) {

				}

				break;
			case AlertDialog.BUTTON_NEGATIVE:// 核实无 仍保留
				if ("被动PDA".equalsIgnoreCase(taskname)) {
					str = "核实无";
				} else if ("废弃地址".equalsIgnoreCase(taskname)) {
					str = "仍保留";
				} else if ("质检".equalsIgnoreCase(taskname)) {

				}
				values.put("answer", Utils.putbyte(str));
				collectDataManager.update(values, "questionid=?",
						new String[] { query.get(i).getQuestionid() });
				CollectData collectData1 = query.get(i);
				collectData1.setAnswer(str);
				query.remove(i);
				query.add(i, collectData1);
				myAdpter.notifyDataSetInvalidated();
				break;
			case AlertDialog.BUTTON_NEUTRAL:// "变更"第三个按钮取消对话框

				if ("被动PDA".equalsIgnoreCase(taskname)) {

				} else if ("废弃地址".equalsIgnoreCase(taskname)) {
					str = "变更";
					values.put("answer", Utils.putbyte(str));
					collectDataManager.update(values, "questionid=?",
							new String[] { query.get(i).getQuestionid() });
					CollectData collectData2 = query.get(i);
					collectData2.setAnswer(str);
					query.remove(i);
					query.add(i, collectData2);
					myAdpter.notifyDataSetInvalidated();
					intent.setClass(DZActivity.this, CJActivity.class);
					edit.putString(Keys.SP_QUESTIONID, query.get(i)
							.getQuestionid());
					intent.putExtra("type", "ZHU");
					intent.putExtra("x", "10000000");
					intent.putExtra("y", "111111111111");
					startActivity(intent);
				} else if ("质检".equalsIgnoreCase(taskname)) {

				}

				break;
			default:
				break;
			}

		}
	};

	public void showPopupWindow(final View view) {

		View contentView = LayoutInflater.from(this).inflate(
				R.layout.popupwindow2, null);
		final PopupWindow popupWindow2 = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		ListView list = (ListView) contentView.findViewById(R.id.listView1_pp2);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				DZActivity.this, android.R.layout.simple_list_item_1);
		adapter.add(Keys.DZ_HS);
		adapter.add(Keys.DZ_DW);

		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (adapter.getItem(arg2) == Keys.DZ_HS) {
					if ("被动PDA".equalsIgnoreCase(taskname)) {
						AlertDialog dialog = new AlertDialog.Builder(
								DZActivity.this).create();
						dialog.setTitle("核实");
						dialog.setMessage(query.get(i).getContent());
						dialog.setButton("核实有", listener);
						dialog.setButton2("核实无", listener);
						dialog.show();
					} else if ("废弃地址".equalsIgnoreCase(taskname)) {
						AlertDialog dialog = new AlertDialog.Builder(
								DZActivity.this).create();
						dialog.setTitle("核实");
						dialog.setMessage(query.get(i).getContent());
						dialog.setButton("已废弃", listener);
						dialog.setButton2("仍保留", listener);
						dialog.setButton3("变更", listener);
						dialog.show();
					} else if ("质检".equalsIgnoreCase(taskname)) {

					}

				} else if (adapter.getItem(arg2) == Keys.DZ_DW) {

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
		getMenuInflater().inflate(R.menu.dz, menu);
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
				view = (LinearLayout) LayoutInflater.from(DZActivity.this)
						.inflate(R.layout.list_item_dz, null);
			} else {
				view = (LinearLayout) convertView;
			}
			TextView text1 = (TextView) view
					.findViewById(R.id.textView1_list_item_dz);
			TextView text2 = (TextView) view
					.findViewById(R.id.textView2_list_item_dz);
			text1.setText(query.get(position).getContent());
			text2.setText(query.get(position).getAnswer());
			if (selectItem == position) {
				view.setBackgroundColor(Color.RED);
				text1.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
				text2.setBackgroundResource(R.drawable.bj_border_blanchedalmond);
			} else {
				view.setBackgroundColor(Color.TRANSPARENT);
				text1.setBackgroundResource(R.drawable.bj_border_ivory);
				text2.setBackgroundResource(R.drawable.bj_border_ivory);
			}
			return view;
		}

		public void setSelectItem(int selectItem) {
			this.selectItem = selectItem;
		}

		private int selectItem = -1;
	}
}
