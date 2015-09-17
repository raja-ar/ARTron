/*
 * Copyright 2015 Azmeer Raja
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package com.RaceAr;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.RaceAr.widget.NetworkUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class RaceAr extends Activity {
    /** Called when the activity is first created. */
	private OpenGLView _View;
	
	private Boolean _FocusChangeFalseSeen = false;
	private Boolean _Resume = false;
	private InterstitialAd mInterstitialAd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
		WindowManager w = getWindowManager();
	    Display d = w.getDefaultDisplay();
	    int width = d.getWidth();
	    int height = d.getHeight();
	   
	    super.onCreate(savedInstanceState);
		if (NetworkUtil.getConnectivityStatus(RaceAr.this) == 0) {
		} else {
			launchAd();
			loadAd();
		}




	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
        _View = new OpenGLView(this, width, height);
        setContentView(_View);

    }
    
    
    @Override
    public void onPause() {
    	_View.onPause();
    	super.onPause();
    }
    
    @Override
    public void onResume() {
    	if(!_FocusChangeFalseSeen)
    	{
    		_View.onResume();
    	}
    	_Resume = true;
    	super.onResume();
    }
    
    @Override
    public void onWindowFocusChanged(boolean focus) {
    	if(focus)
    	{
    		if(_Resume)
    		{
    			_View.onResume();
    		}
    		
    		_Resume = false;
    		_FocusChangeFalseSeen = false;
    	}
    	else
    	{
    		_FocusChangeFalseSeen = true;
    	}
    }   
    
    //open menu when key pressed
     public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
        	this.startActivity(new Intent(this, Preferences.class));
        }
        return super.onKeyUp(keyCode, event);
    }
	private void loadAd() {
		AdRequest adRequest = new AdRequest.Builder()
				.build();

		mInterstitialAd.loadAd(adRequest);
	}

	private void showAd() {
		if (mInterstitialAd.isLoaded()) {
			mInterstitialAd.show();
		} else {
			Log.d("Ad", "Not Loaded");
		}
	}

	private void launchAd() {
		// Create the InterstitialAd and set the adUnitId.
		mInterstitialAd = new InterstitialAd(this);
		// Defined in res/values/strings.xml
		mInterstitialAd.setAdUnitId(getString(R.string.race_ad));

		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				super.onAdLoaded();
				showAd();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				super.onAdFailedToLoad(errorCode);
				String msg = String.format("onFaildtoLoad (%s)", getErrorReson(errorCode));
			}

			@Override
			public void onAdClosed() {
				super.onAdClosed();
			}
		});

	}
	private String getErrorReson(int errcode) {
		String errReason = "";
		switch (errcode) {
			case AdRequest.ERROR_CODE_INTERNAL_ERROR:
				errReason = "Internal Error";
				break;
			case AdRequest.ERROR_CODE_INVALID_REQUEST:
				errReason = "invalid request";
				break;
			case AdRequest.ERROR_CODE_NETWORK_ERROR:
				errReason = "Network err";
				break;
			case AdRequest.ERROR_CODE_NO_FILL:
				errReason = "No Fill";
				break;
		}
		return errReason;
	}
}