package com.flufighter.brace.ui;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.os.Bundle;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.flufighter.brace.ws.remote.oauth2.OAuth2Helper;
import com.flufighter.brace.ws.remote.oauth2.Oauth2Params;
import com.flufighter.brace.ws.remote.oauth2.ProcessTokenTask;

@SuppressLint("SetJavaScriptEnabled")
public class LoginActivity extends Activity implements
		ProcessTokenTask.Callback {
	private static String TAG = LoginActivity.class.getSimpleName();
	public static final String ARG_AUTHENTICATE = "authenticate";
	private SharedPreferences prefs;
	private OAuth2Helper oAuth2Helper;
	private WebView webview;
	private boolean handled = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, " onCreate");
		super.onCreate(savedInstanceState);

		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);

		oAuth2Helper = new OAuth2Helper(this.prefs);

		Bundle extra = getIntent().getExtras();
		if (extra != null) {
			boolean reAuthentication = extra
					.getBoolean(ARG_AUTHENTICATE, false);
			if (reAuthentication)
				// when a token was saved, but is not valid, so we need to force
				// reauthentication
				startOauthFlow();

		} else {
			try {
				if (oAuth2Helper.loadCredential().getAccessToken() == null && true) {

					// when no token has been saved previously
					startOauthFlow();

				} else
					// when a token has been saved previously
					startMainScreen();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void onStart() {
		Log.i(TAG, " onStart");

		super.onStart();

	}

	/**
	 * Starts the main screen .
	 * 
	 */
	private void startMainScreen() {
		Log.i(TAG, " startMainScreen");

		startActivity(new Intent().setClass(this, ItemMenuActivity.class));
	}

	/**
	 * Starts the webview that takes care of the OAuth2 flow
	 * 
	 */
	private void startOauthFlow() {
		Log.i(TAG, " startOauthFlow");

		webview = new WebView(this);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setVisibility(View.VISIBLE);
		setContentView(webview);

		String authorizationUrl = oAuth2Helper.getAuthorizationUrl();
		Log.i(TAG, "Using authorizationUrl = " + authorizationUrl);

		handled = false;

		webview.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap bitmap) {
				Log.d(TAG, "onPageStarted : " + url + " handled = " + handled);
			}

			@Override
			public void onPageFinished(final WebView view, final String url) {
				Log.d(TAG, "onPageFinished : " + url + " handled = " + handled);

				if (url.startsWith(Oauth2Params.JAWBONE_OAUTH2.getRederictUri())) {
					webview.setVisibility(View.INVISIBLE);

					if (!handled) {
						new ProcessTokenTask(url, oAuth2Helper,
								LoginActivity.this).execute();
					}
				} else {
					webview.setVisibility(View.VISIBLE);
				}
			}

		});

		webview.loadUrl(authorizationUrl);
	}

	@Override
	public void onAuthentication(boolean handled, boolean startActivity) {
		Log.i(TAG, " onAuthentication");

		startMainScreen();
		finish();
	}

}
