package com.city.powersns.activities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.WebService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class my_diary extends Activity {
  ListView lv;
  SimpleAdapter adapter;
  LayoutInflater inflater;
  View view;
  TextView title,date,Id;
  EditText content;
  SoapObject result,results;
  Handler handler;
  ArrayList<Map<String, String>> listItem;
  ImageView bt_back_img;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.list_diary);
		super.onCreate(savedInstanceState);
		init();
		System.out.println("ddddddd22222222");
		new list_friendTask().execute();
		System.out.println("ddddddd1111111111");
		bt_back_img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(my_diary.this, Personal_info.class);
				my_diary.this.startActivity(intent);
				my_diary.this.finish();
			}
		});
	}
	
  public void init(){
	  lv=(ListView)findViewById(R.id.list_diary);
	  bt_back_img=(ImageView)this.findViewById(R.id.bt_back1);
	inflater=LayoutInflater.from(my_diary.this);
		 view=inflater.inflate(R.layout.my_dairy, null);
			view.setBackgroundResource(R.drawable.log_bg_loglist_lstview);
		 lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		 lv.setOnItemClickListener(new OnItemClickListener() {  
	            public void onItemClick(AdapterView<?> parent, View view,  
	                    int position, long id) {  
	            	Id=(TextView)view.findViewById(R.id.ID);
	            	title=(TextView)view.findViewById(R.id.title);
	           	 date=(TextView)view.findViewById(R.id.date);
	           	  content=(EditText)view.findViewById(R.id.content);
	           	  title.setTextColor(Color.RED);
	           	 date.setTextColor(Color.RED);
	           	content.setTextColor(Color.RED);
	            	Intent intent=new Intent();
	            	Bundle bundle=new Bundle();
	            	bundle.putString("log_id", Id.getText().toString());
	            	bundle.putString("log_date", date.getText().toString());
	            	intent.putExtras(bundle);
	            	intent.setClass(my_diary.this, log_manager.class);
	            	startActivity(intent);
	            	my_diary.this.finish();
	              
	            }  
	        });  
			
  }

  class list_friendTask extends AsyncTask<String, Integer,SoapObject>{
	
	@Override
	protected SoapObject doInBackground(String... params) {	
		// TODO Auto-generated method stub
		String url="http://192.168.189.1/tomService/service1.asmx";
		String method_name="GetDiaryList";
	   Map<String, String> param=new HashMap<String, String>();
		param.put("UID", PowerSNSManager.getInstance().getUserid());	
		try {
			result = WebService.getSoapObject(url, method_name, param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	
	}

	protected void onPostExecute(SoapObject result1) {
		// TODO Auto-generated method stub
		 super.onPostExecute(result1);
		 System.out.println(result1+"wwwwwwwwww");
			System.out.println(PowerSNSManager.getInstance().getUserid()+"wwwwwwwwww");
		SoapObject ret = (SoapObject)result1.getProperty("GetDiaryListResult");
		String Did = ret.getProperty(0).toString();
		String Dtitle = ret.getProperty(1).toString();
		String DDate = ret.getProperty(2).toString();
		String DContent=ret.getProperty(3).toString();
		String[] _Did=Did.split("\n");
		String[] _Dtitle=Dtitle.split("\n");
		String[] _Ddate=DDate.split("\n");
		String[] _DContent=DContent.split("\n");
		listItem = new ArrayList<Map<String, String>>();
      for(int i=0;i<_Did.length;i++){
    	  Map<String, String> map=new HashMap<String, String>();
    		map.put("Did", _Did[i]);
			map.put("Dtitle",_Dtitle[i]);
			map.put("DDate",_Ddate[i]);	
		map.put("DContent",_DContent[i]);
		listItem.add(map);
      }
      adapter=new SimpleAdapter(my_diary.this,
    		  listItem,
    		  R.layout.my_dairy,
    		 new String[]{"Dtitle","DDate","DContent","Did"},
    		 new int[]{R.id.title,R.id.date,R.id.content,R.id.ID});
      lv.setAdapter(adapter);
      adapter.notifyDataSetChanged();
	}
	
  } 
  
}
