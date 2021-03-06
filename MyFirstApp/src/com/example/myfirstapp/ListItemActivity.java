package com.example.myfirstapp;

import java.util.ArrayList;
import java.util.List;



import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseQuery;
import com.parse.ParseException;


public class ListItemActivity extends ListActivity {
	
	// declare a class variable that will hold a list of items.
	private List<ItemDetails> items;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.list_item);
        
        
        registerForContextMenu(getListView());
       // ParseUser currentUser = ParseUser.getCurrentUser();
       // if (currentUser == null) {
       //     loadLoginView();
       // }
        
        /* create an adapter which manages the data model and adapts it to the individual rows in the list view.
         * Use the given adapter and override the object�s toString() method so that it gives the title of the item.
         */
        
        items = new ArrayList<ItemDetails>();
        ArrayAdapter<ItemDetails> adapter = new ArrayAdapter<ItemDetails>(this, R.layout.list_item_layout, items);
        setListAdapter(adapter);
     
        refreshItemList();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        switch (id) {
           case R.id.action_refresh: {
        	refreshItemList();
            break;
        }
     
        case R.id.action_new: {
            Intent intent = new Intent(this, AddItemActivity.class);
            intent.putExtra("Uniqid","from_List_Activity_Menu"); 
            startActivity(intent);
            finish();
            break;
        }
        case R.id.action_settings: {
            // Do something when user selects Settings from Action Bar overlay
            break;
        }
        }

     
        return super.onOptionsItemSelected(item);
    }

        
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
    
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
      }
    
    public boolean onContextItemSelected(MenuItem item) {

              return super.onContextItemSelected(item);
        }
    
    
    // When called it will fetch data from Parse local datastore and assign it to the item List.
    
	private void refreshItemList() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Inventory");
		query.whereEqualTo("author", ParseUser.getCurrentUser());
		query.fromLocalDatastore();

		setProgressBarIndeterminateVisibility(true);

		query.findInBackground(new FindCallback<ParseObject>() {

			@SuppressWarnings("unchecked")
			@Override
			public void done(List<ParseObject> postList, ParseException e) {
				setProgressBarIndeterminateVisibility(false);
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					items.clear();
					for (ParseObject post : postList) {
						ItemDetails note = new ItemDetails(post.getObjectId(), post.getString("title"), post.getString("description"), post.getString("category"));
						items.add(note);
					}
					((ArrayAdapter<ItemDetails>) getListAdapter()).notifyDataSetChanged();
				} else {
					Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
				}

			}

		});

	}
   
   
   /*
	private void refreshPostList() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("Inventory");
		query.whereEqualTo("author", ParseUser.getCurrentUser());

		setProgressBarIndeterminateVisibility(true);

		query.findInBackground(new FindCallback<ParseObject>() {

			@SuppressWarnings("unchecked")
			@Override
			public void done(List<ParseObject> postList, ParseException e) {
				setProgressBarIndeterminateVisibility(false);
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					items.clear();
					for (ParseObject post : postList) {
						ItemDetails note = new ItemDetails(post.getObjectId(), post.getString("title"), post.getString("description"), post.getString("category"));
						items.add(note);
					}
					((ArrayAdapter<ItemDetails>) getListAdapter()).notifyDataSetChanged();
				} else {
					Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
				}

			}

		});

	}
	
	*/
    
    
	/*
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
	 
		ItemDetails theItems = items.get(position);
	    Intent intent = new Intent(this, AddItemActivity.class);
	    intent.putExtra("Uniqid","from_List_Activity"); 
	    intent.putExtra("itemId", theItems.getId());
	    intent.putExtra("itemTitle", theItems.getTitle());
	    intent.putExtra("itemContent", theItems.getContent());
	    intent.putExtra("itemCategory", theItems.getCategory());
	    startActivity(intent);
	    finish();
	 
	}
	*/
	
    protected void onListItemClick(ListView l, View v, int position, long id) {
   super.onListItemClick(l, v, position, id);
   Object o = this.getListAdapter().getItem(position);
   String pen = o.toString();
   Toast.makeText(this, "You have chosen the item: " + " " + pen, Toast.LENGTH_LONG).show();
   }
	

	
	/*
	private void loadLoginView() {
		Intent intent = new Intent(this, LoginActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    startActivity(intent);
	}
	*/
	
	
	
}


