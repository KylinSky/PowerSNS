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

public class DownloadImage extends AsyncTask<String,String,Bitmap >
{

	@Override
	protected Bitmap  doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String url= arg0[0];
		//String imagePath=arg0[1];
		
		// TODO Auto-generated method stub
		HttpClient hc=new DefaultHttpClient();
		
		//提供get的位置
		
		HttpGet hg=new HttpGet(url);
		Bitmap bmp=null;
		
		try {
			
			HttpResponse hr=hc.execute(hg);
	
			InputStream is=hr.getEntity().getContent();
			
			byte[] data = readStream(is);
			
			bmp=BitmapFactory.decodeByteArray(data, 0, data.length);
			
			return bmp;
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}

	
	public  byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}
	

	
	
	
}
