package com.city.powersns.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.powersna.domain.User;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;
import com.city.powersns.util.SOAPUtils.getimg;
import com.city.pwersns.servers.Perfect_info_servers;
import com.city.pwersns.servers.Personal_info_servers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Perfect_info extends Activity {
	EditText nick_name, age, mood;
	RadioButton boy, girl;
	Button determine, cancel;
	ImageButton canera, upload, file;
	ImageView Hand_image;
	RadioGroup group;
	Bitmap bitmap1;
	String picPath = "";
	String dates = "";
	String uid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perfect_info);
		init();
		 uid = PowerSNSManager.getInstance().getUserid();
		update(uid);
		canera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 重另外一个类启动相机和获得图片，不成功...
				/*
				 * Perfect_info_servers perfect = new
				 * Perfect_info_servers(Perfect_info.this); perfect.pci();
				 */

				picture pc = new picture();
				pc.pci();
			}
		});

		upload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new Perfect_info_servers().new Dateuplod().execute(SOAPUtils
						.getBase64(new File(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath()
								+ "/myImage/" + dates + ".jpg")), dates
						+ ".jpg", uid);

			}
		});
		file.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				picture pc = new picture();
				pc.Picturt_List();
			}
		});
		// 提交修改信息
		determine.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String str = "";
				if (boy.isChecked())
					str = "男";
				else if (girl.isChecked())
					str = "女";
				new Perfect_info_servers().new update_User().execute(uid
						, nick_name.getText().toString(), age
						.getText().toString(), str,
						mood.getText().toString());
				Toast.makeText(Perfect_info.this, "修改成功", Toast.LENGTH_SHORT)
						.show();
				Intent itent1 = new Intent();
				itent1.setClass(Perfect_info.this, Personal_info.class);
				Perfect_info.this.startActivity(itent1);
				
				Perfect_info.this.finish();
			}
		});
		// 取消返回到个人中心界面
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent itent2 = new Intent();
				itent2.setClass(Perfect_info.this, Personal_info.class);
				Perfect_info.this.startActivity(itent2);
				Perfect_info.this.finish();
				
			}
		});

	}

	// 显示用户信息
	public void update(String user) {
		try {
			SoapObject result = new Personal_info_servers().execute(
					user).get();
			SoapObject detail = (SoapObject) result
					.getProperty("GetUserInfoByIdResult");
			String[] userinfo = new String[7];
			userinfo[0] = detail.getProperty(0).toString();
			userinfo[1] = detail.getProperty(1).toString();
			userinfo[3] = detail.getProperty(3).toString();
			userinfo[4] = detail.getProperty(4).toString();
			// 拿到用户性别
			userinfo[5] = detail.getProperty(5).toString();
			userinfo[6] = detail.getProperty(6).toString();
			if (!userinfo[6].equals("anyType{}")) {
				Hand_image.setImageBitmap(new SOAPUtils().new getimg().execute(
						Urls.url + "/" + userinfo[6]).get());
			}
			nick_name.setText(userinfo[1]);
			age.setText(userinfo[4]);
			mood.setText(userinfo[3]);
			if (userinfo[5].equals("男")) {
				boy.setChecked(true);
			} else if (userinfo[5].equals("女")) {
				girl.setChecked(true);
			} else {
				boy.setChecked(false);
				girl.setChecked(false);
			}

			// System.out.println(result.getProperty(0).toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 控件实例化
	public void init() {
		nick_name = (EditText) this.findViewById(R.id.editText_nickname);
		age = (EditText) this.findViewById(R.id.editText_age);
		mood = (EditText) this.findViewById(R.id.editText_mood);

		boy = (RadioButton) this.findViewById(R.id.radioButton_boy);
		girl = (RadioButton) this.findViewById(R.id.radioButton_girl);
		group = (RadioGroup) this.findViewById(R.id.radioGroup1);

		determine = (Button) this.findViewById(R.id.button_determine);
		cancel = (Button) this.findViewById(R.id.button_cancel);

		canera = (ImageButton) this.findViewById(R.id.imageButton_camera);
		upload = (ImageButton) this.findViewById(R.id.imageButton_upload);
		file = (ImageButton) this.findViewById(R.id.imageButton_file);
		Hand_image = (ImageView) this.findViewById(R.id.imageView_hand);
	}

	class picture extends Handler {
		public void pci() {
			Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			i.putExtra(MediaStore.EXTRA_OUTPUT, Environment
					.getExternalStorageDirectory().getAbsolutePath());
			i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			Perfect_info.this.startActivityForResult(i, 0);

		}

		public void Picturt_List() {
			Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
			openAlbumIntent.setType("image/*");
			startActivityForResult(openAlbumIntent, 1);

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 0) {
			boolean sdStatus = Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED);
			System.out.println("SD卡是否存在" + sdStatus);
			/*
			 * System.out.println(
			 * Environment.getExternalStorageDirectory().getAbsolutePath
			 * ()+"tttttttttt");
			 */
			bitmap1 = (Bitmap) data.getExtras().get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			FileOutputStream b = null;
			SimpleDateFormat si = new SimpleDateFormat("HH.mm.ss");
			dates = si.format(new Date());

			File file = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/myImage/");

			if (!file.exists())
				file.mkdirs();

			String fileName = Environment.getExternalStorageDirectory()
					.getAbsolutePath() + "/myImage/" + dates + ".jpg";
			// System.out.println(file.exists());
			byte[] b2 = bitmap1.toString().getBytes();

			try {
				// fil
				b = new FileOutputStream(fileName);
				// 设置图片样式
				bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, b);
				b.write(b2);
				b.flush();
				b.close();
			} catch (FileNotFoundException e) {

				e.printStackTrace();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Hand_image.setImageBitmap(bitmap1);

		}

		ContentResolver resolver = getContentResolver();

		if (requestCode == 1) {
			Uri uri = data.getData();
			try {
				bitmap1 = MediaStore.Images.Media.getBitmap(resolver, uri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Hand_image.setImageBitmap(bitmap1);
			/*
			 * String[] pojo = { MediaStore.Images.Media.DATA };
			 * 
			 * @SuppressWarnings("deprecation") Cursor cursor
			 * =Perfect_info.this.managedQuery(uri, pojo, null, null, null); if
			 * (cursor != null) { ContentResolver cr
			 * =Perfect_info.this.getContentResolver(); int colunm_index =
			 * cursor .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			 * 
			 * cursor.moveToFirst(); String path =
			 * cursor.getString(colunm_index); if (path.endsWith("jpg") ||
			 * path.endsWith("png")) { picPath = path; try { bitmap1 =
			 * BitmapFactory.decodeStream(cr.openInputStream(uri)); } catch
			 * (FileNotFoundException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } Hand_image.setImageBitmap(bitmap1); } }
			 */

		}
	}

}
