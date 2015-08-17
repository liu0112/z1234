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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_cj);
		Button but1 = (Button) findViewById(R.id.button1_cj);
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
			}
		});
		arrayList1 = new ArrayList<Bitmap>();
		arrayList2 = new ArrayList<String>();
		listview = (ListView) findViewById(R.id.listView1_cj);
		adapter = new MyAdapter();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				showPopupWindow(arg0, arg2);
			}
		});
		String sdStatus = Environment.getExternalStorageState();
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
			Log.v("TestFile", "SD card is not avaiable/writeable right now.");
			return;
		}
		String fileName = Keys.SDURL + "myImage";
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("============="+fileName);
			file.mkdir(); // 如果不存在则创建
			System.out.println(file.exists());
		} else {
			File[] files = file.listFiles();
			System.out.println(files.length);
			for (int i = 0; i < files.length; i++) {
				File f = files[i];
				arrayList2.add(f.toString());
				if (f.exists()) {// 若该文件存在
					Bitmap bm = BitmapFactory.decodeFile(f.toString());
					arrayList1.add(bm);
				}
			}
			listview.setAdapter(adapter);
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {

			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.v("TestFile",
						"SD card is not avaiable/writeable right now.");
				return;
			}

			Bundle bundle = data.getExtras();
			Bitmap ybitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			Bitmap bitmap = compressImage(ybitmap);
			FileOutputStream b = null;
			Long tsLong = System.currentTimeMillis()/1000;
			String ts = tsLong.toString();
			String mame = "" + ts;
			String fileName = Keys.SDURL + "myImage/" + mame;
			try {
				b = new FileOutputStream(fileName);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
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
			// .setImageBitmap(bitmap);// 将图片显示在ImageView里

			arrayList1.add(bitmap);
			arrayList2.add(fileName);
			listview.setAdapter(adapter);
		}

	}

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
				// 这里如果返回true的话，touch事件将被拦截
				// 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
			}
		});
		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.t0127c45a8e3188f139));
		popupWindow.setWidth(150);
		popupWindow.showAsDropDown(view, 100, 0);
		String str[] = arrayList2.get(id).split("/");
		text.setText(str[str.length-1]);
		but1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
				File file = new File(arrayList2.get(id));
				file.delete();
				arrayList1.remove(id);
				arrayList2.remove(id);
				listview.setAdapter(adapter);
				popupWindow.dismiss();
			}
		});
	}

	// 图片质量压缩
	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cj, menu);
		return true;
	}

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
