package com.example.myfirstapp;


import java.io.InputStream;

import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity  {
	
	//private TextView       txtScanResult;
	private EditText name;
	
	
	private ItemDetails item;
	
	private EditText titleEditText;
	private EditText detailsEditText;
	private EditText categoryEditText;
	private String itemTitle;
	private String itemDetails;
	private String itemCategory;
	private ImageView imgView;
	
	private Button saveNoteButton;
	private Button cancelButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
        new Thread(new Runnable() { 
            public void run(){
               //All your heavy stuff here!!!
            }
        }).start();
		
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		setContentView(R.layout.add_item);
		Intent intent = this.getIntent();
		
		
		
		titleEditText = (EditText) findViewById(R.id.itemTitle);
	    detailsEditText = (EditText) findViewById(R.id.itemDetailsInput);
	    categoryEditText = (EditText) findViewById(R.id.itemCategory);
	    imgView = (ImageView) findViewById(R.id.imageView1);
	    int width = 460;
	    int height = 426;
	    LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
	    imgView.setLayoutParams(parms);
	    
	    
	    if(intent !=null)
        {
	        String strdata = intent.getExtras().getString("Uniqid");
		    if(strdata.equals("from_Main"))
	        {
		    	
				if (intent.getExtras() != null) {
					String title = intent.getStringExtra("title");
					String descritpion = intent.getStringExtra("description");
					String category = intent.getStringExtra("category");
					String image = intent.getStringExtra("image");
					
					titleEditText.setText(title);
				    detailsEditText.setText(descritpion);
				    categoryEditText.setText(category);
				    
				    try{
				    	
				    	URL url = new URL(image);
				    	HttpGet httpRequest = null;
				    	httpRequest = new HttpGet(url.toURI());
				    	
				    	HttpClient httpclient = new DefaultHttpClient();
				        HttpResponse response = (HttpResponse) httpclient
				                .execute(httpRequest);
				        
				        HttpEntity entity = response.getEntity();
				        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
				        InputStream input = b_entity.getContent();
				        
				        Bitmap bitmap = BitmapFactory.decodeStream(input);

				        imgView.setImageBitmap(bitmap);
				    	
				    } catch (Exception ex){
				    	
				    }
				}
	        }
	        if(strdata.equals("from_List_Activity"))
	        {
	    	    if (intent.getExtras() != null) {
	    	        item = new ItemDetails(intent.getStringExtra("itemId"), intent.getStringExtra("itemTitle"), intent.getStringExtra("itemContent"), intent.getStringExtra("itemCategory"));
	    	 
	    	        titleEditText.setText(item.getTitle());
	    	        detailsEditText.setText(item.getContent());
	    	        categoryEditText.setText(item.getCategory());
	    	        
	    	    }
	        }
	        if(strdata.equals("from_List_Activity_Menu"))
	        {
	        	// From Action settings Menu
	        	// Do Nothing
	        	
	        }
	        
        }
	    
	    
	    
	    
	    
	    
	    /*
	    if (intent.getExtras() != null) {
	        item = new ItemDetails(intent.getStringExtra("itemId"), intent.getStringExtra("itemTitle"), intent.getStringExtra("itemContent"), intent.getStringExtra("itemCategory"));
	 
	        titleEditText.setText(item.getTitle());
	        detailsEditText.setText(item.getContent());
	        categoryEditText.setText(item.getCategory());
	        
	    }

		*/
		
		/*
		//txtScanResult = (TextView) findViewById(R.id.scan_result);
		//name = (EditText)findViewById(R.id.itemTitle);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String title = extras.getString("title");
		    //String value = extras.getString("barcode");
		    //txtScanResult.setText(title);
			titleEditText.setText(title);
		    
		}
		*/
	    
	    
	    
		cancelButton = (Button)findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
	        	startActivity(intent);
	        	finish();
	        }
	    });
		
		
	    saveNoteButton = (Button)findViewById(R.id.save_button);
	    saveNoteButton.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            saveItem();
	        }
	    });
		
	}
	
	private void saveItem(){
		
		itemTitle = titleEditText.getText().toString();
        itemDetails = detailsEditText.getText().toString();
        itemCategory = categoryEditText.getText().toString();
 
        itemTitle = itemTitle.trim();
        itemDetails = itemDetails.trim();
        itemCategory = itemCategory.trim();
 
        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save
 
        if (!itemTitle.isEmpty()) {
 
            // Check if post is being created or edited
 
            if (item == null) {
                // create new post
 
                ParseObject post = new ParseObject("Inventory");
                post.put("title", itemTitle);
                post.put("description", itemDetails);
                post.put("category", itemCategory);
                post.put("author", ParseUser.getCurrentUser());
                setProgressBarIndeterminateVisibility(true);
                post.saveEventually();
                post.pinInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                    	setProgressBarIndeterminateVisibility(false);
                        if (e == null) {
                            // Saved successfully.
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        	Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                        	startActivity(intent);
                        } else {
                            // The save failed.
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });
 
            }
            else {
                // update post
 
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Inventory");
                query.fromLocalDatastore();
                // Retrieve the object by id
                query.getInBackground(item.getId(), new GetCallback<ParseObject>() {
                  public void done(ParseObject post, ParseException e) {
                    if (e == null) {
                      // Now let's update it with some new data.
                        post.put("title", itemTitle);
                        post.put("description", itemDetails);
                        post.put("category", itemCategory);
                        post.put("author", ParseUser.getCurrentUser());
                        setProgressBarIndeterminateVisibility(true);
                        post.saveEventually();
                        post.pinInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                            	setProgressBarIndeterminateVisibility(false);
                                if (e == null) {
                                    // Saved successfully.
                                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                	Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                                	startActivity(intent);
                                } else {
                                    // The save failed.
                                    Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                    Log.d(getClass().getSimpleName(), "User update error: " + e);
                                }
                            }
                        });
                    }
                  }
                });
            }
        }
        else if (itemTitle.isEmpty() && !itemDetails.isEmpty() || itemTitle.isEmpty() && !itemCategory.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
            builder.setMessage(R.string.edit_error_message)
                .setTitle(R.string.edit_error_title)
                .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

	}
	
	
	
	/*
	 * Old saveItem that only works with the cloud server
	 */
	
	/*
	private void saveItem() {
		 
        itemTitle = titleEditText.getText().toString();
        itemDetails = detailsEditText.getText().toString();
        itemCategory = categoryEditText.getText().toString();
 
        itemTitle = itemTitle.trim();
        itemDetails = itemDetails.trim();
        itemCategory = itemCategory.trim();
 
        // If user doesn't enter a title or content, do nothing
        // If user enters title, but no content, save
        // If user enters content with no title, give warning
        // If user enters both title and content, save
 
        if (!itemTitle.isEmpty()) {
 
            // Check if post is being created or edited
 
            if (item == null) {
                // create new post
 
                ParseObject post = new ParseObject("Inventory");
                post.put("title", itemTitle);
                post.put("description", itemDetails);
                post.put("category", itemCategory);
                post.put("author", ParseUser.getCurrentUser());
                setProgressBarIndeterminateVisibility(true);
                post.saveInBackground(new SaveCallback() {
                    public void done(ParseException e) {
                    	setProgressBarIndeterminateVisibility(false);
                        if (e == null) {
                            // Saved successfully.
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                        	Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                        	startActivity(intent);
                        } else {
                            // The save failed.
                            Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                            Log.d(getClass().getSimpleName(), "User update error: " + e);
                        }
                    }
                });
 
            }
            else {
                // update post
 
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Inventory");
 
                // Retrieve the object by id
                query.getInBackground(item.getId(), new GetCallback<ParseObject>() {
                  public void done(ParseObject post, ParseException e) {
                    if (e == null) {
                      // Now let's update it with some new data.
                        post.put("title", itemTitle);
                        post.put("description", itemDetails);
                        post.put("category", itemCategory);
                        post.put("author", ParseUser.getCurrentUser());
                        setProgressBarIndeterminateVisibility(true);
                        post.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                            	setProgressBarIndeterminateVisibility(false);
                                if (e == null) {
                                    // Saved successfully.
                                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                	Intent intent = new Intent(getApplicationContext(), ListItemActivity.class);
                                	startActivity(intent);
                                } else {
                                    // The save failed.
                                    Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
                                    Log.d(getClass().getSimpleName(), "User update error: " + e);
                                }
                            }
                        });
                    }
                  }
                });
            }
        }
        else if (itemTitle.isEmpty() && !itemDetails.isEmpty() || itemTitle.isEmpty() && !itemCategory.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddItemActivity.this);
            builder.setMessage(R.string.edit_error_message)
                .setTitle(R.string.edit_error_title)
                .setPositiveButton(android.R.string.ok, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
	*/
	
	
	
	

}
