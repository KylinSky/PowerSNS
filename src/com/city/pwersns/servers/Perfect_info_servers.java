package com.city.pwersns.servers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.city.powersns.activities.Perfect_info;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.Urls;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.MediaStore;
import android.widget.ImageView;

public class Perfect_info_servers extends Activity {
	Perfect_info cont;
   Bitmap bitmap;
    String dates="";
    ImageView iv;
	public Perfect_info_servers() {
	}

	public Perfect_info_servers(Perfect_info Context) {
		cont = Context;
		
	}
	//Í¼Æ¬ÉÏ´«
public	class Dateuplod extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... arg0) {
        String URL = Urls.url + "/Service1.asmx";
		
		String method_name = "ChangePhoto";
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("ImgIn", arg0[0]);
		maps.put("FileName", arg0[1]);
		maps.put("UID", arg0[2]);
		String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
				maps);		
			return results;
		}
	}
public	class update_User extends AsyncTask<String, Integer, String>{

	@Override
	protected String doInBackground(String... arg0) {
    String URL = Urls.url + "/Service1.asmx";
	
	String method_name = "UpdateUserInfo";
	Map<String, String> maps = new HashMap<String, String>();
	maps.put("UID", arg0[0]);
	maps.put("_name", arg0[1]);
	maps.put("_age", arg0[2]);
	maps.put("_gender", arg0[3]);
	maps.put("_mood", arg0[4]);
	String results = SOAPUtils.callWebServiceWithParams(URL, method_name,
			maps);
System.out.println(results+"T");	
		return results;
	}
}
}
