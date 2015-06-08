package com.example.networkcommunication_homework5;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.networkcommunication.volleymgr.NetworkManager;

public class AddTodoActivity extends Activity {
	
	private EditText mOwner, mTitle, mDesc;
	private ProgressDialog mProgressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_todo);

		Button addButton = (Button) findViewById(R.id.button1);
		addButton.setOnClickListener(mAddNewToDoListener);
		
		mOwner = (EditText) findViewById(R.id.editText1);
		mTitle = (EditText) findViewById(R.id.editText2);
		mDesc = (EditText) findViewById(R.id.editText3);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Wait...");
	}

	private OnClickListener mAddNewToDoListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			try {
				String strOwner = URLEncoder.encode(mOwner.getEditableText().toString(), "UTF-8");
				String strTitle = URLEncoder.encode(mTitle.getEditableText().toString(), "UTF-8");
				String strDesc = URLEncoder.encode(mDesc.getEditableText().toString(), "UTF-8");
				mProgressDialog.show();
				
				String url = "http://<SERVER_IP>:<PORT>/api/insert?title=" + strTitle + "&owner=" + strOwner + "&desc=" + strDesc + "&time=" + (System.currentTimeMillis());
				StringRequest request = new StringRequest(Request.Method.GET, url, mOnAddSuccessListener, mOnErrorListener);
				NetworkManager.getInstance(AddTodoActivity.this).request(null, request);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	protected Listener<String> mOnAddSuccessListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			mProgressDialog.dismiss();
			mOwner.setText("");
			mTitle.setText("");
			mDesc.setText("");
			Toast.makeText(AddTodoActivity.this, "新增成功", Toast.LENGTH_LONG).show();
		}
	};
	
	protected ErrorListener mOnErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
			mProgressDialog.dismiss();
			Toast.makeText(AddTodoActivity.this, "Err " + err.toString(), Toast.LENGTH_LONG).show();
		}
	};

}
