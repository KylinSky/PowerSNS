package com.city.powersns.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.Friends_List_services;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Friends_List extends Activity {
	ListView fridens_list;
	ImageButton back, creation;
	Button but_delect,but_message;
	String uid = "";
	SimpleAdapter adapter;
	String tr[];
	Button but_delect_friends;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_list);
		init();
		uid = PowerSNSManager.getInstance().getUserid();

		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Friends_List.this, Personal_info.class);
				Friends_List.this.startActivity(intent);

			}
		});

		creation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Friends_List.this, Fridnds_add.class);
				Friends_List.this.startActivity(intent);

			}
		});

		but_message.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(Friends_List.this, Message_List.class);
				Friends_List.this.startActivity(intent);

				
			}
		});
		list_View();
		fridens_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				 but_delect_friends = (Button) arg1.findViewById(R.id.button_friends_delect);
				final TextView friends_uid = (TextView) arg1.findViewById(R.id.textView_friends_name);
				
			but_delect_friends.setOnClickListener(new OnClickListener() {		
                @Override
					public void onClick(View arg0) {				
						 
				try {
				String	result = new Friends_List_services().new Friends_delect_services().execute(uid,friends_uid.getText().toString()).get();
				if(result.equals("OK")){
					Toast.makeText(Friends_List.this, "É¾³ý³É¹¦", Toast.LENGTH_SHORT).show();
					list_View();
				}else{
					Toast.makeText(Friends_List.this, "É¾³ýÊ§°Ü", Toast.LENGTH_SHORT).show();
					
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
		
		
		
		
		});
				
	}

	// ·µ»ØÒ»¸öList
	private List<Map<String, String>> get_Date_List(String uid2) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			SoapObject result = new Friends_List_services().execute(uid2).get();
			SoapObject detail = (SoapObject) result
					.getProperty("GetFriendListResult");
			System.out.println(detail.getProperty(0).toString().split("\n"));
            int length = detail.getProperty(0).toString().split("\n").length;
		
		    String []br;
System.out.println(detail.getProperty(0).toString().split("\n").length);
			// ×Ö·û´®²ð
			for (int i = 0; i < length; i++) {
			
				Map<String, String> maps = new HashMap<String, String>();

				String dte = detail.getProperty(0).toString();
				String dt2 = detail.getProperty(1).toString();
		
				tr = dte.split("\n");// ÓÃ»»ÐÐ·û½øÐÐêÇ³ÆºÍÃû×ÖµÄ²ð·Ö

				br = dt2.split("\n");
				if(dte.equals("anyType{}")){
					
					maps.put("Friends_name","êÇ³Æ£º"+  tr[i]);
					maps.put("Friends_uid","ÕËºÅ£º"+ br[i]);	
					//but_delect_friends.setVisibility(View.GONE);
				}else{					
					maps.put("Friends_name","êÇ³Æ£º"+ tr[i]);
					maps.put("Friends_uid","ÕËºÅ£º"+ br[i]);

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
		adapter = new SimpleAdapter(Friends_List.this, get_Date_List(uid),
				R.layout.friends_list_style, new String[] { "Friends_name",
						"Friends_uid" }, new int[] {
						R.id.textView_friends_name, R.id.textView_friends_uid });
		fridens_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	private void init() {
		fridens_list = (ListView) this
				.findViewById(R.id.listView1_friends_list);
		//but_delect = (Button) this.findViewById(R.id.button_friends_delect);
		back = (ImageButton) this.findViewById(R.id.imageButton_back_friends);
		creation = (ImageButton) this
				.findViewById(R.id.imageButton_add_friends);
		but_message = (Button) this.findViewById(R.id.button_message);
	}

}
