package com.towery.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poi.R;
import com.towery.beans.CollectData;
import com.towery.beans.MediaData;
import com.towery.manager.CollectDataManager;
import com.towery.manager.MediadataManager;
import com.towery.utils.Keys;
import com.towery.utils.Utils;

public class CJActivity extends Activity {

	private ArrayList<Bitmap> arrayList1;
	private ArrayList<String> arrayList2;
	private ListView listview;
	private MyAdapter adapter;
	private Button but1;
	private SharedPreferences sharedPreferences;
	private String SDfile;
	private Intent intent;
	private String extrax;
	private String extray;
	private Button but2;
	private EditText edit;
	private String extraGPSx;
	private String extraGPSy;
	private MediadataManager mediadataManager;
	private CollectDataManager collectDataManager;
	private String taskid;
	private String questionid;
	private String type;
	private String temporary = "temporary";// 临时文件夹名

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cj);
		init();
		initframe();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		listview.setAdapter(adapter);
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
			isExit.setButton("退出保存", listener);
			isExit.setButton2("退出不保存", listener);
			isExit.setButton3("取消", listener);
			// 显示对话框
			isExit.show();

		}

		return false;

	}

	/** 监听对话框里面的button点击事件 */
	DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "退出保存"按钮退出程序
				finishSql();
				finish();
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "退出不保存"第二个按钮取消对话框
				File file = new File(SDfile + temporary);
				delete(file);
				finish();
				break;
			case AlertDialog.BUTTON_NEUTRAL:// "取消"第三个按钮取消对话框
				break;
			default:
				break;
			}
		}
	};

	public void init() {

		but1 = (Button) findViewById(R.id.button1_cj);
		but2 = (Button) findViewById(R.id.button2_cj);
		edit = (EditText) findViewById(R.id.editText1_cj);
		listview = (ListView) findViewById(R.id.listView1_cj);
		intent = getIntent();
		extrax = intent.getStringExtra("x");
		extray = intent.getStringExtra("y");
		type = intent.getStringExtra("type");

		sharedPreferences = CJActivity.this.getSharedPreferences(Keys.SP,
				MODE_PRIVATE);
		taskid = sharedPreferences.getString(Keys.SP_TASKID, "");
		questionid = sharedPreferences.getString(Keys.SP_QUESTIONID, "");

		mediadataManager = new MediadataManager(CJActivity.this);
		collectDataManager = new CollectDataManager(CJActivity.this);
		arrayList1 = new ArrayList<Bitmap>();
		arrayList2 = new ArrayList<String>();
		SDfile = Keys.SDURL + "POI/photos/" + taskid + "/" + questionid + "/";
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			Log.v("TestFile", "SD card is not avaiable/writeable right now.");
			return;
		}
		adapter = new MyAdapter();
		if (type.equalsIgnoreCase("ZHU")) {
			extraGPSx = intent.getStringExtra("GPSx");
			extraGPSx = intent.getStringExtra("GPSY");
			String fileName = SDfile;
			File file = new File(fileName);

			if (!file.exists()) {
				file.mkdirs();
			}

		} else if (type.equalsIgnoreCase("BJ")) {
			edit.setText(intent.getStringExtra("remark"));
			List<MediaData> query = mediadataManager.query("questionid",
					questionid);
			if (query != null) {
				for (int i = 0; i < query.size(); i++) {
					arrayList2.add(query.get(i).getFilename().split("\\.")[0]);
				}

				String fileName = SDfile;
				File file = new File(fileName);

				if (!file.exists()) {
					file.mkdirs();
				} else {
					for (int i = 0; i < arrayList2.size(); i++) {
						File f = new File(SDfile + arrayList2.get(i));
						if (f.exists()) {
							Bitmap bm = BitmapFactory.decodeFile(f.toString());
							arrayList1.add(bm);
						} else {
							InputStream is;
							try {
								is = getResources().getAssets().open("f.jpg");
								BitmapDrawable drawable = new BitmapDrawable(is);
								arrayList1.add(drawable.getBitmap());
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					listview.setAdapter(adapter);// 初始化照片列表
				}

			}

		}

	}

	// 事件操作
	public void initframe() {
		// 牌照按鈕
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
			}
		});
		// 完成
		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishSql();
				finish();
			}
		});
		// 照片列表点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//
				// arg1.setBackground(getResources().getDrawable(
				// R.drawable.t0127c45a8e3188f139));
				//
				// showPopupWindow(arg0, arg2);
			}
		});
	}

	// 获取相机返回照片
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {

			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
				Log.v("TestFile",
						"SD card is not avaiable/writeable right now.");
				return;
			}

			Bundle bundle = data.getExtras();
			Bitmap ybitmap = (Bitmap) bundle.get("data");// ��ȡ���ص���ݣ���ת��ΪBitmapͼƬ��ʽ
			Bitmap bitmap = compressImage(ybitmap);
			FileOutputStream b = null;
			String name = imageName();
			String fileName = SDfile + temporary + "/" + name;
			File file = new File(SDfile + temporary);
			if (!file.exists()) {
				file.mkdirs();
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// �����д���ļ�
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} else {
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// �����д���ļ�
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			arrayList1.add(bitmap);
			arrayList2.add(name);
			listview.setAdapter(adapter);// 刷新照片列表
		}

	}

	// 点击图片列表显示的弹出框
	private void showPopupWindow(View view, final int id) {
		View contentView = LayoutInflater.from(CJActivity.this).inflate(
				R.layout.popupwindow_cj, null);
		TextView text = (TextView) contentView
				.findViewById(R.id.textView1_pp_cj);
		Button but1 = (Button) contentView.findViewById(R.id.button1_pp_cj);
		Button but2 = (Button) contentView.findViewById(R.id.button2_pp_cj);

		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

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
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bg_popwindow));
		popupWindow.setWidth(420);
		popupWindow.showAsDropDown(view, 100, 0);
		// String str[] = arrayList2.get(id).split("/");
		text.setText(arrayList2.get(id) + "jpg");
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 调用系统工具查看图片
				String viewAction = "android.intent.action.VIEW";
				String uri;
				File file = new File(SDfile + temporary + "/"
						+ arrayList2.get(id));
				if (file.exists()) {
					uri = "file://" + SDfile + temporary + "/"
							+ arrayList2.get(id);
				} else {
					uri = "file://" + SDfile + arrayList2.get(id);
				}
				Uri picUri = Uri.parse(uri);
				Intent lookPic = new Intent();
				lookPic.setAction(viewAction);
				lookPic.setDataAndType(picUri, "image/*");
				lookPic.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(lookPic);
			}
		});
		but2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 删除图片
				String path = SDfile + temporary + "/" + arrayList2.get(id);
				File file = new File(path);
				if (file.exists()) {
					file.delete();
				}
				arrayList1.remove(id);
				arrayList2.remove(id);
				listview.setAdapter(adapter);
				popupWindow.dismiss();
			}
		});
	}

	// 图片命名方法
	public String imageName() {
		String name;
		int intname;
		if (arrayList2.size() == 0) {
			name = "001";
		} else {
			intname = Integer.parseInt(arrayList2.get(arrayList2.size() - 1)) + 1;
			if (intname < 10) {
				name = "00" + intname;
			} else if (intname < 100) {
				name = "0" + intname;
			} else {
				name = String.valueOf(intname);
			}
		}

		return name;
	}

	public void finishSql() {

		if (type.equalsIgnoreCase("ZHU")) {
			// 数据库存储
			ArrayList<CollectData> arrayListcollec = new ArrayList<CollectData>();
			CollectData collectData = new CollectData();
			collectData.setTaskid(sharedPreferences.getString(Keys.SP_TASKID,
					""));
			collectData.setQuestionid(sharedPreferences.getString(
					Keys.SP_QUESTIONID, ""));
			collectData.setX(extrax);
			collectData.setY(extray);
			collectData.setGPSx(extraGPSx);
			collectData.setGPSy(extraGPSy);
			collectData.setRemark(edit.getText().toString());
			arrayListcollec.add(collectData);

			collectDataManager.insert(arrayListcollec);
			ArrayList<MediaData> arrayListmedia = new ArrayList<MediaData>();
			for (int i = 0; i < arrayList2.size(); i++) {
				MediaData mediaData = new MediaData();
				mediaData.setTaskid(sharedPreferences.getString(Keys.SP_TASKID,
						""));
				mediaData.setQuestionid(sharedPreferences.getString(
						Keys.SP_QUESTIONID, ""));
				mediaData.setFilename(arrayList2.get(i) + ".jpg");
				mediaData.setFilepath(SDfile.split("POI/")[1]);
				arrayListmedia.add(mediaData);
			}
			mediadataManager.insert(arrayListmedia);
			// 图片文件存储
			for (int i = 0; i < arrayList1.size(); i++) {
				FileOutputStream b = null;
				String fileName = SDfile + arrayList2.get(i);
				File file = new File(SDfile);
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					b = new FileOutputStream(fileName);
					arrayList1.get(i).compress(Bitmap.CompressFormat.JPEG, 100,
							b);// �����д���ļ�
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else if (type.equalsIgnoreCase("BJ")) {
			// 图片文件存储
			for (int i = 0; i < arrayList1.size(); i++) {
				FileOutputStream b = null;
				String fileName = SDfile + arrayList2.get(i);
				File file = new File(SDfile);
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					b = new FileOutputStream(fileName);
					arrayList1.get(i).compress(Bitmap.CompressFormat.JPEG, 100,
							b);// �����д���ļ�
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// 数据库跟新
			ContentValues values = new ContentValues();
			values.put("remark", Utils.putbyte(edit.getText().toString()));
			collectDataManager.update(values, "questionid=?",
					new String[] { questionid });
			mediadataManager.delete("questionid", new String[] { questionid });
			ArrayList<MediaData> arrayListmedia = new ArrayList<MediaData>();
			for (int i = 0; i < arrayList2.size(); i++) {
				MediaData mediaData = new MediaData();
				mediaData.setTaskid(sharedPreferences.getString(Keys.SP_TASKID,
						""));
				mediaData.setQuestionid(sharedPreferences.getString(
						Keys.SP_QUESTIONID, ""));
				mediaData.setFilename(arrayList2.get(i) + ".jpg");
				mediaData.setFilepath(SDfile.split("POI/")[1]);
				arrayListmedia.add(mediaData);
			}
			mediadataManager.insert(arrayListmedia);
		}
		File file = new File(SDfile + temporary);
		delete(file);
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

	// 压缩图片

	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ�������ݴ�ŵ�baos��
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ�������ݴ�ŵ�baos��
			options -= 10;// ÿ�ζ�����10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ��������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream������ͼƬ
		return bitmap;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cj, menu);
		return true;
	}

	// 自定义图片adapter
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList1.size();
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
			LinearLayout view;
			if (convertView == null) {
				view = (LinearLayout) LayoutInflater.from(CJActivity.this)
						.inflate(R.layout.list_item_cj, null);
			} else {
				view = (LinearLayout) convertView;
			}
			ImageView img = (ImageView) view.findViewById(R.id.imageView1_licj);
			TextView text = (TextView) view.findViewById(R.id.textView1_licj);
			img.setImageBitmap(arrayList1.get(position));
			text.setText(arrayList2.get(position) + ".jpg");
			view.findViewById(R.id.button1_item_cj).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							// TODO Auto-generated method stub
							// 调用系统工具查看图片
							String viewAction = "android.intent.action.VIEW";
							String uri;
							File file = new File(SDfile + temporary + "/"
									+ arrayList2.get(position));
							if (file.exists()) {
								uri = "file://" + SDfile + temporary + "/"
										+ arrayList2.get(position);
							} else {
								uri = "file://" + SDfile
										+ arrayList2.get(position);
							}
							Uri picUri = Uri.parse(uri);
							Intent lookPic = new Intent();
							lookPic.setAction(viewAction);
							lookPic.setDataAndType(picUri, "image/*");
							lookPic.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
									| Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(lookPic);

						}
					});
			view.findViewById(R.id.button2_item_cj).setOnClickListener(
					new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							String path = SDfile + temporary + "/"
									+ arrayList2.get(position);
							File file = new File(path);
							if (file.exists()) {
								file.delete();
							}
							arrayList1.remove(position);
							arrayList2.remove(position);
							// listview.setAdapter(adapter);
							onStart();
						}
					});
			return view;
		}
	}

}
