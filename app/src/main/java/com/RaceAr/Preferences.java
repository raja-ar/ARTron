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

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.RaceAr.widget.NetworkUtil;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Preferences extends PreferenceActivity {
	private InterstitialAd mInterstitialAd;

	@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        // TODO Auto-generated method stub
	        super.onCreate(savedInstanceState);
	        addPreferencesFromResource(R.layout.preferences);

		Preference button = (Preference)findPreference(getString(R.string.btn_dev));
		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				//code for what you want it to do
				Intent intent = new Intent(Preferences.this,DevActivity.class);
				startActivity(intent);
				return true;
			}
		});

		 if (NetworkUtil.getConnectivityStatus(Preferences.this) == 0) {

		 } else {
			 launchAd();
			 loadAd();
		 }


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
		mInterstitialAd = new InterstitialAd(this);
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

