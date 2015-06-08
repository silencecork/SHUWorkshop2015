package com.example.networkcommunication_homework5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.networkcommunication.volleymgr.NetworkManager;

public class ShowtodoActivity extends Activity {
	
	private ListView mListView;
	private ProgressDialog mProgressDialog;

	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			final Todo todo = (Todo) parent.getAdapter().getItem(position);
			AlertDialog.Builder builder = new AlertDialog.Builder(ShowtodoActivity.this);
			builder.setMessage(todo.desc);
			builder.setPositiveButton("Ãö³¬", null);
			builder.setNegativeButton("§R°£", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					StringRequest request = new StringRequest(Request.Method.GET, "http://<SERVER_IP>:<PORT>/api/delete?id=" + todo._id, mOnDeleteSuccessListener, mOnErrorListener);
					NetworkManager.getInstance(ShowtodoActivity.this).request(null, request);
				}
			});
			builder.show();
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showtodo);
		
		mListView = (ListView) findViewById(R.id.listView1);
		mListView.setOnItemClickListener(mOnItemClickListener);
		
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Wait...");
		
		queryTodoList();
	}
	
	private void queryTodoList() {
		mProgressDialog.show();
		StringRequest request = new StringRequest(Request.Method.GET, "http://<SERVER_IP>:<PORT>/api/query", mOnQuerySuccessListener, mOnErrorListener);
		NetworkManager.getInstance(this).request(null, request);
	}
	
	private Listener<String> mOnQuerySuccessListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			mProgressDialog.dismiss();
			try {
				JSONArray ary = new JSONArray(response);
				int length = ary.length();
				
				ArrayList<Todo> datas = new ArrayList<Todo>();
				
				for (int i = 0; i < length; i++) {
					JSONObject obj = ary.getJSONObject(i);
					Todo todo = new Todo();
					todo._id = obj.getString("_id");
					todo.title = obj.getString("title");
					todo.desc = obj.getString("desc");
					todo.owner = obj.getString("owner");
					todo.time = obj.getLong("time");
					datas.add(todo);
				}
				
				TodoListAdapter adapter = new TodoListAdapter(ShowtodoActivity.this, datas);
				mListView.setAdapter(adapter);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
	
	private Listener<String> mOnDeleteSuccessListener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			mProgressDialog.dismiss();
			queryTodoList();
		}
	};
	
	private ErrorListener mOnErrorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError err) {
			mProgressDialog.dismiss();
			Toast.makeText(ShowtodoActivity.this, err.toString(), Toast.LENGTH_LONG).show();
		}
	};
	
	class Todo {
		String _id;
		String title;
		String desc;
		String owner;
		long time;
	}
	
	class TodoListAdapter extends BaseAdapter {
		
		private Context mContext;
		private ArrayList<Todo> mData;
		
		TodoListAdapter(Context context, ArrayList<Todo> data) {
			mContext = context;
			mData = data;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@SuppressLint({ "SimpleDateFormat", "InflateParams" })
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(mContext).inflate(R.layout.todo_item, null);
			}
			
			Todo todo = (Todo) getItem(position);
			
			TextView title = (TextView) convertView.findViewById(R.id.title);
			title.setText(todo.title);
			
			TextView owner = (TextView) convertView.findViewById(R.id.owner);
			owner.setText(todo.owner);
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			TextView time = (TextView) convertView.findViewById(R.id.time);
			time.setText(format.format(new Date(todo.time)));
			
			return convertView;
		}

	}
}
