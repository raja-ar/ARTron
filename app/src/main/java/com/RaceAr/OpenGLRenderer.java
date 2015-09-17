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

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.util.Log;

import com.RaceAr.Game.RaceArGame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

	RaceArGame Game = new RaceArGame();
	
	Context mContext;
	
	String Debug;
	StringBuffer sb = new StringBuffer(40);

	private int frameCount = 0;
	
	public OpenGLRenderer(Context context, int win_width, int win_height)
	{
		mContext = context;
		Log.e("RACEAR", "Renderer Constructor: Create Video Object");
		Debug = sb.append("Screen size = ").append(win_width).append(",").append(win_height).toString();
		Log.e("RACEAR", Debug);
		Game.updateScreenSize(win_width, win_height);
	}
	
	public void setUI_Handler(Handler handler)
	{
		Game.setUI_Handler(handler);
	}
	
	public void onTouch(float x, float y)
	{
		Game.addTouchEvent(x, y);
	}
	
	public void onPause()
	{
		Game.pauseGame();
	}
	
	public void onResume()
	{
		Game.resumeGame();
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		Log.e("RACEAR", "Renderer: Surface Created Do perspective");

	    //Game.initialiseGame(mContext, gl);
	    Game.drawSplash(mContext, gl);
	}

	
	@Override
	public void onSurfaceChanged(GL10 gl, int w, int h) {
		Log.e("RACEAR", "Renderer: Surface changed");
		sb=null;
		sb = new StringBuffer(40);
		Debug = sb.append("Screen size = ").append(w).append(",").append(h).toString();
		Log.e("RACEAR", Debug);
		Game.updateScreenSize(w, h);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {

		if(frameCount == 1)
		{
			Game.initialiseGame();
		}
		else if(frameCount > 1)
		{
			Game.RunGame();
		}

		frameCount++;
		
	}

	
}
