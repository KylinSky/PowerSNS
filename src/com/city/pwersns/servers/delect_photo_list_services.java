package com.city.pwersns.servers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.serialization.SoapObject;

import com.city.powersns.activities.Login;

import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class delect_photo_list_services extends AsyncTask<String,String,SoapObject >
{

	@Override
	protected SoapObject  doInBackground(String... arg0) {
		// 下面是参数初始化
		String URL = Urls.url + "/PhotoService.asmx";
		
		String method_name = "getPhotoList";
		SoapObject results = null;
		SoapObject request = new SoapObject(Urls.NAME_SPACE,method_name);
		request.addProperty("album_id","'"+arg0[0]+"'");
		results = new SOAPUtils().getWebServiceInfo(request, URL, method_name);		
		return results;
	
	}

public class delect_photo_services extends AsyncTask<String,String,String >
	{

		@Override
		protected String  doInBackground(String... arg0) {
			// 下面是参数初始化
			String URL = Urls.url + "/PhotoService.asmx";
			
			String method_name = "DeletePhoto";
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("photo_id", arg0[0]);
			String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
					maps);
			return results;
		
		}	

	}
	
	
}
