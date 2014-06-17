package com.city.powersns.activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.powersna.domain.User;
import com.city.powersns.util.ImageDispose;
import com.city.powersns.util.SOAPUtils;
import com.city.powersns.util.SoapUtils2;
import com.city.powersns.util.Urls;
import com.city.powersns.util.SOAPUtils.getimg;
import com.city.pwersns.servers.My_Album_upload_servers;
import com.city.pwersns.servers.Perfect_info_servers;
import com.city.pwersns.servers.Personal_info_servers;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class My_Album_upload extends Activity {
	EditText Album_name;
	Button comment, select_commect;
	Button back, add, delect;
	ImageView Hand_image;

	String picPath = "";
	String dates = "";
	String uid = "";
	Spinner sp;
	String id = "";
	Bitmap bitmap1 = null;
	String image_base64="";
	TextView textView_photoname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_album);
		init();
		uid = PowerSNSManager.getInstance().getUserid();
		id = PowerSNSManager.getInstance().getId();// 获得相册id
		System.out.println("相册ID" + id);
		
		
		textView_photoname.setText(id+"相册");
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(My_Album_upload.this, My_Album.class);
				My_Album_upload.this.startActivity(intent);

			}
		});

		delect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//image_base64 =ImageDispose.getImageBase64FromBitmap(bitmap1);
			Intent  intent = new Intent();
			intent.setClass(My_Album_upload.this,delect_photo_List.class );
			
		    PowerSNSManager.getInstance().setImgname(image_base64);
			
			My_Album_upload.this.startActivity(intent);	
			}
		});
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				picture pc = new picture();
				pc.Picturt_List();
			
			}
		});
		// 提交修改信息
		comment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});
		// 取消返回到个人中心界面
		select_commect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

	}

	// 显示用户信息

	// 控件实例化
	public void init() {
		Album_name = (EditText) this.findViewById(R.id.editText_album_comment);

		comment = (Button) this.findViewById(R.id.button_album_comment);
		select_commect = (Button) this.findViewById(R.id.button_select_comment);

		back = (Button) this.findViewById(R.id.button_album_backs);
		add = (Button) this.findViewById(R.id.button_add_img);
		delect = (Button) this.findViewById(R.id.button_delect_img);
		Hand_image = (ImageView) this.findViewById(R.id.imageView_img);
		textView_photoname = (TextView) this.findViewById(R.id.textView_photoname);
	}

	class picture extends Handler {

		public void Picturt_List() {
			Intent openAlbumIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			openAlbumIntent.setType("image/*");
			startActivityForResult(openAlbumIntent, 1);

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK) { // 此处的 RESULT_OK 是系统自定义得一个常量

			Log.e("TAG->onresult", "ActivityResult resultCode error");

			return;

		}

		
		ContentResolver resolver = getContentResolver();
		if (requestCode == 1) {
			Uri uri = data.getData();
			bitmap1 = getBitmapFromUri(uri);
System.out.println("Bitmap1   "+bitmap1);				        
			Hand_image.setImageBitmap(bitmap1);
          try {
			String result = new Dateuplod().execute().get();
System.out.println("result   "+result);			
			if(result.equals("SUCCESS..")){
Toast.makeText(My_Album_upload.this, "上传成功", Toast.LENGTH_LONG).show();								
			}else{
				Toast.makeText(My_Album_upload.this, "上传失败", Toast.LENGTH_LONG).show();					
				
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
	}
	public  Bitmap getBitmapFromUri(Uri uri)
    {
     try
     {
      // 读取uri所在的图片
      Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
      return bitmap;
     }
     catch (Exception e)
     {
      Log.e("[Android]", e.getMessage());
      Log.e("[Android]", "目录为：" + uri);
      e.printStackTrace();
      return null;
     }
    }
	//上传图片
	public	class Dateuplod extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... arg0) {
        String URL = Urls.url + "/PhotoService.asmx";
		
		String method_name = "UploadPhotoToAlbum_base64";
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("useid", uid);
		maps.put("albumid", id);
		maps.put("FileName", SOAPUtils.getFileName()+".jpg");
		maps.put("descript", "1");
System.out.println("图片的名字"+SOAPUtils.getFileName()+".jpg");
	    image_base64= ImageDispose.getImageBase64FromBitmap(bitmap1);
System.out.println("bitmap    "+bitmap1);		
		String result=SoapUtils2.UploadImageWebService(URL, method_name, maps, image_base64);
System.out.println(image_base64+"图片的值");		
		
		return result;
		}
	}

	
	
	
}
