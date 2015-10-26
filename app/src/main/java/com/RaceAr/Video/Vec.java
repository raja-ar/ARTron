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

package com.RaceAr.Video;

public class Vec {

	public float v[] = new float[3];
	
	public Vec()
	{
		v[0] = 0.0f;
		v[1] = 0.0f;
		v[2] = 0.0f;
	}
	
	public Vec(float x, float y, float z)
	{
		v[0] = x;
		v[1] = y;
		v[2] = z;
	}
	
	public void Copy(Vec V1)
	{
		v[0] = V1.v[0];
		v[1] = V1.v[1];
		v[2] = V1.v[2];
	}
	
	public Vec Add(Vec V1)
	{
		Vec ReturnResult = new Vec();
		
		ReturnResult.v[0] = v[0] + V1.v[0];
		ReturnResult.v[1] = v[1] + V1.v[1];
		ReturnResult.v[2] = v[2] + V1.v[2];
		
		return ReturnResult;
	}
	
	public Vec Sub(Vec V1)
	{
		Vec ReturnResult = new Vec();
		
		ReturnResult.v[0] = v[0] - V1.v[0];
		ReturnResult.v[1] = v[1] - V1.v[1];
		ReturnResult.v[2] = v[2] - V1.v[2];
		
		return ReturnResult;
	}
	
	public void Mul(float Mul)
	{
		v[0] *= Mul;
		v[1] *= Mul;
		v[2] *= Mul;
	}
	
	public void Scale(float fScale)
	{
		v[0] *= fScale;
		v[1] *= fScale;
		v[2] *= fScale;
	}
	
	public Vec Cross(Vec V1)
	{
		Vec ReturnResult = new Vec();
		
		ReturnResult.v[0] = v[1] * V1.v[2] - v[2] * V1.v[1];
		ReturnResult.v[1] = v[2] * V1.v[0] - v[0] * V1.v[2];
		ReturnResult.v[2] = v[0] * V1.v[1] - v[1] * V1.v[0];
		
		return ReturnResult;
	}
	
	public float Dot(Vec V1)
	{
		return (v[0] * V1.v[0] + v[1] * V1.v[1] + v[2] * V1.v[2]);
	}
	
	public float Length()
	{
		return (float) (Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]));
	}
	
	public float Length2()
	{
		return (float) (Math.sqrt(v[0] * v[0] + v[1] * v[1]));
	}

	public void Normalise()
	{
		float d = Length();
		
		if(d != 0) {
			v[0] /= d;
			v[1] /= d;
			v[2] /= d;
		}
	}
	
	public void Normalise2()
	{
		float d = Length2();
		
		if(d != 0)
		{
			v[0] /= d;
			v[1] /= d;
		}
	}
	
	public Vec Orthogonal()
	{
		Vec ReturnResult = new Vec();
		
		ReturnResult.v[0] = v[1];
		ReturnResult.v[1] = -v[0];
		
		return ReturnResult;
	}
	
}
