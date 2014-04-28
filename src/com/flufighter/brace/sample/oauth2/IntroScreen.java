package com.flufighter.brace.sample.oauth2;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flufighter.brace.R;
import com.flufighter.brace.ui.ItemMenuActivity;
import com.google.api.client.auth.oauth2.Credential;

public class IntroScreen extends Activity {

	private SharedPreferences prefs;
	private OAuth2Helper oAuth2Helper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);

		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		oAuth2Helper = new OAuth2Helper(this.prefs);
		try {
			if (oAuth2Helper.loadCredential().getAccessToken() == null)
				startOauthFlow();
			else
				startMainScreen();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Starts the main screen .
	 * 
	 */
	private void startMainScreen() {
		startActivity(new Intent().setClass(this, ItemMenuActivity.class));
	}

	/**
	 * Starts the activity that takes care of the OAuth2 flow
	 * 
	 */
	private void startOauthFlow() {
		startActivity(new Intent().setClass(this,
				OAuthAccessTokenActivity.class));
	}

}
