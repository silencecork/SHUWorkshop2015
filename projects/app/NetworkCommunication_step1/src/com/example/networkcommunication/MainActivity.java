package com.example.networkcommunication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.networkcommunication.volleymgr.NetworkManager;

public class MainActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        StringRequest request = new StringRequest(Request.Method.GET, "http://jsonplaceholder.typicode.com/posts/1", mResponseListener, mErrorListener);
        NetworkManager.getInstance(this).request(null, request);
    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		NetworkManager.getInstance(this).stop();
	}

	private Listener<String> mResponseListener = new Listener<String>() {

		@Override
		public void onResponse(String string) {
			Log.d("Response", string);
		}
	};
	
	private ErrorListener mErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("Error", error.toString());
		}
	};
}
