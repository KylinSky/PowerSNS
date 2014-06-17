/**
 * 
 */
package com.city.pwersns.servers;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebService {

	public static String NAME_SPACE="http://tempuri.org/";
	/**
	 * 返回单值数据的webservie访问方法，比如布尔型、整形、字符串
	 * 
	 * @param request
	 * @param method
	 * @param url
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static String getPrimitive(String url, String method,
			Map<String,String> params) throws IOException, XmlPullParserException {
		
		// TODO Auto-generated method stub
		SoapObject request=new SoapObject(NAME_SPACE,method);
	Set<String> sets=params.keySet();
		
		for(String paraName:sets)
		{
			System.out.println("Key:"+paraName);
			System.out.println("Value:"+params.get(paraName));
			request.addProperty(paraName,params.get(paraName));
		}
		String result = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapSerializationEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.call(NAME_SPACE + method, envelope);
		  SoapPrimitive primitive =(SoapPrimitive ) envelope.getResponse(); 
		  result=primitive.toString();
		  return result;
	}
	/**
	 * 访问webservice 中，获得复杂数据的调用方法
	 * @param url
	 * @param method
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public static SoapObject getSoapObject(String url, String method,
			Map<String,String> params) throws IOException, XmlPullParserException {
		// TODO Auto-generated method stub
		SoapObject request=new SoapObject(NAME_SPACE,method);
		request.addProperty("UID",params.get("UID"));
		/*Set<String> sets=params.keySet();
		for(String paraName:sets)
		{
			System.out.println("Key:"+paraName);
			System.out.println("Value:"+params.get(paraName));
			request.addProperty(paraName,params.get(paraName));
		}*/
		SoapObject result = null;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapSerializationEnvelope.VER11);
		envelope.bodyOut = request;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(url);
		ht.call(NAME_SPACE + method , envelope);
		result = (SoapObject)envelope.bodyIn;
		return result;
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

}
