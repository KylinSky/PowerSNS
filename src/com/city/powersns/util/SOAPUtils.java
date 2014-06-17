package com.city.powersns.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class SOAPUtils {

	public static String NAME_SPACE="http://tempuri.org/";
	
	//这 是一个静态方法,用于与服务器通信
	//URL: http://192.168.0.200/TomService/Service1.asmx
	//method_name:login
	//NAME_SPACE="http://tempuri.org/"
	//
	public static String callWebServiceWithParams(String URL,String method_name,Map<String,String> params)
	{
				
		SoapObject request=new SoapObject(NAME_SPACE,method_name);

		Set<String> sets=params.keySet();
		
		for(String paraName:sets)
		{
			request.addProperty(paraName,params.get(paraName));
		}
	
		SoapSerializationEnvelope envelope=new SoapSerializationEnvelope (SoapEnvelope.VER11);

		envelope.bodyOut=request;
		envelope.dotNet=true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht=new HttpTransportSE(URL);

		
		  try {
			ht.call(NAME_SPACE+method_name,envelope);
			  SoapPrimitive primitive =(SoapPrimitive ) envelope.getResponse();
			  
			  String result=primitive.toString();

			  return result;
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "connect fail";
		
	}
	//获取多条信息
public static SoapObject getWebServiceInfo(SoapObject request ,String url,String method){
	SoapObject result =null;
	SoapSerializationEnvelope envelop = new SoapSerializationEnvelope(SoapEnvelope.VER11);
	envelop.bodyOut = request;
	envelop.dotNet=true;
	envelop.setOutputSoapObject(request);
	
	HttpTransportSE ht = new HttpTransportSE(url);
	
	try {
		ht.call(NAME_SPACE+method,envelop);
		
		result = (SoapObject) envelop.bodyIn;
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (XmlPullParserException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return result;
	
	
}
//头像的加载,不能用
public static Bitmap loadPhoto(String imgURL){
	Bitmap bm =null;
	try {
		URL imgUri = new URL(imgURL);
		URLConnection conn = imgUri.openConnection();
		conn.connect();
		InputStream is = conn.getInputStream();
		bm = BitmapFactory.decodeStream(is);
		is.close();		
	} catch (MalformedURLException e) {	
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return bm;		
}
//开启异步类来下载图片
public class getimg extends AsyncTask<String, Integer, Bitmap> {

	@Override
	protected Bitmap doInBackground(String... params) {
	   HttpClient client = new DefaultHttpClient();
	   //图片UIL
	   
	   	HttpGet get = new HttpGet(params[0]);
	   	Bitmap bitmap = null;
	   	
	   	try {
			HttpResponse response = client.execute(get);
			
			bitmap = BitmapFactory.decodeStream(response.getEntity().getContent());			
			
		} catch (ClientProtocolException e) {		
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   		return bitmap;
	}
}
//将图片转换成base64Bary文件
public static String getBase64(File file){
	byte [] buff = new byte[(int)file.length()];
	InputStream is;
	String bytes="";
	try {
		is = new FileInputStream(file);
		is.read(buff);
		 bytes = Base64.encode(buff);
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return bytes;
}

public static String getFileName(){
String dates="";	
	
	SimpleDateFormat si = new SimpleDateFormat("HH.mm.ss");
	dates = si.format(new Date());
	return dates;
}


}
