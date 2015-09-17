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

package com.RaceAr.Video;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class GraphicUtils {

	public static vec2 vec2Add(vec2 Result, vec2 v1, vec2 v2)
	{
		Result.v[0] = v1.v[0] + v2.v[0];
		Result.v[1] = v1.v[1] + v2.v[1];

		return Result;
	}
	
	public static FloatBuffer ConvToFloatBuffer(float buf[])
	{
		FloatBuffer ReturnBuffer;

		ByteBuffer vbb = ByteBuffer.allocateDirect(buf.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		ReturnBuffer = vbb.asFloatBuffer();
		ReturnBuffer.put(buf);
		ReturnBuffer.position(0);

		return ReturnBuffer;
	}
	
	public static ByteBuffer ConvToByteBuffer(byte buf[])
	{
		ByteBuffer ReturnBuffer = ByteBuffer.allocateDirect(buf.length);

		ReturnBuffer.order(ByteOrder.nativeOrder());
		ReturnBuffer.put(buf);
		ReturnBuffer.position(0);

		return ReturnBuffer;
	}

	public static ShortBuffer ConvToShortBuffer(short buf[])
	{
		ShortBuffer ReturnBuffer = ShortBuffer.allocate(buf.length);
		ReturnBuffer.put(buf);
		ReturnBuffer.position(0);
		return ReturnBuffer;
	}

	public class vec2 {
		public float v[] = new float[2];
	}

	public class vec3 {
		public float v[] = new float[3];
	}

	public class vec4 {
		public float v[] = new float[4];
	}
	
}