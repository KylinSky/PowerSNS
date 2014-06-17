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

public class My_Album_add extends Activity {
	ImageButton back;
	Button but_back, but_save;
	EditText create_album_name;
	String uid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.album_add);
		super.onCreate(savedInstanceState);
		uid = PowerSNSManager.getInstance().getUserid();
		init();
		// ���ذ�ť
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(My_Album_add.this, My_Album.class);
				My_Album_add.this.startActivity(intent);

			}
		});
		// ���ذ�ť
		but_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(My_Album_add.this, My_Album.class);
				My_Album_add.this.startActivity(intent);

			}
		});

		// �������,��ӵ�ListView��
		but_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					String result = new My_Album_add_servers().execute(uid,
							create_album_name.getText().toString()).get();
System.out.println("���+result"+result);					
					if(result.equals("SUCCESS..")){
				Toast.makeText(My_Album_add.this, "�������ɹ�", Toast.LENGTH_SHORT).show();		
						Intent intent = new Intent();
						intent.setClass(My_Album_add.this, My_Album.class);
						My_Album_add.this.startActivity(intent);
					
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

	}

	private void init() {
		back = (ImageButton) this.findViewById(R.id.imageButton_back);
		// �����������������
		// creation = (ImageButton) this.findViewById(R.id.imageButton_back);

		but_back = (Button) this.findViewById(R.id.button_back);
		but_save = (Button) this.findViewById(R.id.button_save);
		create_album_name = (EditText) this
				.findViewById(R.id.editText_create_album_name);// �����
	}
}
