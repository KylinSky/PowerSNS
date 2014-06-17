package com.city.powersns.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


public class SoapUtils2 {

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
			//System.out.println("Key:"+paraName);
			//System.out.println("Value:"+params.get(paraName));
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
	
	public static SoapObject callWebServiceWithParams2(String URL,String method_name,Map<String,String> params)
	{
				
		SoapObject request=new SoapObject(NAME_SPACE,method_name);

		Set<String> sets=params.keySet();
		
		for(String paraName:sets)
		{
			System.out.println("Key:"+paraName);
			System.out.println("Value:"+params.get(paraName));
			request.addProperty(paraName,params.get(paraName));
		}
	
		SoapSerializationEnvelope envelope=new SoapSerializationEnvelope (SoapEnvelope.VER11);

		envelope.bodyOut=request;
		envelope.dotNet=true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht=new HttpTransportSE(URL);

		
		  try {
			ht.call(NAME_SPACE+method_name,envelope);

			  SoapObject result=(SoapObject)envelope.getResponse();
			  
			  return result;
			  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		
	}
	
	public static String UploadImageWebService(String URL,String method_name,Map<String,String> params,String image_base64)
	{
				
		SoapObject request=new SoapObject(NAME_SPACE,method_name);

		Set<String> sets=params.keySet();
		
		for(String paraName:sets)
		{
			//System.out.println("Key:"+paraName);
			//System.out.println("Value:"+params.get(paraName));
			request.addProperty(paraName,params.get(paraName));
		}
		request.addProperty("image_base64",image_base64);
	
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
	




           
	
}
