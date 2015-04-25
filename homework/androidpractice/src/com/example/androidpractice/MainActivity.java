package com.example.androidpractice;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {
	
	private ListView mListView;
	private ArrayList<Person> mData = new ArrayList<Person>();
	private ArrayAdapter<Person> mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mListView = (ListView) findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mData);
        mListView.setAdapter(mAdapter);
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
			Intent intent = new Intent(this, AddActivity.class);
			startActivityForResult(intent, 100);
			
			return true;
		} else if (id == R.id.action_about) {
			
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setTitle("關於");
			b.setMessage("作者:Justin");
			b.setPositiveButton("確定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			
			b.show();
			
			return true;
		}
        return super.onOptionsItemSelected(item);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			String name = data.getStringExtra("name");
			String address = data.getStringExtra("address");
			String telephone = data.getStringExtra("tele");
			
			Person person = new Person();
			person.address = address;
			person.name = name;
			person.telephone = telephone;
			
			mData.add(person);
			
			mAdapter.notifyDataSetChanged();
		}
	}
    
    
}
