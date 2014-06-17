package com.city.powersns.activities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.powersns.util.Urls;
import com.city.pwersns.servers.DownloadImage;
import com.city.pwersns.servers.Friends_List_services;
import com.city.pwersns.servers.delect_photo_list_services;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class delect_photo_List extends Activity {
	ListView delect_list;
	ImageView[] imageView;
	String uid = "", id = "", imgname = null, photo_id = "";
	String filePath = null, fileImgPath = "";
	Bitmap bitmap = null;
	Button photo_delect;
	SoapObject detail = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delect_photo_list);
		uid = PowerSNSManager.getInstance().getUserid();
		id = PowerSNSManager.getInstance().getId();
		filePath = PowerSNSManager.getInstance().getFilePath();
		imgname = PowerSNSManager.getInstance().getImgname();

		init();
		System.out.println("相册路径       :" + filePath);
		System.out.println("相册名字      " + id);
		list_View();

		delect_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Button delect_photo = (Button) arg1
						.findViewById(R.id.button_delect_photo);
				delect_photo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						try {
							System.out.println("相册 id                      :"
									+ photo_id);
							String result = new delect_photo_list_services().new delect_photo_services()
									.execute(photo_id).get();
							if (result.equals("SUCCESS..")) {
								Toast.makeText(delect_photo_List.this, "删除成功",
										Toast.LENGTH_SHORT).show();
							} else if (result
									.equals("SERVICE WARNING:NO SUCH PHOTO..")) {
								Toast.makeText(delect_photo_List.this,
										"没有这个照片", Toast.LENGTH_SHORT).show();
							} else if (result
									.equals("SERVICE ERROR:DELETE PHOTO_TABLE FAILURE..")) {								Toast.makeText(delect_photo_List.this, "失败",										Toast.LENGTH_SHORT).show();

							}
							list_View();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});

			}

		});

	}

	// 返回一个List
	private List<Map<String, Object>> get_Date_List(String uid2) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 获得图片信息
		try {
			SoapObject result = new delect_photo_list_services().execute(uid2)
					.get();
			System.out.println("图片的具体信息    " + result);
			detail = (SoapObject) result.getProperty("getPhotoListResult");

			for (int i = 0; i < detail.getPropertyCount(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				SoapObject detail3 = (SoapObject) detail.getProperty(i);
				fileImgPath = detail3.getProperty(3).toString();
				photo_id = detail3.getProperty(0).toString();
				System.out.println(detail3.getProperty(3).toString() + "取到了呵呵");
				System.out.println("图片路径为        " + fileImgPath);
				map.put("delect_photo", fileImgPath);
				DelayTask dt = new DelayTask();
				dt.execute("");
				list.add(map);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// ListView
	private void list_View() {
		SimpleAdapter adapter = new SimpleAdapter(delect_photo_List.this,
				get_Date_List(id), R.layout.delect_phopt_item,
				new String[] { "delect_photo" },
				new int[] { R.id.imageView_delect_photo });

		delect_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	private void init() {
		delect_list = (ListView) this
				.findViewById(R.id.listView1_delect_photo_list);

	}

	class DelayTask extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			System.out.println("Item长度        :" + detail.getPropertyCount());
			DownloadImage[] tasks = new DownloadImage[detail.getPropertyCount()];

			imageView = new ImageView[detail.getPropertyCount()];

			for (int i = 0; i < detail.getPropertyCount(); i++) {
				System.out.println("goint to create task!");
				View temp = delect_list.getChildAt(i);
				System.out.println("success in getChildAt!");
				if (temp != null) {
					System.out.println("List Item " + i + " found!");
				} else {
					System.out.println("List Item " + i + " not found!");
				}

				imageView[i] = (ImageView) temp
						.findViewById(R.id.imageView_delect_photo);
				System.out.println(imageView[i] + "tttttt");
				if (imageView[i] != null) {
					System.out.println("imageView " + i + " found!");
				} else {
					System.out.println("imageView " + i + " not found!");

				}

				try {
					imageView[i].setImageBitmap(new DownloadImage().execute(
							Urls.url + "/PhotoFile/" + fileImgPath).get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}

	}

	public class DownloadImage extends AsyncTask<String, Integer, Bitmap> {
		int target = 0;

		@Override
		protected Bitmap doInBackground(String... arg0) {
			System.out.println("下标               ：" + target);

			String url = arg0[0];
			// String imagePath=arg0[1];

			// TODO Auto-generated method stub
			HttpClient hc = new DefaultHttpClient();

			// 提供get的位置

			HttpGet hg = new HttpGet(url);
			Bitmap bmp = null;

			try {

				HttpResponse hr = hc.execute(hg);

				InputStream is = hr.getEntity().getContent();

				byte[] data = readStream(is);

				bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				System.out.println("图片                   +" + bmp);
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

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			// imageView[target].setImageBitmap(result);

		}

		public byte[] readStream(InputStream inStream) throws Exception {
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

}
