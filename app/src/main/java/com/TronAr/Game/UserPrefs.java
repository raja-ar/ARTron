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

package com.TronAr.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPrefs {

	private static final float C_GRID_SIZES[] = {360.0f, 720.0f, 1440.0f};
	private static final float C_SPEED[] = {5.0f, 10.0f, 15.0f, 20.0f};
	// Pref defaults
	private final int C_PREF_FOLLOW_CAM = 1;
	private final int C_PREF_FOLLOW_CAM_FAR = 2;
	private final int C_PREF_FOLLOW_CAM_CLOSE = 3;
	private final int C_PREF_BIRD_CAM = 4;
	private final String C_DEFAULT_CAM_TYPE = "1";
	private Context mContext;
	private Camera.CamType mCameraType;
	
	private boolean mMusic;
	private boolean mSFX;
	
	private boolean mFPS;
	private boolean mDrawRecog;
	
	private int mNumOfPlayers;
	private float mGridSize;
	private float mSpeed;
	private int mPlayerColourIndex;
	
	public UserPrefs(Context ctx)
	{
		mContext = ctx;
		ReloadPrefs();
	}
	
	public void ReloadPrefs()
	{
		int cameraType;
		int gridIndex;
		int speedIndex;
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		cameraType = Integer.valueOf(prefs.getString("cameraPref",C_DEFAULT_CAM_TYPE));
		
		switch(cameraType)
		{
			case C_PREF_FOLLOW_CAM:
				mCameraType = Camera.CamType.E_CAM_TYPE_FOLLOW;
				break;
			case C_PREF_FOLLOW_CAM_FAR:
				mCameraType = Camera.CamType.E_CAM_TYPE_FOLLOW_FAR;
				break;
			case C_PREF_FOLLOW_CAM_CLOSE:
				mCameraType = Camera.CamType.E_CAM_TYPE_FOLLOW_CLOSE;
				break;
			case C_PREF_BIRD_CAM:
				mCameraType = Camera.CamType.E_CAM_TYPE_BIRD;
				break;
			default:
				mCameraType = Camera.CamType.E_CAM_TYPE_FOLLOW;
				break;
		}
		
		mMusic = prefs.getBoolean("musicOption", true);
		mSFX = prefs.getBoolean("sfxOption", true);
		mFPS = prefs.getBoolean("fpsOption", false);
		mNumOfPlayers = Integer.valueOf(prefs.getString("playerNumber", "4"));
		gridIndex = Integer.valueOf(prefs.getString("arenaSize", "1"));
		mGridSize = C_GRID_SIZES[gridIndex];
		speedIndex = Integer.valueOf(prefs.getString("gameSpeed", "1"));
		mSpeed = C_SPEED[speedIndex];
		mPlayerColourIndex = Integer.valueOf(prefs.getString("playerBike","0"));
		mDrawRecog = prefs.getBoolean("drawRecog", true);
	}
	
	public Camera.CamType CameraType()
	{
		
		return mCameraType;
	}
	
	public boolean PlayMusic()
	{
		return mMusic;
	}
	
	public boolean PlaySFX()
	{
		return mSFX;
	}
	
	public boolean DrawFPS()
	{
		return mFPS;
	}
	
	public int NumberOfPlayers()
	{
		return mNumOfPlayers;
	}
	
	public float GridSize()
	{
		return mGridSize;
	}
	
	public float Speed()
	{
		return mSpeed;
	}
	
	public int PlayerColourIndex()
	{
		return mPlayerColourIndex;
	}
	
	public boolean DrawRecognizer()
	{
		return mDrawRecog;
	}
}
