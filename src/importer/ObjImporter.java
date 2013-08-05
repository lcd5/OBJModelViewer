package importer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.Vector;
import android.content.res.AssetManager;

public class ObjImporter implements Importer
{
	Vector<ModelData> myModels = new Vector<ModelData>();
	Vector<Material> myMaterials=new Vector<Material>();
	AssetManager myAssetManager;
	
	Vector<Float> myVertices=new Vector<Float>();
	Vector<Float> myNormals=new Vector<Float>();
	Vector<Float> myUVs=new Vector<Float>();
	
	@Override
	public Vector<ModelData> getModels()
	{
		return myModels;
	}
	
	@Override
	public Material getMaterial(String mtlName)
	{
		for(int i=0; i< myMaterials.size(); i++)
		{
			if( myMaterials.get(i).myName == mtlName )
				return myMaterials.get(i);
		}
		
		return null;
	}
	
	@Override
	public void importFile(AssetManager manager, String fileName)
	{
		try
		{
			myAssetManager = manager;
			importStream( manager.open(fileName) );
		}
		catch(IOException e){
		}
	}
	
	
	@Override
	public void importFile(String fileName)
	{
		try
		{
			importStream( new FileInputStream(fileName) );
		}
		catch(IOException e){
		}
	}

	public void importStream(InputStream stream)
	{
		BufferedReader reader=null;
		String lineBuffer = null;
		
		reader = new BufferedReader(new InputStreamReader(stream));
		
		// Parser the file content.
		//
		try{
			while( (lineBuffer = reader.readLine()) != null )
			{
				lineBuffer.trim();
				
				if(lineBuffer.startsWith("vn"))
					parserNormalLine(lineBuffer);
				else if(lineBuffer.startsWith("vt"))
					parserUVLine(lineBuffer);
				else if(lineBuffer.startsWith("v"))
					parserVertexLine(lineBuffer);
				else if(lineBuffer.startsWith("f"))
					parserFLine(lineBuffer);
				else if(lineBuffer.startsWith("usemtl"))
					parserMaterial(lineBuffer);
				else if(lineBuffer.startsWith("mtllib"))
					readMaterial(lineBuffer);
			}
		}
		catch(IOException e){
		}
		
		for(int i=0; i<myModels.size(); i++)
		{
			myModels.get(i).myVertices = myVertices;
			myModels.get(i).myNormals = myNormals;
			myModels.get(i).myUVs = myUVs;
		}
	}
	
	private ModelData getCurrentModel()
	{
		if( myModels.size()== 0 )
			myModels.add( new ModelData() );
			
		return myModels.lastElement();	
	}
	private void parserFLine(String buffer)
	{
		String []token = buffer.split("[ ]+");
		int len = token.length;
		
		// Only support one triangle per face
		//
		if(len < 4)
			return;
		
		ModelData currentModel = getCurrentModel();
		
		// case f: v
		//
		if( token[1].matches("[0-9]+"))
		{
			for(int i=1; i<len; i++)
			{
				Short index = Short.valueOf(token[i]);
				index--;
				currentModel.myFaceIndices.add(index);
			}
		}
		//case f: v/vt
		//
		else if(token[1].matches("[0-9]+/[0-9]+"))
		{
			for(int i=1; i<len; i++)
			{
				String []vUV = token[i].split("/");
				
				Short vIndex = Short.valueOf(vUV[0]);
				vIndex--;
				currentModel.myFaceIndices.add(vIndex);
				
				Short uvIndex = Short.valueOf(vUV[1]);
				uvIndex--;
				currentModel.myUVIndices.add(uvIndex);
			}
		}
		// case f:v//vn
		//
		else if(token[1].matches("[0-9]+//[0-9]+"))
		{
			for(int i=1; i<len; i++)
			{
				String []vn = token[i].split("//");
			
				Short vIndex = Short.valueOf(vn[0]);
				vIndex--;
				currentModel.myFaceIndices.add(vIndex);
			
				Short nIndex = Short.valueOf(vn[1]);
				nIndex--;
				currentModel.myNormalIndices.add(nIndex);
			}
		}
		// case f: v/vt/vn
		//
		else if(token[1].matches("[0-9]+/[0-9]+/[0-9]+"))
		{
			for(int i=1; i<len; i++)
			{
				String []vUVn = token[i].split("/");
			
				Short vIndex = Short.valueOf(vUVn[0]);
				vIndex--;
				currentModel.myFaceIndices.add(vIndex);
			
				Short uvIndex = Short.valueOf(vUVn[1]);
				uvIndex--;
				currentModel.myUVIndices.add(uvIndex);
			
				Short nIndex = Short.valueOf(vUVn[2]);
				nIndex--;
				currentModel.myNormalIndices.add(nIndex);
			}
		}
	}
	
	private void parserVertexLine(String lineBuffer)
	{
		String [] tokens=lineBuffer.split("[ ]+"); 
		int len=tokens.length; 
		for(int i=1; i<len; i++)
		{ 
			myVertices.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void parserNormalLine(String lineBuffer)
	{
		String [] tokens=lineBuffer.split("[ ]+"); 
		int len=tokens.length; 
		for(int i=1; i<len; i++)
		{ 
			myNormals.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void parserUVLine(String lineBuffer)
	{
		String [] tokens=lineBuffer.split("[ ]+"); 
		int len=tokens.length; 
		for(int i=1; i<len; i++)
		{ 
			myUVs.add(Float.valueOf(tokens[i]));
		}
	}
	
	private void parserMaterial(String lineBuffer)
	{
		String mtlName=lineBuffer.split("[ ]+",2)[1];
		ModelData model = new ModelData();
		myModels.add(model);
		model.myMaterialName = mtlName;
	}
	
	private void readMaterial(String lineBuffer)
	{
		String mtlFileName=lineBuffer.split("[ ]+")[1];
		BufferedReader reader=null;
		
		try 
		{ 
			reader = new BufferedReader(new InputStreamReader(myAssetManager.open(mtlFileName)));
		} 		
		catch(IOException e){
		}
		
		if(reader != null)
		{
			
			try
			{
				Material currentMtl=null;
				
				while( (lineBuffer = reader.readLine()) != null )
				{
					if(lineBuffer.startsWith("newmtl"))
					{
						if(currentMtl != null)
							myMaterials.add(currentMtl);
						
						currentMtl = new Material();
						String mtName = lineBuffer.split("[ ]+", 2)[1];
						currentMtl.myName = mtName; 
					}
					else if(lineBuffer.startsWith("Ka"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.setAmbientColor(Float.parseFloat(str[1]), 
								Float.parseFloat(str[2]), Float.parseFloat(str[3]));
					}
					else if(lineBuffer.startsWith("Kd"))
					{
						String[] str = lineBuffer.split("[ ]+");
						currentMtl.setDiffuseColor(Float.parseFloat(str[1]), 
								Float.parseFloat(str[2]), Float.parseFloat(str[3]));
					}
					else if(lineBuffer.startsWith("Ks"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.setSpecularColor(Float.parseFloat(str[1]), 
								Float.parseFloat(str[2]), Float.parseFloat(str[3]));
					}
					
					else if(lineBuffer.startsWith("Tr") || lineBuffer.startsWith("d"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.myAlpha = Float.parseFloat(str[1]);
					}
					else if(lineBuffer.startsWith("Ns"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.myShine = Float.parseFloat(str[1]);
					}
					else if(lineBuffer.startsWith("sharpness"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.mySharpness = Float.parseFloat(str[1]);
					}
					else if(lineBuffer.startsWith("illum")){
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.myIllum = Integer.parseInt(str[1]);
					}
					else if(lineBuffer.startsWith("map_Ka"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.myAmbientTextureFile = str[str.length-1];
					}
					else if(lineBuffer.startsWith("map_Kd"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.myDiffuseTextureFile = str[str.length-1];
					}
					else if(lineBuffer.startsWith("map_Ks"))
					{
						String[] str=lineBuffer.split("[ ]+");
						currentMtl.mySpecularTextureFile = str[str.length-1];
					}
				}
				
				if(currentMtl != null)
					myMaterials.add(currentMtl);
			}
			catch(IOException e){
			}
		}
		
	}
}
