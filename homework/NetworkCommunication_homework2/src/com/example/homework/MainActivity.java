package com.example.homework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";
	
	private EditText mSearchKeyword;
	private Button mSearchButton;
	private ListView mListView;
	private ProgressDialog mProgressDialog;
	private int mCurrentPage;
	private String mCurrentKeyword;
	private PhotoAdapter mAdapter;

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListView = (ListView) findViewById(R.id.search_result);
		
		View footerView = LayoutInflater.from(this).inflate(R.layout.footer, null);
		mListView.addFooterView(footerView, null, true);
		footerView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCurrentKeyword != null) {
					mCurrentPage++;
					loadPhoto(mCurrentKeyword);
				}
			}
		});
		
		
		mSearchKeyword = (EditText) findViewById(R.id.keyword_input);
		mSearchButton = (Button) findViewById(R.id.btn_search);
		
		mSearchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentKeyword = mSearchKeyword.getEditableText().toString();
				if (TextUtils.isEmpty(mCurrentKeyword)) {
					Toast.makeText(MainActivity.this, "請輸入關鍵字", Toast.LENGTH_LONG).show();
					return;
				}
				
				mProgressDialog.show();
				mCurrentPage = 0;
				loadPhoto(mCurrentKeyword);
			}
		});
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Searching...");
	}
	
	private void loadPhoto(String keyword) {
		try {
			int start = mCurrentPage * 4; 
			String url = URL + URLEncoder.encode(keyword, "UTF-8") + "&start=" + start;
			StringRequest request = new StringRequest(Request.Method.GET, url, mOnSuccessListener, mOnErrorListener);
			NetworkManager.getInstance(MainActivity.this).request(null, request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	protected Listener<String> mOnSuccessListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			mProgressDialog.dismiss();
			ArrayList<Photo> data = new ArrayList<Photo>();
			try {
				JSONObject json = new JSONObject(response);
				JSONObject responseData = json.getJSONObject("responseData");
				JSONArray results = responseData.getJSONArray("results");
				
				for (int i = 0; i < results.length(); i++) {
					Photo photo = new Photo();
					JSONObject photoJson = results.getJSONObject(i);
					photo.title = photoJson.getString("titleNoFormatting");
					photo.url = photoJson.getString("url");
					data.add(photo);
				}
				if (mAdapter == null) {
					mAdapter = new PhotoAdapter(data);
					mListView.setAdapter(mAdapter);
				} else {
					mAdapter.addData(data);
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
	};
	
	protected ErrorListener mOnErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			// TODO Auto-generated method stub
			
		}
	};
	
	class Photo {
		String title;
		String url;
	}
	
	class PhotoAdapter extends BaseAdapter {
		
		private ArrayList<Photo> mData;
		public PhotoAdapter(ArrayList<Photo> data) {
			mData = data;
		}
		
		public void addData(ArrayList<Photo> data) {
			mData.addAll(data);
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return (mData != null) ? mData.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return (mData != null) ? mData.get(position) : null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint("InflateParams")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null);
			}
			
			ImageView image = (ImageView) convertView.findViewById(R.id.photo);
			TextView title = (TextView) convertView.findViewById(R.id.title);
			
			Photo photo = (Photo) getItem(position);
			title.setText(photo.title);
			
			Picasso.with(parent.getContext()).load(photo.url).fit().centerCrop().into(image);
			
			return convertView;
		}
		
	}
	
}
