package com.city.powersns.activities;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.city.powersns.util.Dialog;
import com.city.powersns.util.Dialog.DialogListener;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;
import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.Friends_add_servers;
import com.city.pwersns.servers.My_Album_add_servers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Fridnds_add extends Activity {
	ImageButton back,add;
	Button but_back, but_save;
public	EditText create_friends_uid;
public	String uid;
String result="";
public String flag="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.friends_add);
		super.onCreate(savedInstanceState);
		uid = PowerSNSManager.getInstance().getUserid();
		init();
		// ���ذ�ť
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Fridnds_add.this, Friends_List.class);
				Fridnds_add.this.startActivity(intent);

			}
		});
		// ���ذ�ť
		but_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Fridnds_add.this, Friends_List.class);
				Fridnds_add.this.startActivity(intent);

			}
		});

	
		
		
		// ����,��ӵ�ListView��
		but_save.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//new Dialog(Fridnds_add.this).show();
			
			try {
				result = new Friends_add_servers().execute(uid,create_friends_uid.getText().toString()).get();
System.out.println("��Ϣla"+result);					
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
												
                     if(result.equals("Y")){
						Toast.makeText(Fridnds_add.this, "��������ɹ�" ,Toast.LENGTH_SHORT).show();			
						   Intent intent = new Intent();
							intent.setClass(Fridnds_add.this, Friends_List.class);
							Fridnds_add.this.startActivity(intent);						
					}else if(result.equals("N")){
						Toast.makeText(Fridnds_add.this, "�û�������" ,Toast.LENGTH_SHORT).show();										
					}else if(result.equals("E")){
						Toast.makeText(Fridnds_add.this, "�Է����������" ,Toast.LENGTH_SHORT).show();										
					}else if(result.equals("R")){
					Toast.makeText(Fridnds_add.this, "���������ѷ���,��ȴ���Ӧ" ,Toast.LENGTH_SHORT).show();	
						
						
					}else if(result.equals("A")){
						DialogListener listener = new DialogListener() {
							
							@Override
							public void getText(String string) {
								flag = string;
						try {
							String resu = new Friends_add_servers().new Friends_add_server().execute(uid,create_friends_uid.getText().toString(),string).get();
							if(resu.equals("Y")){
								Toast.makeText(Fridnds_add.this, "��Ӻ��ѳɹ�",Toast.LENGTH_SHORT);
								Intent intent = new Intent();
								intent.setClass(Fridnds_add.this, Friends_List.class);
								Fridnds_add.this.startActivity(intent);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
								
							}
						}; 
						Dialog dialog =	new Dialog(Fridnds_add.this,listener);
						dialog.show();
     
					}
			
			
			}

			private Object Friends_add_serverss(String uid, String string) {
				// TODO Auto-generated method stub
				return null;
			}
		});

	}

	private void init() {
		back = (ImageButton) this.findViewById(R.id.imageButton_friends_back);
		// �����������������
		// creation = (ImageButton) this.findViewById(R.id.imageButton_back);
		add = (ImageButton) this.findViewById(R.id.imageButton_fridends_apply);
		but_back = (Button) this.findViewById(R.id.button_fridens_back);
		but_save = (Button) this.findViewById(R.id.button_fridens_apply);
		create_friends_uid = (EditText) this
				.findViewById(R.id.editText_friends_name);// �����
	}


}
