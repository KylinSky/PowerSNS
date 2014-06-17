package com.city.powersns.activities;

import java.util.zip.Inflater;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

public class WelcomeActivity extends Activity {
     AlphaAnimation animation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		LayoutInflater inflater=LayoutInflater.from(WelcomeActivity.this);
		View view = inflater.inflate(R.layout.welcome, null);
		setContentView(view);
		animation=new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(1500);
		view.setAnimation(animation);
		animation.startNow();
		animation.setAnimationListener(new AnimationListener() {
			
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(WelcomeActivity.this,Login.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		});
	}
   
}
