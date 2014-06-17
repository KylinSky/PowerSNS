package com.city.pwersns.servers;

import java.util.HashMap;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

import com.city.powersns.activities.Login;

import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Personal_info_servers extends AsyncTask<String, Integer, SoapObject> {
	
	@Override
	protected SoapObject doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.url + "/Service1.asmx";
		
		String method_name = "GetUserInfoById";
		SoapObject results = null;
		SoapObject request = new SoapObject(Urls.NAME_SPACE,method_name);
		request.addProperty("UID", arg0[0]);
		results = new SOAPUtils().getWebServiceInfo(request, URL, method_name);		
		return results;
	}

	@Override
	protected void onPostExecute(SoapObject result) {
//		super.onPostExecute(result);
		//拿到http://10.10.16.192/zzl/Service1.asmx?op=LoadMainPage下的LoadMainPageResult的值
		SoapObject detail = (SoapObject) result.getProperty("GetUserInfoByIdResult");
		String [] userinfo = new String[3];
		userinfo[0] = detail.getProperty(0).toString();
		userinfo[1] = detail.getProperty(1).toString();	

	
	
	}

}
