package com.example.myfirstapp;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.xml.sax.SAXException;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import jim.h.common.android.lib.zxing.config.ZXingLibConfig;
import jim.h.common.android.lib.zxing.integrator.IntentIntegrator;
//import jim.h.common.android.lib.zxing.sample.ZXingLibSampleActivity;
//import jim.h.common.android.lib.zxing.sample.R;
import jim.h.common.android.lib.zxing.integrator.IntentResult;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;



public class MainActivity extends Activity {
	
	Context context = this;
	boolean gotCode = false;
	String thecode = "";
	String theTitle = "";
	
	
	
    private Handler        handler = new Handler();
    private TextView       txtScanResult;
    private ZXingLibConfig zxingLibConfig;
    
    // HTML page
    static final String BLOG_URL = "http://www.amazon.co.uk/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=";
    String barcode = "";
    // example XPATH queries in the form of strings - will be used later
    String xpath = "//../li[@id='result_0']//../h2";
    //String xpath_price = "//../li[@id='result_0']//../span[contains(@class,'s-price')]/text()";
    
    // TagNode object, its use will come in later
    private static TagNode node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	if (android.os.Build.VERSION.SDK_INT > 9) {
    	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	    StrictMode.setThreadPolicy(policy);
    	}
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            loadLoginView();
        }
        
        
		txtScanResult = (TextView) findViewById(R.id.scan_result);
        zxingLibConfig = new ZXingLibConfig();
        zxingLibConfig.useFrontLight = true;
        
        View btnScan = findViewById(R.id.scan_button);
        


        
        btnScan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator.initiateScan(MainActivity.this, zxingLibConfig);
                
            }
        });
        
        addListenerOnButton();
        openViewItem();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_logout) {
        	ParseUser.logOut();
            loadLoginView();
        }
        return super.onOptionsItemSelected(item);
    }
    
    
	public void addListenerOnButton() {
		 
		final Context context = this;
 
		Button button = (Button) findViewById(R.id.help_button);
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    //Intent intent = new Intent(context, AddItemActivity.class);
                //startActivity(intent);   
			}
 
		});
 
	}
	
	public void openViewItem() {
		 
		final Context context = this;
 
		Button button = (Button) findViewById(R.id.inventory_button);
 
		button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, ListItemActivity.class);
                startActivity(intent);   
			}
 
		});
 
	}
	
	
	
	public String getBlogStats(String code) throws Exception {
        String stats = "";
 
        // config cleaner properties
        HtmlCleaner htmlCleaner = new HtmlCleaner();
        CleanerProperties props = htmlCleaner.getProperties();
        props.setAllowHtmlInsideAttributes(false);
        props.setAllowMultiWordAttributes(true);
        props.setRecognizeUnicodeChars(true);
        props.setOmitComments(true);
 
        // create URL object
        URL url = new URL(BLOG_URL + code);
        // get HTML page root node
        TagNode root = htmlCleaner.clean(url);
 
        // query XPath
        Object[] statsNode = root.evaluateXPath(xpath);
        // process data if found any node
        if(statsNode.length > 0) {
            // I already know there's only one node, so pick index at 0.
            TagNode resultNode = (TagNode)statsNode[0];
            // get text data from HTML node
            stats = resultNode.getText().toString();
        }
 
        // return value
        return stats;
    }
	
	

    /**
     * A placeholder fragment containing a simple view.
     */
    /*
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    */


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// parse init
    	
    	
    	final Context context = this;
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE: 
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //txtScanResult.setText(result);
                            String value = "";
                            try {
                            	value = getBlogStats(result);
                            	txtScanResult.setText(value);
								//Log.d("---------", getBlogStats());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	
                            thecode = txtScanResult.toString();
                            Intent intent = new Intent(context, AddItemActivity.class);
                            
                            /****
                            Bundle extras = new Bundle();
                            //extras.putString("barcode", result);

                            extras.putString("title", value);
                            // parse create object & create field called barcode and title

                            	//intent.putExtra("barcode",result);
                            intent.putExtras(extras);
                            ****/
                            intent.putExtra("Uniqid","from_Main"); 
                            intent.putExtra("title", value);
                            
                            
                            
                            startActivity(intent); 
                            finish();

                        }
                    });
                }
                break;
            default:
        }
    }
    
	
	private void loadLoginView() {
		Intent intent = new Intent(this, LoginActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
	    startActivity(intent);
	}
	
}
