package importer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class ObjImporter implements Importer
{
	private String myFileName;
	
	Vector<Short> myFaces=new Vector<Short>();
	Vector<Short> myUVIndices=new Vector<Short>();
	Vector<Short> myNormalIndices=new Vector<Short>();
	
	Vector<Float> myVertices=new Vector<Float>();
	Vector<Float> myNormals=new Vector<Float>();
	Vector<Float> myUVs=new Vector<Float>();
	
	@Override
	public void importFile(String fileName)
	{
		myFileName = fileName;
		BufferedReader reader=null;
		String lineBuffer = null;
		
		try { 
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
		} 		
		catch(IOException e){
		}
		
		// Parser the file content.
		//
		try{
			while( (lineBuffer = reader.readLine()) != null )
			{
				lineBuffer.trim();
				
				if(lineBuffer.startsWith("v"))
					parserVertexLine(lineBuffer);
				else if(lineBuffer.startsWith("vn"))
					parserNormalLine(lineBuffer);
				else if(lineBuffer.startsWith("vt"))
					parserUVLine(lineBuffer);
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
	}
	
	private void parserFLine(String buffer)
	{
		String []token = buffer.split("[ ]+");
		int len = token.length;
		
		// Only support one triangle per face
		//
		if(len != 4)
			return;
		
		// case f: v
		//
		if( token[1].matches("[0-9]+"))
		{
			for(int i=0; i<len; i++)
			{
				Short index = Short.valueOf(token[i]);
				index--;
				myFaces.add(index);
			}
		}
		//case f: v/vt
		//
		else if(token[1].matches("[0-9]+/[0-9]+"))
		{
			for(int i=0; i<len; i++)
			{
				String []vUV = buffer.split("/");
				
				Short vIndex = Short.valueOf(vUV[0]);
				vIndex--;
				myFaces.add(vIndex);
				
				Short uvIndex = Short.valueOf(vUV[1]);
				uvIndex--;
				myUVIndices.add(uvIndex);
			}
		}
		// case f:v//vn
		//
		else if(token[1].matches("[0-9]+//[0-9]+"))
		{
			String []vn = buffer.split("//");
			
			Short vIndex = Short.valueOf(vn[0]);
			vIndex--;
			myFaces.add(vIndex);
			
			
			Short nIndex = Short.valueOf(vn[1]);
			nIndex--;
			myNormalIndices.add(nIndex);
		}
		// case f: v/vt/vn
		//
		else if(token[1].matches("[0-9]+/[0-9]+/[0-9]+"))
		{
			String []vUVn = buffer.split("/");
			
			Short vIndex = Short.valueOf(vUVn[0]);
			vIndex--;
			myFaces.add(vIndex);
			
			Short uvIndex = Short.valueOf(vUVn[1]);
			uvIndex--;
			myUVIndices.add(uvIndex);
			
			Short nIndex = Short.valueOf(vUVn[2]);
			nIndex--;
			myNormalIndices.add(nIndex);
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
		
	}
	
	private void readMaterial(String lineBuffer)
	{
		
	}
}
