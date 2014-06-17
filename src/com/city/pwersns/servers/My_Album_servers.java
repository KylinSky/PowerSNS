package com.city.pwersns.servers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.city.powersns.activities.Login;
import com.city.powersns.activities.My_Album;

import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View.OnClickListener;

public class My_Album_servers extends AsyncTask<String, String, SoapObject> {
	
	@Override
	protected SoapObject doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.url + "/PhotoService.asmx";
		
		//String method_name = "getAlbumList";
		SoapObject results = null;
		SoapObject request = new SoapObject(Urls.NAME_SPACE,arg0[1]);
		request.addProperty("userid", arg0[0]);
		results = new SOAPUtils().getWebServiceInfo(request, URL, arg0[1]);		
		return results;	
	}

	
public class My_Album_serverss extends AsyncTask<String, String, String> {
		
		@Override
		protected String doInBackground(String... arg0) {
			// 下面是参数初始化
			String URL = Urls.url + "/PhotoService.asmx";
			
			Map<String, String> maps = new HashMap<String, String>();
			
			maps.put("album_id", arg0[0]);
			String results = new SOAPUtils().callWebServiceWithParams(URL, arg0[1], maps);		
			return results;	
		}

		
		
		
		
	}
	
	
}
