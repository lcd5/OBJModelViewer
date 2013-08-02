package com.example.objmodelviewer;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import importer.Material;

public class MeshNode 
{
	private Material myMaterial;
	
	void draw(GL10 gl)
	{
		myMaterial.getAlpha();
	}
	
	void setMaterial(Material material)
	{
		myMaterial = material;
	}
	
	void setVertexBuffer( Vector<Float> vertices)
	{
		
	}
	
	void setIndexBuffer( Vector<Short> indices)
	{
		
	}
}
