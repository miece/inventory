package com.example.mysecondapp;

import com.example.simplefragment.SimpleFragmentActivity;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    public void simple(View v){
    	
    	Intent intent = new Intent(getApplicationContext(), SimpleFragmentActivity.class);
    	startActivity(intent);
    	
    }
    
    
    
    
    
}
