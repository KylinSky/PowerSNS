package com.city.pwersns.servers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.city.powersns.activities.My_Album_upload;
import com.city.powersns.activities.Perfect_info;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.SoapUtils2;
import com.city.powersns.util.Urls;
import com.city.powersns.util.ImageDispose;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.provider.MediaStore;
import android.widget.ImageView;

public class My_Album_upload_servers extends Activity {
	My_Album_upload cont;
   Bitmap bitmap;
    String dates="";
    ImageView iv;
	public My_Album_upload_servers() {
	}

	public My_Album_upload_servers(My_Album_upload Context) {
		cont = Context;
		
	}
	//Í¼Æ¬ÉÏ´«


}
