package com.city.pwersns.servers;

import java.util.HashMap;
import java.util.Map;

import com.city.powersns.activities.Login;

import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;

public class Friends_add_serverss  {
	public String results = "";
	String uids, create_friends_uid;
	Myhalder handle;
	public Friends_add_serverss(String uids,String create_friends_uid){
		this.uids=uids;
		this.create_friends_uid=create_friends_uid;
		Myhalder myhalder =new Myhalder();
		new Thread(new add_serverss()).start();
	}
	
	class Myhalder extends Handler{
	
	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (msg.what == 1) {			
System.out.println("jinlale ");	
		} else {
			System.out.println("88");
		}
	}

}

	class add_serverss implements Runnable {

		@Override
		public void run() {
			String URL = Urls.url + "/Service1.asmx";
			String method_name = "FriendRequest";
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("UID1", uids);
			maps.put("UID2", create_friends_uid);
		String 	result = SOAPUtils
					.callWebServiceWithParams(URL, method_name, maps);
System.out.println(result+"ÏûÏ¢lla");		
		results = result;
			Message message = new Message();
			message.what = 1;
			handle.sendMessage(message);

		}
	
	}
}
