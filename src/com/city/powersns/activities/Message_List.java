package com.city.powersns.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.powersns.util.Dialog;
import com.city.powersns.util.Dialogmess.DialogListener;
import com.city.powersns.util.Dialogmess;
import com.city.pwersns.servers.Friends_List_services;
import com.city.pwersns.servers.Friends_add_servers;
import com.city.pwersns.servers.Friends_add_servers.Friends_add_server;
import com.city.pwersns.servers.Message_List_services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Message_List extends Activity {
	ListView fridens_list;
	ImageButton back, creation;
	Button but_delect,but_message;
	String uid = "";
	SimpleAdapter adapter;
    String flag="";
    public	TextView create_friends_uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.message);
		init();
		uid = PowerSNSManager.getInstance().getUserid();

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Message_List.this, Friends_List.class);
				Message_List.this.startActivity(intent);

			}
		});	
	
		list_View();
		fridens_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				create_friends_uid = (TextView) arg1.findViewById(R.id.textView_friends_name_apply);	
				DialogListener listener = new DialogListener() {
					
					@Override
					public void getText(String string) {
						flag=string;
						try {
							String resu = new Message_List_services().new message_add_server().execute(uid,create_friends_uid.getText().toString(),string).get();
							list_View();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				};
				Dialogmess dialog = new Dialogmess(Message_List.this, listener);
		
				dialog.show();
				
				}
								
		});
				
	}
	
	// 返回一个List
	private List<Map<String, String>> get_Date_List(String uid2) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			SoapObject result = new Message_List_services().execute(uid2).get();
System.out.println("result"+result);
System.out.println("长度"+result.getPropertyCount());
System.out.println("message"+result.getProperty(0));
			SoapObject detail = (SoapObject) result
					.getProperty("GetFriendRequestListResult");
			System.out.println(detail.getProperty(0).toString().split("\n"));
            int length = detail.getProperty(0).toString().split("\n").length;
			String tr[];
		    String []br;
System.out.println(detail.getProperty(0).toString().split("\n").length);
			// 字符串拆
			for (int i = 0; i < length; i++) {
			
				Map<String, String> maps = new HashMap<String, String>();

				String dte = detail.getProperty(0).toString();
				String dt2 = detail.getProperty(1).toString();
				tr = dte.split("\n");// 用换行符进行昵称和名字的拆分

				br = dt2.split("\n");
				if(dte.equals("anyType{}")){
					maps.put("Friends_name", tr[i]);
					maps.put("Friends_uid", br[i]);
				}else{
				maps.put("Friends_name", tr[i]);
				maps.put("Friends_uid", br[i]);

				list.add(maps);
			}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// ListView
	private void list_View() {
		adapter = new SimpleAdapter(Message_List.this, get_Date_List(uid),
				R.layout.message_item, new String[] { "Friends_name",
						"Friends_uid" }, new int[] {
						R.id.textView_friends_name_apply, R.id.textView_friends_uid_apply });
		fridens_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	private void init() {
		fridens_list = (ListView) this
				.findViewById(R.id.listView_message);
		//but_delect = (Button) this.findViewById(R.id.button_friends_delect);
		back = (ImageButton) this.findViewById(R.id.imageButton_back_friends_apply);
		
	}

}
