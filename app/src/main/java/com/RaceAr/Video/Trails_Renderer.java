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

import android.content.Context;

import com.RaceAr.Game.Camera;
import com.RaceAr.R;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Trails_Renderer {

	static final float trail_top[] = {
			1.0f, 1.0f, 1.0f, .7f,
			1.0f, 1.0f, 1.0f, .7f,
			1.0f, 1.0f, 1.0f, .7f};
	private final float LX = 2.0f;
	private final float LY = 2.0f;
	private final float shadow_matrix[] =
		{
			LX * LY, 0.0f, 0.0f, 0.0f,
			0.0f, LX * LY, 0.0f, 0.0f,
			-LY, -LX, 0.0f, 0.0f,
			0.0f, 0.0f, 0.0f, LX * LY
		};
	GLTexture Trails;
	GL10 gl;
	FloatBuffer shadowFb;
	FloatBuffer trailtopfb;
	
	public Trails_Renderer(GL10 gl_in, Context context)
	{
		gl = gl_in;
		Trails = new GLTexture(gl,context,R.drawable.gltron_traildecal,GL10.GL_REPEAT, GL10.GL_CLAMP_TO_EDGE);
		shadowFb = GraphicUtils.ConvToFloatBuffer(shadow_matrix);
	}
	
	public void Render(TrailMesh mesh)
	{
		int i;

		FloatBuffer vertexFb = GraphicUtils.ConvToFloatBuffer(mesh.Vertices);
		FloatBuffer normalFb = GraphicUtils.ConvToFloatBuffer(mesh.Normals);
		FloatBuffer texFb = GraphicUtils.ConvToFloatBuffer(mesh.TexCoords);
		ShortBuffer indBb = GraphicUtils.ConvToShortBuffer(mesh.Indices);
		ByteBuffer colorFb = GraphicUtils.ConvToByteBuffer(mesh.Colors);

		statesNormal();

		for(i = 0; i < 2; i++) // change to 2 to draw shadows
		{
			if( i == 0)
				statesNormal();
			else
				statesShadow();

			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texFb);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexFb);
			gl.glNormalPointer(GL10.GL_FLOAT,	0, normalFb);
			gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, colorFb);

			gl.glDrawElements(GL10.GL_TRIANGLES, mesh.iUsed, GL10.GL_UNSIGNED_SHORT, indBb);

			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

			statesRestore();
		}

		gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_REPLACE);

	}

	private void statesShadow()
	{
//        gl.glDisable(GL10.GL_CULL_FACE);
//        gl.glDisable(GL10.GL_TEXTURE_2D);
//        gl.glDisable(GL10.GL_LIGHTING);

        gl.glEnable(GL10.GL_STENCIL_TEST);
		gl.glStencilOp(GL10.GL_REPLACE, GL10.GL_REPLACE, GL10.GL_REPLACE);
		gl.glStencilFunc(GL10.GL_GREATER, 1, 1);
//		gl.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
		gl.glEnable(GL10.GL_BLEND);
		gl.glColor4f(0.0f, 0.0f, 0.0f, 0.4f);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glPushMatrix();
		gl.glMultMatrixf(shadowFb);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

		gl.glDisable(GL10.GL_CULL_FACE);
        gl.glDisable(GL10.GL_TEXTURE_2D);
        gl.glDisable(GL10.GL_LIGHTING);
	}
	
	private void statesRestore()
	{
		gl.glDisable(GL10.GL_COLOR_MATERIAL);
		gl.glCullFace(GL10.GL_BACK);
		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glDisable(GL10.GL_TEXTURE_2D);
		gl.glDisable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_LIGHTING);
		//gl.glPolygonMode(GL10.GL_FRONT_AND_BACK, GL10.GL_FILL);
		gl.glDisable(GL10.GL_POLYGON_OFFSET_FILL);
		gl.glDisable(GL10.GL_STENCIL_TEST);
		gl.glPopMatrix();
	}

	private void statesNormal()
	{
		gl.glEnable(GL10.GL_POLYGON_OFFSET_FILL);
		gl.glPolygonOffset(1,1);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		//gl.glFrontFace(GL10.GL_CCW);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glDisable(GL10.GL_CULL_FACE);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, Trails.getTextureID());

		gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_DECAL);

		float black[] = { 0.0f, 0.0f, 0.0f, 1.0f};
		FloatBuffer fBlack = GraphicUtils.ConvToFloatBuffer(black);

		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10. GL_SPECULAR, fBlack);

		gl.glEnable(GL10.GL_COLOR_MATERIAL);

		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	}

	public void drawTrailLines(Segment segs[],int trail_offset, float trail_height, Camera cam)
	{
				
		int segOffset;

		if(trailtopfb == null)
			trailtopfb = GraphicUtils.ConvToFloatBuffer(trail_top);
		
		gl.glEnable(GL10.GL_LINE_SMOOTH);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDisable(GL10.GL_LIGHTING);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, trailtopfb);

		for(segOffset = 0; segOffset <= trail_offset; segOffset++)
		{
			// Dont change alpha based on dist yet
			gl.glColorPointer(4, GL10.GL_FLOAT, 0, trailtopfb);
			gl.glColor4f(1.0f, 1.0f, 1.0f, 0.4f);
			
			float tempvertex[] =
			{
				segs[segOffset].vStart.v[0], 
				segs[segOffset].vStart.v[1], 
				trail_height,
				
				segs[segOffset].vStart.v[0] + segs[segOffset].vDirection.v[0],
				segs[segOffset].vStart.v[1] + segs[segOffset].vDirection.v[1],
				trail_height
			};
			
			FloatBuffer fb = GraphicUtils.ConvToFloatBuffer(tempvertex);
			
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fb);
			gl.glDrawArrays(GL10.GL_LINES, 0, 2);
		}

		// TODO: Draw the final segment
//		gl.glColorPointer(4, GL10.GL_FLOAT, 0, trailtopfb);
//		
//		float tempvertex[] = 
//		{
//			segs[trail_offset].vStart.v[0],
//			segs[trail_offset].vStart.v[1],
//			trail_height
//		};
		
//		gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
		
		gl.glDisable(GL10.GL_BLEND);
		gl.glDisable(GL10.GL_LINE_SMOOTH);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
	
}
