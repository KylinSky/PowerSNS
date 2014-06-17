package com.city.powersns.util;
import com.city.powersns.activities.Message_List;
import com.city.powersns.activities.My_Album;
import com.city.powersns.activities.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Update_Albummess extends android.app.Dialog{
 Button yes,no;
EditText usrid;
 public DialogListener listener;
 public My_Album message_List;

	public Update_Albummess(final Context context,DialogListener listener) {
		super(context);
		message_List =(My_Album) context;
		this.listener = listener;
	
	}
	

	public interface DialogListener{
		void getText(String string);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.album_name);
	    this.setTitle("ÐÞ¸ÄÏà²áÃû");
	    init(); 
   
	yes.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
System.out.println("Yes");		
  listener.getText(usrid.getText().toString());

		listener.getText(usrid.getText().toString());
			Update_Albummess.this.dismiss();
		}
	});
	no.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
System.out.println("no");			
Update_Albummess.this.dismiss();	
		}
	});  
	
	}

	private void init() {
		yes = (Button) this.findViewById(R.id.button_determints);
		no = (Button) this.findViewById(R.id.button_cancle);
		usrid = (EditText) this.findViewById(R.id.editText_neirong);
	}

	
}
