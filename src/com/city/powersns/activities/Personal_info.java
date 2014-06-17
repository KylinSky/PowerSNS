package com.city.powersns.activities;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.powersna.domain.User;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;
import com.city.pwersns.servers.Personal_info_servers;
import com.city.pwersns.servers.Reqister_servers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Personal_info extends Activity {
	ImageView imageview_hand, imageview_sex;
	Button but_updat, but_updatps,my_picture,list_frient;
	User users;
	TextView nickname, mood;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_center);
		init();
		// 接收Login Intent传过来的User对象
		String uid = PowerSNSManager.getInstance().getUserid();
		update(uid);

		but_updat.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到 Perfect_info页面
				Intent intent = new Intent();
				intent.setClass(Personal_info.this, Perfect_info.class);
				Personal_info.this.startActivity(intent);

			}
		});

		my_picture.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			 Intent intent = new Intent();
			 intent.setClass(Personal_info.this, My_Album.class);
			 Personal_info.this.startActivity(intent);
			}
		});
		list_frient.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent();
				 intent.setClass(Personal_info.this, Friends_List.class);
				 Personal_info.this.startActivity(intent);
				
			}
		});
	
	
	}

	// 控件的实例化
	public void init() {
		but_updat = (Button) this.findViewById(R.id.edit_ziliao);
		but_updatps = (Button) this.findViewById(R.id.edit_psd);
		nickname = (TextView) this.findViewById(R.id.textView1);
		mood = (TextView) this.findViewById(R.id.textView3);
		imageview_hand = (ImageView) this.findViewById(R.id.imageView1);
		imageview_sex = (ImageView) this.findViewById(R.id.imageView_sex);
		my_picture = (Button) this.findViewById(R.id.my_picture);
		list_frient = (Button) this.findViewById(R.id.list_frient);
	}

	// 更新用户信息
	public void update(String user) {
		try {
			SoapObject result = new Personal_info_servers().execute(user).get();
			SoapObject detail = (SoapObject) result
					.getProperty("GetUserInfoByIdResult");
			String[] userinfo = new String[7];
			userinfo[0] = detail.getProperty(0).toString();// UID
			userinfo[1] = detail.getProperty(1).toString();// NICK_NAME
			userinfo[2] = detail.getProperty(2).toString();// password
			userinfo[3] = detail.getProperty(3).toString(); // 心情
			userinfo[4] = detail.getProperty(4).toString();// 年龄
			// 拿到用户性别
			userinfo[5] = detail.getProperty(5).toString();// SEX
			userinfo[6] = detail.getProperty(6).toString();// photo_path
			System.out.println(userinfo[6] + "ttt");
			if (!userinfo[6].equals("anyType{}")) {
				imageview_hand.setImageBitmap(new SOAPUtils().new getimg()
						.execute(Urls.url + "/" + userinfo[6]).get());
			}

			for (int i = 0; i < userinfo.length; i++) {
				if (userinfo[i].equals("anyType{}")) {
					userinfo[i] = "暂无此信息";
				}
				System.out.println(userinfo[i] + "userinfo");

			}
			String sex = userinfo[5];
			if (sex.equals("男")) {
				imageview_sex.setImageResource(R.drawable.ic_male);
			} else {
				imageview_sex.setImageResource(R.drawable.ic_female);
			}

			// 問題
			/*
			 * switch (sex) { case "男":
			 * imageview_sex.setImageResource(R.drawable.ic_male); break; case
			 * "女": imageview_sex.setImageResource(R.drawable.ic_female); break;
			 * default: imageview_sex.setImageResource(R.drawable.ic_female);
			 * break; }
			 */

			nickname.setText(userinfo[1]);
			mood.setText(userinfo[3]);

			// System.out.println(result.getProperty(0).toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
