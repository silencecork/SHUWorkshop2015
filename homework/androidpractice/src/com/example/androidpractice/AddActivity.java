package com.example.androidpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {
	
	private EditText mInputName;
	private EditText mInputAddress;
	private EditText mInputTelephone;
	private Button mConfirmButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		mInputName = (EditText) findViewById(R.id.editText1);
		mInputAddress = (EditText) findViewById(R.id.editText2);
		mInputTelephone = (EditText) findViewById(R.id.editText3);
		
		mConfirmButton = (Button) findViewById(R.id.button1);
		mConfirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = mInputName.getEditableText().toString();
				String address = mInputAddress.getEditableText().toString();
				String telephone = mInputTelephone.getEditableText().toString();
				
				Intent backIntent = new Intent();
				backIntent.putExtra("name", name);
				backIntent.putExtra("address", address);
				backIntent.putExtra("tele", telephone);
				
				setResult(RESULT_OK, backIntent);
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            return true;
        }
		return super.onOptionsItemSelected(item);
	}
}
