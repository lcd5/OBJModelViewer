package com.example.objmodelviewer;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

import importer.*;

public class Scene 
{
	private Vector<MeshNode> myMeshNodes;
	
	void update(Importer importer)
	{
		Vector<ModelData> models = importer.getModels();
		myMeshNodes = new Vector<MeshNode>();
		
		for(int i=0; i< models.size(); i++)
		{
			MeshNode node = new MeshNode();
			String mtlName = models.get(i).getMaterialName();
			node.setMaterial( importer.getMaterial(mtlName));
			
			node.setVertexBuffer( models.get(i).getVertices() );
			node.setIndexBuffer( models.get(i).getFaceIndices() );
			node.setUVBuffer(models.get(i).getUVs(), models.get(i).getUVIndices());
			node.setNormalBuffer(models.get(i).getNormals(), models.get(i).getNormalIndices());
			
			myMeshNodes.add(node);
		}
		
	}
	void renderAll( GL10 gl )
	{
		for(int i=0; i<myMeshNodes.size(); i++)
		{
			myMeshNodes.get(i).draw(gl);
		}
	}
}
