package com.city.powersns.activities;

import java.util.concurrent.ExecutionException;

import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.My_Album_add_servers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Fridnds_apply extends Activity {
	ImageButton back;
	Button but_back, but_save;
	EditText create_album_name;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.friends_add);
		super.onCreate(savedInstanceState);
		uid = PowerSNSManager.getInstance().getUserid();
		init();
		// 返回按钮
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Fridnds_apply.this, Friends_List.class);
				Fridnds_apply.this.startActivity(intent);

			}
		});
		// 返回按钮
		but_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Fridnds_apply.this, Friends_List.class);
				Fridnds_apply.this.startActivity(intent);

			}
		});

		// 接收,添加到ListView中
		but_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
						Intent intent = new Intent();
						intent.setClass(Fridnds_apply.this, Friends_List.class);
						Fridnds_apply.this.startActivity(intent);
					
			
			}
		});

	}

	private void init() {
		back = (ImageButton) this.findViewById(R.id.imageButton_back);
		// 添加完相册无需在添加
		// creation = (ImageButton) this.findViewById(R.id.imageButton_back);

		but_back = (Button) this.findViewById(R.id.button_back);
		but_save = (Button) this.findViewById(R.id.button_save);
		create_album_name = (EditText) this
				.findViewById(R.id.editText_create_album_name);// 输入框
	}
}
