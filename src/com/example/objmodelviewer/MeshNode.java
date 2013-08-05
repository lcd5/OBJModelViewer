package com.example.objmodelviewer;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import importer.Material;

public class MeshNode 
{
	private Material myMaterial;
	private FloatBuffer myVertexBuffer;
	private ShortBuffer myIndexBuffer;
	private FloatBuffer myNormalBuffer;
	private FloatBuffer myUVBuffer;
	
	private int myIndicesSize;
	
	void draw(GL10 gl)
	{
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		
		if(myMaterial != null)
		{
			FloatBuffer a = myMaterial.getAmbientColorBuffer();
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT,a);
		}
		
		if(myNormalBuffer != null)
		{
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);
		}
		
		//gl.glFrontFace(GL10.GL_CW);
		
		gl.glDrawElements(GL10.GL_TRIANGLES,
				myIndicesSize, GL10.GL_UNSIGNED_SHORT, myIndexBuffer);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
	
	void setMaterial(Material material)
	{
		myMaterial = material;
	}
	
	void setVertexBuffer( Vector<Float> vertices)
	{
		ByteBuffer bBuf = ByteBuffer.allocateDirect(vertices.size()*4);
		bBuf.order(ByteOrder.nativeOrder());
		myVertexBuffer = bBuf.asFloatBuffer();
		
		for(int i=0; i< vertices.size(); i++)
			myVertexBuffer.put(vertices.get(i));
		
		myVertexBuffer.position(0);
	}
	
	void setIndexBuffer( Vector<Short> indices)
	{
		myIndicesSize = indices.size();
		
		ByteBuffer fBuf = ByteBuffer.allocateDirect(indices.size() * 2);
		fBuf.order(ByteOrder.nativeOrder());
		myIndexBuffer = fBuf.asShortBuffer();
		
		for(int i=0; i<indices.size(); i++)
			myIndexBuffer.put(indices.get(i));
		
		myIndexBuffer.position(0);
	}
	
	void setNormalBuffer(Vector<Float> normals, Vector<Short> normalIndices)
	{
		ByteBuffer bBuf = ByteBuffer.allocateDirect(normalIndices.size()*4*3);
		bBuf.order(ByteOrder.nativeOrder());
		myNormalBuffer = bBuf.asFloatBuffer();
		
		for(int i=0; i< normalIndices.size(); i++)
		{
			float x=normals.get(normalIndices.get(i)*3);
			float y=normals.get(normalIndices.get(i)*3+1);
			float z=normals.get(normalIndices.get(i)*3+2);
			myNormalBuffer.put(x);
			myNormalBuffer.put(y);
			myNormalBuffer.put(z);
		}
		
		myNormalBuffer.position(0);
	}
	
	void setUVBuffer(Vector<Float> uvs, Vector<Short> uvsIndices)
	{
		ByteBuffer bBuf = ByteBuffer.allocateDirect(uvsIndices.size()*4*2);
		bBuf.order(ByteOrder.nativeOrder());
		myUVBuffer = bBuf.asFloatBuffer();
		
		for(int i=0; i< uvsIndices.size(); i++)
		{
			float u=uvs.get(uvsIndices.get(i)*2);
			float v=uvs.get(uvsIndices.get(i)*2+1);
			myUVBuffer.put(u);
			myUVBuffer.put(v);
		}
		
		myUVBuffer.position(0);
	}
}
