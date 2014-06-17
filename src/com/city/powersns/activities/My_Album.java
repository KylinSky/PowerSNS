package com.city.powersns.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import org.ksoap2.serialization.SoapObject;

import com.city.powersna.domain.PowerSNSManager;
import com.city.pwersns.servers.My_Album_servers;
import com.city.pwersns.servers.Personal_info_servers;
import com.city.powersns.util.Update_Albummess;
import com.city.powersns.util.Update_Albummess.DialogListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class My_Album extends Activity {
	ImageButton back, creation;
	String uid,album;
	ListView lv;
	private final String[] province = new String[] { "仅自己浏览", "仅好友浏览" };
	private final int checkItems = 0;
	private ButtonOnClick buttonOnClick = new ButtonOnClick(1);
	String album_name = "";
	String id;
	SimpleAdapter adapter;
    String filePath=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album);
		uid = PowerSNSManager.getInstance().getUserid();		
		init();
	
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(My_Album.this, Personal_info.class);
				My_Album.this.startActivity(intent);

			}
		});

		creation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(My_Album.this, My_Album_add.class);
				My_Album.this.startActivity(intent);

			}
		});
		// ListView的显示
		list_View();
		lv.setFocusable(true);
		lv.setFocusableInTouchMode(true);

		System.out.println("我到这里了");
		// ListView中的Item中的TextViw事件的监听

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("I coming OnItemClickListene");
				final TextView Album_Name = (TextView) arg1
						.findViewById(R.id.textView_album_name);
				Album_Name.setTextColor(Color.YELLOW);
				Album_Name.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent();
						intent.setClass(My_Album.this, My_Album_upload.class);
						
						
						PowerSNSManager.getInstance().setId(album_name);
						PowerSNSManager.getInstance().setFilePath(filePath);
						
						My_Album.this.startActivity(intent);

					}
				});
				// 修改按钮
				Button but_updat = (Button) arg1
						.findViewById(R.id.button_update);
				but_updat.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
		System.out.println("dd.....");	
		DialogListener listener = new DialogListener() {

			@Override
			public void getText(String string) {
				System.out.println(string);

			}
		};

		Update_Albummess up = new Update_Albummess(
				My_Album.this, listener);
		up.show();
						break;
						default:
							System.out.println("h哈哈");
							
							break;
						}

						return false;
					}
				});

				// 权限按钮
				Button but_permisson = (Button) arg1
						.findViewById(R.id.button_permisson);
				// 对话框的内容
				but_permisson.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View arg0, MotionEvent event) {

						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							System.out.println("我被touch2");
							show_SingleChoiceButton();
							break;
						default:
							System.out.println("h哈哈");
							break;
						}

						return false;
					}
				});

				// 删除按钮
				Button but_delept = (Button) arg1
						.findViewById(R.id.button_delect);

				but_delept.setOnTouchListener(new OnTouchListener() {

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							System.out.println("我被touch3");
							try {
								String result = new My_Album_servers().new My_Album_serverss()
										.execute(id, "DeleteAlbum").get();
								if (result.equals("SUCCESS..")) {
									Toast.makeText(My_Album.this, "删除成功",
											Toast.LENGTH_SHORT).show();
									list_View();
								} else if (result
										.equals("SERVICE WARNING:NO SUCH ALBUM..")) {
									Toast.makeText(My_Album.this, "相册不存在",
											Toast.LENGTH_SHORT).show();

								} else if (result
										.equals("SERVICE ERROR:DELETE ALBUM_TABLE FAILURE..")) {

									Toast.makeText(My_Album.this, "删除失败",
											Toast.LENGTH_SHORT).show();
								}

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							break;
						default:
							break;
						}
						return false;
					}
				});
			}
		});

	}

	private void init() {
		back = (ImageButton) this.findViewById(R.id.imageButton_back);
		creation = (ImageButton) this.findViewById(R.id.imageButton_add);
		lv = (ListView) this.findViewById(R.id.listView_item_style);
	}

	public void list_View() {

		adapter = new SimpleAdapter(My_Album.this, get_Date_List(uid),
				R.layout.item_sytle, new String[] { "album_name" },
				new int[] { R.id.textView_album_name });
		lv.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	// 返回一个List
	public List<Map<String, String>> get_Date_List(String user) {
		SoapObject result;
		String Photo_Mes[] = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			result = new My_Album_servers().execute(user, "getAlbumList").get();
			System.out.println("___________");
			System.out.println(result);
			System.out.println("___________");
			SoapObject detail = (SoapObject) result
					.getProperty("getAlbumListResult");

			for (int i = 0; i < detail.getPropertyCount(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				SoapObject detail3 = (SoapObject) detail.getProperty(i);

				map.put("album_name", detail3.getProperty(2).toString());
				list.add(map);

				album_name = detail3.getProperty(2).toString();
				id = detail3.getProperty(0).toString();
				filePath =detail3.getProperty(6).toString();
				System.out.println(detail3.getProperty(2).toString() + "取到了呵呵");
				System.out.println("图片路径为        "+filePath);
			}
			System.out.println("------------------------------------");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public void show_SingleChoiceButton() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请选择权限");
		builder.setSingleChoiceItems(province, checkItems, buttonOnClick);

		builder.setPositiveButton("确定", buttonOnClick);
		builder.setNegativeButton("取消", buttonOnClick);
		builder.show();

	}

	private class ButtonOnClick implements DialogInterface.OnClickListener {
		private int indes;// 选项的索引

		public ButtonOnClick(int indes) {
			this.indes = indes;

		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// which表示单击的按钮索引，选项的大于0，按钮是小于0
			if (which >= 0) {
				indes = which;
			} else if (which == DialogInterface.BUTTON_POSITIVE) {
				final AlertDialog alertDialog = new AlertDialog.Builder(
						My_Album.this).setMessage(
						"你选择的权限是:" + indes + ":" + province[indes]).show();

				Handler handler = new Handler();
				Runnable runnable = new Runnable() {

					@Override
					public void run() {
						alertDialog.dismiss();

					}
				};
				handler.postDelayed(runnable, 5 * 1000);

			} else if (which == DialogInterface.BUTTON_NEGATIVE) {
				Toast.makeText(My_Album.this, "你没有选择任何东西", Toast.LENGTH_SHORT)
						.show();

			}

		}

	}

}
