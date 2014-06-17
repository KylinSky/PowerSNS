package com.city.powersns.util;



import com.city.powersns.activities.Fridnds_add;
import com.city.powersns.activities.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Dialog extends android.app.Dialog{
 Button yes,no;
 TextView usrid;
 public DialogListener listener;
 public Fridnds_add friends_uid;

	public Dialog(final Context context,DialogListener listener) {
		super(context);
		friends_uid =(Fridnds_add) context;
		this.listener = listener;
	
	}
	

	public interface DialogListener{
		void getText(String string);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.dialog);
	    this.setTitle("消息");
	    init(); 
System.out.println(friends_uid.create_friends_uid.getText().toString()+"想加你为好友");	    
	    usrid.setText(friends_uid.create_friends_uid.getText().toString());
	yes.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
System.out.println("Yes");		
		//friends_uid.flag="A";
		listener.getText("A");
			Dialog.this.dismiss();
		}
	});
	no.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
System.out.println("no");			
Dialog.this.dismiss();	
		}
	});  
	
	}

	private void init() {
		yes = (Button) this.findViewById(R.id.button_yes);
		no = (Button) this.findViewById(R.id.button_no);
		usrid = (TextView) this.findViewById(R.id.textView_uid);
	}

	
}
