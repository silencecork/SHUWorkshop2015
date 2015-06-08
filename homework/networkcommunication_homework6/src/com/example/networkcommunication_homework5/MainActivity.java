package com.example.networkcommunication_homework5;

import com.example.networkcommunication.volleymgr.NetworkManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button displayTodo = (Button) findViewById(R.id.button2);
		displayTodo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ShowtodoActivity.class);
				startActivity(intent);
			}
		});
		Button addTodo = (Button) findViewById(R.id.button1);
		addTodo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		NetworkManager.getInstance(this).stop();
	}
}
