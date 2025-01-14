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
import android.util.Log;

import com.RaceAr.Video.Material.ColourType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Model {

	// member vars
	private Material mMaterials;
	private int MaterialsCount[];
	private float rawVertex[];
	private FloatBuffer mVertexBuffer;
	private FloatBuffer mNormalBuffer;
	private ShortBuffer mIndicesBuffer[];
	private int mNumOfVertices;
	private Vec mBBoxMin;
	private Vec mBBoxSize;
	private float mBBoxfRadius;
	
	String Debug;
	StringBuffer sb = new StringBuffer(40);
	
	private class face {
		int vertex[] = new int[3];
		int normal[] = new int[3];
		int material;
	}
	
	private class vec3 {
		float v[] = {0.0f, 0.0f, 0.0f};
	}
	
	public Model(Context ctx, int resId)
	{
		readMesh(ctx,resId);
	}
	
	private void readMesh(Context ctx, int resId)
	{
		String buf;
		String[] temp;
		String[] temp2;
		String restemp;
		int resourceId;
		int faceIndex = -1;
		ArrayList<vec3> mVertices = new ArrayList<vec3>();
		ArrayList<vec3> mNormals = new ArrayList<vec3>();
		ArrayList<face> mFaces = new ArrayList<face>();
		int numOfVertices = 0;
		int numOfNormals = 0;
		int numOfFaces = -1;
		vec3 Vertex;
		face currFace;
		face faceArray[];

		Log.e("RACEAR", "Start Read Mesh");
		
		InputStream inputStream = ctx.getResources().openRawResource(resId);
		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		
		try
		{
			while((buf = buffreader.readLine()) != null) {
				
				if(buf != null && (buf.length() > 1)) {
					temp = buf.split(" ");
					switch(buf.charAt(0)) {
						case 'm':
							//obtain the resource id
							restemp = temp[1].substring(0, (temp[1].length()-4));
							resourceId = ctx.getResources().getIdentifier(restemp.trim(), "raw", ctx.getPackageName());
							if(mMaterials == null) {
								mMaterials = new Material(ctx,resourceId);
							} else {
								mMaterials.AddMaterial(ctx,resourceId);
							}
							break;
						case 'u':
							//search all materials for mesh for name
							int result;
							result = mMaterials.GetIndex(temp[1]); 
							if(result > -1) {
								faceIndex = result; 
							}
							break;
						case 'v':
							// populate vertex, normal and texture coords
							switch(buf.charAt(1)) {
								case ' ':
									mVertices.add(new vec3());
									Vertex = mVertices.get(numOfVertices);
									Vertex.v[0] = Float.valueOf(temp[1].trim()).floatValue();
									Vertex.v[1] = Float.valueOf(temp[2].trim()).floatValue();
									Vertex.v[2] = Float.valueOf(temp[3].trim()).floatValue();
									numOfVertices++;
									break;
								case 'n':
									mNormals.add(new vec3());
									Vertex = mNormals.get(numOfNormals);
									Vertex.v[0] = Float.valueOf(temp[1].trim()).floatValue();
									Vertex.v[1] = Float.valueOf(temp[2].trim()).floatValue();
									Vertex.v[2] = Float.valueOf(temp[3].trim()).floatValue();
									numOfNormals++;
									break;
								case 't':
									// ignore textures
									break;
							}
							break;
						case 'f':
							// Load face data
							numOfFaces++;
							mFaces.add(new face());
							currFace = mFaces.get(numOfFaces);
							temp2 = temp[1].split("//"); 
							currFace.vertex[0] = Integer.parseInt(temp2[0].trim());
							currFace.normal[0] = Integer.parseInt(temp2[1].trim());
							temp2 = temp[2].split("//");
							currFace.vertex[1] = Integer.parseInt(temp2[0].trim());
							currFace.normal[1] = Integer.parseInt(temp2[1].trim());
							temp2 = temp[3].split("//");
							currFace.vertex[2] = Integer.parseInt(temp2[0].trim());
							currFace.normal[2] = Integer.parseInt(temp2[1].trim());
							currFace.material = faceIndex;
							break;
					}
				}
			}
		}
		catch (IOException e) 
		{
			Log.e("RACEAR", "Mesh file access error");
		}
		
		faceArray = new face[mFaces.size()];
		faceArray = mFaces.toArray(faceArray);
		int nVertices = 0;
		
		// Create an array of the size of the total number of materials
		int numOfMaterials = mMaterials.GetNumber();
		MaterialsCount = new int[numOfMaterials];
		
		for(int x=0; x<numOfMaterials; x++) {
			MaterialsCount[x] = 0;
		}
		
		for(int x=0; x<numOfFaces; x++) {
			MaterialsCount[faceArray[x].material] += 1;
		}
		
		// Combine vectors and normals for each vertex
		int lookup[][] = new int[numOfVertices][numOfNormals];
		
		for(int i=0; i<numOfVertices; i++) {
			for(int j=0; j<numOfNormals; j++) {
				lookup[i][j] = -1;
			}
		}

		Log.e("RACEAR", "CalVertices...");
		for(int i=0; i<numOfFaces; i++) {
			for(int j=0; j<3; j++) {
				int vertex = faceArray[i].vertex[j] - 1;
				int normal = faceArray[i].normal[j] - 1;
				if(lookup[vertex][normal] == -1) {
					lookup[vertex][normal] = nVertices;
					nVertices++;
				}
			}
		}

		Log.e("RACEAR", "Building Vertex Array");
		
		// Now that we have loaded all the data build the vertexarray
		mNumOfVertices = nVertices;
		
		ByteBuffer vbb = ByteBuffer.allocateDirect(mNumOfVertices * 3 * 4);
		vbb.order(ByteOrder.nativeOrder());
		mVertexBuffer = vbb.asFloatBuffer();
		
		ByteBuffer nbb = ByteBuffer.allocateDirect(mNumOfVertices * 3 * 4);
		nbb.order(ByteOrder.nativeOrder());
		mNormalBuffer = nbb.asFloatBuffer();
		
		float tempVertex[] = new float[mNumOfVertices * 3];
		float tempnormals[] = new float[mNumOfVertices * 3];
		vec3 VertexArr[] = new vec3[mVertices.size()];
		VertexArr = mVertices.toArray(VertexArr);
		vec3 NormalArr[] = new vec3[mNormals.size()];
		NormalArr = mNormals.toArray(NormalArr);
		
		for(int i=0;i<numOfVertices;i++) {
			for(int j=0;j<numOfNormals; j++) {
				int vertex = lookup[i][j];
				if(vertex != -1) {
					for(int k=0; k<3; k++) {
						tempVertex[3 * vertex + k] = VertexArr[i].v[k];
						tempnormals[3 * vertex + k] = NormalArr[i].v[k];
					}
				}
			}
		}
		
		rawVertex = tempVertex;
		
		mVertexBuffer.put(tempVertex);
		mVertexBuffer.position(0);
		mNormalBuffer.put(tempnormals);
		mNormalBuffer.position(0);
		
		// Create the indices (per Material)
		mIndicesBuffer = new ShortBuffer[numOfMaterials];
		int tempface[] = new int[numOfMaterials];

		ArrayList<short[]> Indices = new ArrayList<short[]>();
		short tempIndices[];
		
		for(int i=0;i<numOfMaterials;i++) {
			ByteBuffer ibb = ByteBuffer.allocateDirect(2 * 3 * MaterialsCount[i]);
			ibb.order(ByteOrder.nativeOrder());
			mIndicesBuffer[i] = ibb.asShortBuffer();
			tempface[i] = 0;
			Indices.add(new short[MaterialsCount[i] * 3]);
		}
		
		for(int i=0;i<numOfFaces;i++) {
			int materialtemp = faceArray[i].material;
			tempIndices = Indices.get(materialtemp);
			for(int j=0;j<3;j++) {
				int vertex = faceArray[i].vertex[j] - 1;
				int normal = faceArray[i].normal[j] - 1;
				tempIndices[3 * tempface[materialtemp] + j] = (short)lookup[vertex][normal];
			}
			tempface[materialtemp] += 1;
		}
		
		for(int i=0;i<numOfMaterials;i++) {
			tempIndices = Indices.get(i);
			mIndicesBuffer[i].put(tempIndices);
			mIndicesBuffer[i].position(0);
		}
		
		computeBBox();
		
	}

	public void Draw(GL10 gl, float ambient_color[], float diffuse_color[])
	{
		mMaterials.SetMaterialColour("Hull", ColourType.E_AMBIENT, ambient_color);
		mMaterials.SetMaterialColour("Hull", ColourType.E_DIFFUSE, diffuse_color);
		Draw(gl);
	}
	
	public void Draw(GL10 gl)
	{
		int MaterialCount = mMaterials.GetNumber();
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);

		for(int i=0; i<MaterialCount;i++) {
			if(mIndicesBuffer[i].capacity() > 0) {

				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mMaterials.GetAmbient(i));
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mMaterials.GetDiffuse(i));
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mMaterials.GetSpecular(i));
				gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mMaterials.GetShininess(i));
				
				gl.glDrawElements(GL10.GL_TRIANGLES, mIndicesBuffer[i].capacity(),
						GL10.GL_UNSIGNED_SHORT, mIndicesBuffer[i]);
			}
		}
		
	}
	
	public void Explode(GL10 gl, float radius)
	{
		int i,j,k;
		
		float normal[] = new float[3];
		float vertex[] = new float[3];
		FloatBuffer Normals;
		FloatBuffer Vertex;
		short indices;

		final int EXP_VECTORS = 10;
		
		float vectors[][] = {
				{ 0.03f, -0.06f, -0.07f }, 
			    { 0.04f, 0.08f, -0.03f }, 
			    { 0.10f, -0.04f, -0.07f }, 
			    { 0.06f, -0.09f, -0.10f }, 
			    { -0.03f, -0.05f, 0.02f }, 
			    { 0.07f, 0.08f, -0.00f }, 
			    { 0.01f, -0.04f, 0.10f }, 
			    { -0.01f, -0.07f, 0.09f }, 
			    { 0.01f, -0.01f, -0.09f }, 
			    { -0.04f, 0.04f, 0.02f }
		};
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		
		for(i = 0; i <  mMaterials.GetNumber(); i++)
		{
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mMaterials.GetAmbient(i));
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mMaterials.GetDiffuse(i));
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mMaterials.GetSpecular(i));
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mMaterials.GetShininess(i));
			
			for(j=0; j < (mIndicesBuffer[i].capacity() / 3); j++)
			{
				indices = mIndicesBuffer[i].get(3 * j);
				
				normal[0] = mNormalBuffer.get(3 * indices);
				normal[1] = mNormalBuffer.get((3 * indices) + 1);
				normal[2] = mNormalBuffer.get((3 * indices) + 2);
						
				gl.glPushMatrix();
				gl.glTranslatef(
						radius * (normal[0] + vectors[j % EXP_VECTORS][0]),
						radius * (normal[1] + vectors[j % EXP_VECTORS][1]),
						Math.abs(radius * (normal[2] + vectors[j % EXP_VECTORS][2])) );
				
				for(k=0; k < 3; k++)
				{
					indices = mIndicesBuffer[i].get(3 * j + k);
				
					normal[0] = mNormalBuffer.get(3 * indices);
					normal[1] = mNormalBuffer.get((3 * indices) + 1);
					normal[2] = mNormalBuffer.get((3 * indices) + 2);
					
					Normals = GraphicUtils.ConvToFloatBuffer(normal);
					
					vertex[0] = rawVertex[3 * indices];
					vertex[1] = rawVertex[(3 * indices) + 1];
					vertex[2] = rawVertex[(3 * indices) + 2];
					
					Vertex = GraphicUtils.ConvToFloatBuffer(vertex);
					
					gl.glVertexPointer(3, GL10.GL_FLOAT, 0, Vertex);
					gl.glNormalPointer(GL10.GL_FLOAT, 0, Normals);
					gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
				}
				gl.glPopMatrix();
			}
		}
	}
	
	private void computeBBox()
	{
		int i, j;
		Vec vMin = new Vec(rawVertex[0],rawVertex[1],rawVertex[2]);
		Vec vMax = new Vec(rawVertex[0],rawVertex[1],rawVertex[2]);
		Vec vSize = new Vec();
		
		for(i=0; i<mNumOfVertices; i++) {
			for(j=0; j<3; j++) {
				if(vMin.v[j] > rawVertex[3 * i + j]) {
					vMin.v[j] = rawVertex[3 * i + j];
				}
				if(vMax.v[j] < rawVertex[3 * i + j]) {
					vMax.v[j] = rawVertex[3 * i + j];
				}
			}
		}
		
		vSize = vMax.Sub(vMin);
		mBBoxMin = vMin;
		mBBoxSize = vSize;
		mBBoxfRadius = vSize.Length() / 10.0f;
		
	}
	
	public Vec GetBBoxSize()
	{
		return mBBoxSize;
	}
	
	public Vec GetBBoxMin()
	{
		return mBBoxMin;
	}

	public float GetBBoxRadius()
	{
		return mBBoxfRadius;
	}
}
