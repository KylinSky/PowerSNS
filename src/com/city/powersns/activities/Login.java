package com.city.powersns.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.city.powersna.domain.PowerSNSManager;
import com.city.powersna.domain.User;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;
import com.city.pwersns.servers.Login_servers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	EditText et1, et2;
	Button but, but2;
	String result = "";
	CheckBox checkbox;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et1 = (EditText) this.findViewById(R.id.login_userName);
		et2 = (EditText) this.findViewById(R.id.login_psd);
		but = (Button) this.findViewById(R.id.btn_login);
		but2 = (Button) this.findViewById(R.id.btn_register);

		// 记住密码
		checkbox = (CheckBox) this.findViewById(R.id.RemeberPsd);
		checkbox.setChecked(true);
		sp = Login.this.getSharedPreferences("User", Login.this.MODE_APPEND);
		et1.setText(sp.getString("UID", ""));
		et2.setText(sp.getString("password", ""));
		// 登陆验证

		but.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
					// 拿到返回值
					String tt = new Login_servers().execute(
							et1.getText().toString(), et2.getText().toString())
							.get();
					// 返回值的验证，和页面的跳转
					// 测试代码
					if (tt.equals("true")) {
						if (checkbox.isChecked()) {

							Editor editor = sp.edit();
							editor.putString("UID", et1.getText().toString());
							editor.putString("password", et2.getText()
									.toString());
							editor.commit();
						}

						Intent intent = new Intent();
						intent.setClass(Login.this, Personal_info.class);
						Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT)
								.show();
						PowerSNSManager.getInstance().setUserid(
								et1.getText().toString());

						Login.this.startActivity(intent);

					} else {

						Toast.makeText(Login.this, "登陆失败", Toast.LENGTH_SHORT)
								.show();
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});

		but2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(Login.this, Reqister.class);
				Login.this.startActivity(intent);

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
