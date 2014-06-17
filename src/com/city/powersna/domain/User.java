package com.city.powersna.domain;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Serializable{
 private  String UID;

public User() {}

public User(String uID) {
	super();
	UID = uID;
}

public String getUID() {
	return UID;
}

public void setUID(String uID) {
	UID = uID;
}

 
}
