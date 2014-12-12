package com.example.simplefragment;

import com.example.mysecondapp.R;
import com.example.mysecondapp.R.id;
import com.example.mysecondapp.R.layout;
import com.example.mysecondapp.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class SimpleFragmentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_fragment);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}


	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_simple,
					container, false);
			
			Button btnClickMeSkyline = (Button)rootView.findViewById(R.id.btnSimpleFragSkyline);
			btnClickMeSkyline.setOnClickListener(new OnClickListener(){
				
				public void onClick(View v){
					Toast.makeText(getActivity(), "You clicked me skyline", Toast.LENGTH_SHORT);
					
				}
			});
			
			
			return rootView;
		}
	}
}
