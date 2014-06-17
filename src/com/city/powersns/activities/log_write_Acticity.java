package com.city.powersns.activities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.WebService;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class log_write_Acticity extends Activity {
   EditText log_write_title,log_write_content;
   Button fabiao,quxiao;
   ImageView img;
   Boolean result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.write_log);
		init();
		fabiao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new log_writeTask().execute();
				Intent intent=new Intent(log_write_Acticity.this,my_diary.class);
				startActivity(intent);
				log_write_Acticity.this.finish();
			}
		});
		quxiao.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				log_write_title.setText("");
				log_write_content.setText("");
			}
		});
		img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(log_write_Acticity.this,log_manager.class);
				startActivity(intent);
				log_write_Acticity.this.finish();
			}
		});
	}
    public void init(){
    	log_write_title=(EditText)findViewById(R.id.title_write);
    	log_write_content=(EditText)findViewById(R.id.content_write);
    	fabiao=(Button)findViewById(R.id.fabiao);
    	quxiao=(Button)findViewById(R.id.quxiao);
    	img=(ImageView)findViewById(R.id.imageView33);
    }
    class log_writeTask extends AsyncTask<String, String, Boolean>{

		@Override
		protected Boolean doInBackground(String... params) {
			// TODO Auto-generated method stub
			Map<String, String> param=new HashMap<String, String>();
			param.put("title", log_write_title.getText().toString());
			param.put("content", log_write_content.getText().toString());
			param.put("UID", PowerSNSManager.getInstance().getUserid());
			try {
				String result1=WebService.getPrimitive("http://192.168.189.1/tomService/service1.asmx",
						"WriteDiary", param);
				result=Boolean.parseBoolean(result1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;			
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			System.out.println(result+"qqqqqqqqqqqq");
			if(result==true){
				Toast.makeText(log_write_Acticity.this, "发表日志成功", Toast.LENGTH_LONG).show();
				
			}else{
				System.out.println("访问失败");
			}
		}  	
    }
}
