package com.example.networkcommunication;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
			try {
				JSONObject json = new JSONObject(string);
				String title = json.getString("title");
				String body = json.getString("body");
				
				TextView text1 = (TextView) findViewById(R.id.textView1);
				text1.setText(title);
				TextView text2 = (TextView) findViewById(R.id.textView2);
				text2.setText(body);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	private ErrorListener mErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			Log.e("Error", error.toString());
		}
	};
}
