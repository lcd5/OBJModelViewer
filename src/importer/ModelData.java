package importer;

import java.util.Vector;

public class ModelData 
{
	Vector<Short> myFaceIndices=new Vector<Short>();
	Vector<Short> myUVIndices=new Vector<Short>();
	Vector<Short> myNormalIndices=new Vector<Short>();
	
	Vector<Float> myVertices=new Vector<Float>();
	Vector<Float> myNormals=new Vector<Float>();
	Vector<Float> myUVs=new Vector<Float>();
	
	String myMaterialName = "";
	
	public String getMaterialName()
	{
		return myMaterialName;
	}
	
	public Vector<Float> getVertices()
	{
		return myVertices;
	}
	
	public Vector<Float> getNormals()
	{
		return myNormals;
	}
	
	public Vector<Float> getUVs()
	{
		return myUVs;
	}
	
	public Vector<Short> getFaceIndices()
	{
		return myFaceIndices;
	}
	
	public Vector<Short> getUVIndices()
	{
		return myUVIndices;
	}
	
	public Vector<Short> getNormalIndices()
	{
		return myNormalIndices;
	}
}
