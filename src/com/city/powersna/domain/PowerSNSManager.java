package com.city.powersna.domain;

import android.app.Application;



public class PowerSNSManager {
	private static PowerSNSManager mInstance = null;
	private String userid = null;//用户id
	private String userNick = null;//用户昵称
	private String id=null;//相册名
	private String filePath=null;
	private String imgname="";
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public static synchronized PowerSNSManager getInstance(){
		if(mInstance==null){
			mInstance = new PowerSNSManager();
		}
		return mInstance;	
	}
	public static void release(){
		mInstance = null ;
	}
}
 class PowerSNSApp extends Application{	
	private static  PowerSNSApp instance;
	public static PowerSNSApp getInstance(){
		return instance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		PowerSNSManager.getInstance();
	}
	
}



