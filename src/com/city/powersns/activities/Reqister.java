package com.city.powersns.activities;

import java.util.concurrent.ExecutionException;

import com.city.pwersns.servers.Login_servers;
import com.city.pwersns.servers.Reqister_servers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reqister extends Activity{
  EditText et1,et2,et3,et4;
  Button but1,but2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reqister);
		et1 = (EditText)this.findViewById(R.id.uid);
		et2 = (EditText)this.findViewById(R.id.password);
		et3 = (EditText)this.findViewById(R.id.passwords);
		et4 = (EditText)this.findViewById(R.id.nickname);
		
		but1 = (Button)this.findViewById(R.id.reqister);
		but2 = (Button)this.findViewById(R.id.cancel);
		
		but1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				try {
					String tt = new Reqister_servers().execute(
							et1.getText().toString(), et2.getText().toString(),et3.getText().toString(),et4.getText().toString())
							.get();
				 if(tt.equals("true")){
					 Toast.makeText(Reqister.this, "注册成功", Toast.LENGTH_SHORT).show();
					 Intent intent = new Intent();
					 intent.setClass(Reqister.this, Login.class);
					 Reqister.this.startActivity(intent);
					 Reqister.this.finish();
				 }else{
					 Toast.makeText(Reqister.this, "注册不成功", Toast.LENGTH_SHORT).show();
					 
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


}
