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

public class Friends_add_servers extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.url + "/Service1.asmx";
		
		String method_name = "FriendRequest";
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("UID1", arg0[0]);
		maps.put("UID2", arg0[1]);
		String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
				maps);

		return results;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
	}

 public class Friends_add_server extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// 下面是参数初始化
			String URL = Urls.url + "/Service1.asmx";
			
			String method_name = "AddFriend";
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("UID1", arg0[0]);
			maps.put("UID2", arg0[1]);
			maps.put("flag", arg0[2]);
			String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
					maps);

			return results;
		}

}
}