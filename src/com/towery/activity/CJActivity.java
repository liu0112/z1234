package com.towery.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.poi.R;
import com.towery.utils.Keys;

public class CJActivity extends Activity {

	private ArrayList<Bitmap> arrayList1;
	private ArrayList<String> arrayList2;
	private ListView listview;
	private MyAdapter adapter;
	private Button but1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cj);
        init();
        initframe();
	}

	public void init() {
		but1 = (Button) findViewById(R.id.button1_cj);
		listview = (ListView) findViewById(R.id.listView1_cj);
		arrayList1 = new ArrayList<Bitmap>();
		arrayList2 = new ArrayList<String>();
		adapter = new MyAdapter();
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // ���sd�Ƿ����
			Log.v("TestFile", "SD card is not avaiable/writeable right now.");
			return;
		}
		String fileName = Keys.SDURL + "myImage";
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("=============" + fileName);
			file.mkdir(); // �������򴴽�
			System.out.println(file.exists());
		} else {
			File[] files = file.listFiles();
			System.out.println(files.length);
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				arrayList2.add(f.toString());
				if (f.exists()) {// �����ļ�����
					Bitmap bm = BitmapFactory.decodeFile(f.toString());
					arrayList1.add(bm);
				}
			}
			listview.setAdapter(adapter);// 初始化照片列表
		}
	}
//事件操作
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

		// 照片列表点击事件
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				showPopupWindow(arg0, arg2);
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
			Long tsLong = System.currentTimeMillis() / 1000;
			String ts = tsLong.toString();
			String mame = "" + ts;
			String fileName = Keys.SDURL + "myImage/" + mame;
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

			// ((ImageView) findViewById(R.id.imageView1_cj))
			// .setImageBitmap(bitmap);// ��ͼƬ��ʾ��ImageView��

			arrayList1.add(bitmap);
			arrayList2.add(fileName);
			listview.setAdapter(adapter);// 刷新照片列表
		}

	}
//    点击图片列表显示的弹出框
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
				R.drawable.t0127c45a8e3188f139));
		popupWindow.setWidth(150);
		popupWindow.showAsDropDown(view, 100, 0);
		String str[] = arrayList2.get(id).split("/");
		text.setText(str[str.length - 1]);
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// 调用系统工具查看图片
				String viewAction = "android.intent.action.VIEW";
				Uri picUri = Uri.parse("file://" + arrayList2.get(id));
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
				File file = new File(arrayList2.get(id));
				file.delete();
				arrayList1.remove(id);
				arrayList2.remove(id);
				listview.setAdapter(adapter);
				popupWindow.dismiss();
			}
		});
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
		public View getView(int position, View convertView, ViewGroup parent) {
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
			text.setText(arrayList2.get(position));
			return view;
		}
	}

}
