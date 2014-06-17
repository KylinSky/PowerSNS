package com.city.pwersns.servers;

import java.util.HashMap;
import java.util.Map;

import com.city.powersns.activities.Login;

import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Reqister_servers extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.url + "/Service1.asmx";
		String method_name = "RegisterNewAccount";
		
		if(arg0[1].equals(arg0[2])){
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("UID", arg0[0]);
		maps.put("Password", arg0[1]);		
		maps.put("NickName", arg0[3]);
		String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
				maps);	
		return results;		
		}else{
			String results;
			return results="false";
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

}
