package com.example.noteapp;

import com.parse.Parse;
import com.parse.ParseObject;

import android.app.Application;

public class NoteAppApplication extends Application {
	
	@Override
	public void onCreate() {
	    super.onCreate();
	 
	    Parse.initialize(this, "saIQWlbzHC5G90hwMY0FdjnuN2dQInrRGMwuCMjy", "bN0TkD4r0ELzZaWX9zlNqoYJnnWUDyK2qSsOTDQT");
	}
	
	
	

}
