package com.city.powersns.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.xmlpull.v1.XmlPullParserException;

import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.WebService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
 
public class log_manager extends Activity implements OnClickListener,OnTouchListener {
    EditText log_manager_title,log_manager_content,log_person,log_comment_content;
    TextView log_manager_date,log_manager_edit,log_manager_edit_success,
    log_manager_delte,log_manager_comment,user_Id;
     String id,log_date;
    SoapObject result,result4;
    String result1,result2,result3;
    ImageView log_manager_return,log_write;
    myhandle hd;
    ArrayList<Map<String, String>> Lists;
    Dialog dialog ;
    ListView lv,list_comment;
     SimpleAdapter adapter;
  
  View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_manager);
		init();
		Intent intent=getIntent();
		Bundle extra=intent.getExtras();
		 id=extra.getString("log_id");
		 log_date=extra.getString("log_date");
		 System.out.println(id+"ssssss");
		 new xianshiTask().execute();
		 new read_diary().start();
	
	}

	class myhandle extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what==1){
				System.out.println(result+"fffffffffff");
				//SoapObject ret = (SoapObject)result.getProperty("ReadDiaryResult");
				//System.out.println(ret+"fffffffffff11111111");
		    	String Dtitle = result.getProperty(0).toString();//读取属性
				String Dcontent = result.getProperty(1).toString();
				 log_manager_title.setText(Dtitle);
				 log_manager_content.setText(Dcontent);
				 log_manager_date.setText(log_date);			
			}
		}
		
	}
	//读取日志线程
	class read_diary extends Thread{
		
		@Override
		public void run() {
		
			Map<String, String> map=new HashMap<String, String>();
			map.put("did", id);
			result=WebService.callWebServiceWithParams2("http://192.168.189.1/tomService/service1.asmx",
								"ReadDiary", map);
				  Message msg=new Message();
				  msg.what=1;
				  hd.sendMessage(msg);
		}
		
	}
	//各个控件的识别
   @SuppressLint({ "CutPasteId", "HandlerLeak" })
   public void init(){
	   log_manager_title=(EditText)findViewById(R.id.log_manager_title);
	   log_manager_content=(EditText)findViewById(R.id.log_manager_content);
	   log_manager_date=(TextView)findViewById(R.id.log_manager_date);
	   log_manager_edit=(TextView)findViewById(R.id.log_manager_edit);
	   log_manager_edit_success=(TextView)findViewById(R.id.log_manager_edit_success);
	   log_manager_delte=(TextView)findViewById(R.id.log_manager_delete);
	   log_manager_comment=(TextView)findViewById(R.id.log_manager_comment);
	   list_comment=(ListView)findViewById(R.id.list_comment);
	  
	   LayoutInflater inflater=LayoutInflater.from(log_manager.this);
	   view=inflater.inflate(R.layout.log_comment, null,false);
	   log_person=(EditText)view.findViewById(R.id.log_person);
	   log_comment_content=(EditText)view.findViewById(R.id.zys);
	   hd=new myhandle();
	   log_manager_return=(ImageView)findViewById(R.id.log_manager_return);
	   log_write=(ImageView)findViewById(R.id.dd);
	   log_manager_edit.setOnClickListener(this);
	   log_manager_edit_success.setOnClickListener(this);
	   log_manager_delte.setOnClickListener(this);
	   log_write.setOnClickListener(this);
	   log_manager_comment.setOnClickListener(this);
	   log_manager_return.setOnClickListener(this);
	   log_manager_edit.setOnTouchListener(this);
	   log_manager_edit_success.setOnTouchListener(this);
	   log_manager_delte.setOnTouchListener(this);
	   log_manager_comment.setOnTouchListener(this);
	   lv=(ListView)findViewById(R.id.list_comment);
		log_manager_content.setFocusable(false);
   }
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	if(v==log_manager_edit){
		log_comment_content.requestFocusFromTouch();
		log_manager_content.setFocusableInTouchMode(true);

	}else if(v==log_manager_edit_success){
		log_manager_content.setFocusable(false);
		new log_manager_updateTask().execute();
		
		
	}else if(v==log_manager_return){
		Intent intent=new Intent(log_manager.this,my_diary.class);
		startActivity(intent);
		log_manager.this.finish();
	}else if(v==log_write){
		Intent intent=new Intent(log_manager.this,log_write_Acticity.class);
		 startActivity(intent);
		log_manager.this.finish();
	}
	else if(v==log_manager_delte){
		new deleteTask().execute();
		log_manager_title.setText("");
		log_manager_content.setText("");
		log_manager_title.setText("");
		
	}else if(v==log_manager_comment){
		
		final AlertDialog.Builder builder=new AlertDialog.Builder(log_manager.this);
		builder.setTitle("评论日志");
		builder.setView(view);
		builder.setCancelable(false);
		builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				builder.setCancelable(true);
		    new postCommentTask().execute();
		
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				System.out.println(id+"wwwwwwwwwwwwww");
				
			}
		});
		builder.create().show();
	
	}
}
//更新日志的异步任务处理
   class log_manager_updateTask extends AsyncTask<String, Integer, String>{

	@Override
	protected  String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		Map<String, String> param=new HashMap<String, String>();
		param.put("DContent", log_manager_content.getText().toString());
		param.put("did", id);	
	 	try {
	 		result1=WebService.getPrimitive("http://192.168.189.1/tomService/service1.asmx", 
					"UpdateDiary", param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result1;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result.equals("OK")){
			Toast.makeText(log_manager.this, "修改完成", Toast.LENGTH_LONG).show();
		}
	}
	   
   }

   @Override
public boolean onTouch(View v, MotionEvent event) {
	// TODO Auto-generated method stub
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:
		if(v==log_manager_edit){
		log_manager_edit.setTextColor(Color.RED);
		}
		if(v==log_manager_edit_success){
			
		log_manager_edit_success.setTextColor(Color.RED);
		}
		if(v==log_manager_delte){
			log_manager_delte.setTextColor(Color.RED);
		}
		if(v==log_manager_comment){
			log_manager_comment.setTextColor(Color.RED);
		}
		
		break;
	case MotionEvent.ACTION_UP:
		if(v==log_manager_edit){
			log_manager_edit.setTextColor(Color.WHITE);
			}
			if(v==log_manager_edit_success){
				
				log_manager_edit_success.setTextColor(Color.WHITE);
			}
			if(v==log_manager_delte){
				log_manager_delte.setTextColor(Color.WHITE);
			}
			if(v==log_manager_comment){
				log_manager_comment.setTextColor(Color.WHITE);
			}
			
		break;
	}
	
	return false;
}
   //删除日志
class deleteTask extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Map<String, String> param=new HashMap<String, String>();
		param.put("did", id);
		try {
			result2=WebService.getPrimitive("http://192.168.189.1/tomService/service1.asmx",
					"DeleteDiary", param);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result2;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result.equals("OK")){
			Toast.makeText(log_manager.this, "删除日志完成", Toast.LENGTH_LONG).show();
		}
		log_manager_title.setText("");
		log_manager_date.setText("");
		log_manager_content.setText("");
		
	}	
	
		
	}
 //提交日志评论
class postCommentTask extends AsyncTask<String,String, String>{

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		Map<String, String> map=new HashMap<String, String>();
		System.out.println(id+"sheng1234");
		map.put("did", id);
		map.put("comcontent", log_comment_content.getText().toString());
		map.put("fuid",log_person.getText().toString());
		//log_person.getText().toString()
		try {
			result3=WebService.getPrimitive("http://192.168.189.1/tomService/service1.asmx",
					"PostComment", map);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result3;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if(result.equals("OK")){
			Toast.makeText(log_manager.this, "日志评论成功", Toast.LENGTH_LONG).show();
		}
	}
}  
  //显示日志评论信息
  class xianshiTask extends AsyncTask<String, String, SoapObject>{

	@Override
	protected SoapObject doInBackground(String... params) {
		// TODO Auto-generated method stub
		  
			/* Map<String, String> map1=new HashMap<String, String>();
					map1.put("did", id);*/
		SoapObject res=null;
		SoapObject sd = new SoapObject("http://tempuri.org/",
				"GetCommentList");
		System.out.println(id+"sheng123456");
		sd.addProperty("did",id);
	result4=WebService.getWebServiceInfo(sd, "http://192.168.189.1/tomService/service1.asmx",
							 "GetCommentList");
					
		   return result4;
	}

	@Override
	protected void onPostExecute(SoapObject result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		System.out.println(result+"gggggggggggggggg");
		Lists = new ArrayList<Map<String, String>>();
		SoapObject ret = (SoapObject)result.getProperty("GetCommentListResult");
System.out.println(ret.getPropertyCount()+"jfeijfiej");		
   for(int i=0;i<ret.getPropertyCount();i++){		
	   Map<String, String> map=new HashMap<String, String>();
			SoapObject ret1 = (SoapObject)ret.getProperty(i);
			System.out.println(ret1+"5555555555555");
			String Dtitle = ret1.getProperty(4).toString();
			System.out.println(Dtitle+"666666666666666");
		String DDate = ret1.getProperty(2).toString();
			String DContent=ret1.getProperty(3).toString();
	System.out.println(ret1.getProperty(0).toString()+"lala de maxi ya");
	System.out.println("--------------------------");
	System.out.println(ret1.getProperty(3).toString());
		 map.put("Dtitle",Dtitle);
		 map.put("DDate",DDate);
		 map.put("DContent", DContent);
		 Lists.add(map);
		}
System.out.println(ret.getPropertyCount()+"dddddd");	

     adapter=new SimpleAdapter(log_manager.this,
				Lists,
				R.layout.item,
				new String[]{"Dtitle","DDate","DContent"}, 
				new int[]{R.id.name,R.id.list_date,R.id.list_content});
     adapter.notifyDataSetChanged();
           list_comment.setAdapter(adapter);
         
	}
	  
  }
}
