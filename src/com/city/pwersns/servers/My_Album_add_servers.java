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

public class My_Album_add_servers extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.url + "/PhotoService.asmx";
		
		String method_name = "CreateAlbum";
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("user_id", arg0[0]);
		maps.put("album_name", arg0[1]);
		maps.put("descript", "1");
		String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
				maps);

		return results;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}

	
	
	
}
