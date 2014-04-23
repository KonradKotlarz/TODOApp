package com.example.todoapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todoapp.model.DatabaseHandler;

public class LoginActivity extends Activity implements OnClickListener {
	public static final String DEBUG_TEXT = "LoginActivity";
	
	EditText loginEditText, passwordLoginText;
	Button loginButton;
	TextView invalidLoginInputTextView, authenticationFailedTextView;
	
	private DatabaseHandler db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginEditText = (EditText) findViewById(R.id.loginEditText);
		passwordLoginText = (EditText) findViewById(R.id.passwordEditText);
		loginButton = (Button) findViewById(R.id.loginButton);
		invalidLoginInputTextView = (TextView) findViewById(R.id.invalidLoginInputTextView);
		authenticationFailedTextView = (TextView) findViewById(R.id.authenticationFailedTextView);
		
		loginButton.setOnClickListener(this);
		
		
	}
	
	@Override
	public void onClick(View v) {
		invalidLoginInputTextView.setVisibility(TextView.GONE);
		authenticationFailedTextView.setVisibility(TextView.GONE);
		
		if(validInput()) {
			db = new DatabaseHandler(this);
			
			// Success
			if(db.getUser(loginEditText.getText().toString(), passwordLoginText.getText().toString())) {
				SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_data", 0); // 0 for private mode
				sharedPreferences.edit().putString("username", loginEditText.getText().toString()).commit();
			} else {
				authenticationFailedTextView.setVisibility(TextView.VISIBLE);
			}
			
		} else {
			invalidLoginInputTextView.setVisibility(TextView.VISIBLE);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	private boolean validInput() {
		return (!loginEditText.getText().toString().equals("") && !passwordLoginText.getText().toString().equals(""));
	}
	
}
