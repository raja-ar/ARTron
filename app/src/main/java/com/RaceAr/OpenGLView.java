/*
 *    Copyright 2015 Azmeer Raja
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
import android.view.MotionEvent;

public class OpenGLView extends GLSurfaceView {
	
	private OpenGLRenderer _renderer;
	
	private float _x = 0;
	private float _y = 0;
	
	public OpenGLView(Context context,int width, int height) {
		super(context);
		_renderer = new OpenGLRenderer(context, width, height);
		setRenderer(_renderer);
	}
	
	public void setUI_Handler(Handler handler)
	{
		_renderer.setUI_Handler(handler);
	}
	
	public void onPause()
	{
		_renderer.onPause();
	}
	
	public void onResume()
	{
		_renderer.onResume();
	}
	
	public boolean onTouchEvent(final MotionEvent event) {

		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			_x = event.getX();
			_y = event.getY();
			_renderer.onTouch(_x, _y);
			
		}
		
		return true;
	}
}
