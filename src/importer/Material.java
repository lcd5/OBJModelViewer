package importer;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Material 
{
	String myName;
	float[] myAmbientColor; 
	float[] myDiffuseColor;
	float[] mySpecularColor;
	float myAlpha;
	float myShine;
	float mySharpness;
	int myIllum;
	String myAmbientTextureFile;
	String myDiffuseTextureFile;
	String mySpecularTextureFile;
	
	public String getName() 
	{
		return myName;
	}

	public float[] getAmbientColor() {
		return myAmbientColor;
	}
	
	public void setAmbientColor(float r, float g, float b) {
		myAmbientColor = new float[3];
		myAmbientColor[0]=r;
		myAmbientColor[1]=g;
		myAmbientColor[2]=b;
	}

	public float[] getDiffuseColor() {
		return myDiffuseColor;
	}
	
	public void setDiffuseColor(float r, float g, float b) {
		myDiffuseColor = new float[3];
		myDiffuseColor[0]=r;
		myDiffuseColor[1]=g;
		myDiffuseColor[2]=b;
	}

	public float[] getSpecularColor() {
		return mySpecularColor;
	}
	
	public void setSpecularColor(float r, float g, float b) {
		mySpecularColor = new float[3];
		mySpecularColor[0]=r;
		mySpecularColor[1]=g;
		mySpecularColor[2]=b;
	}

	public float getAlpha() {
		return myAlpha;
	}

	public float getShine() {
		return myShine;
	}

	public int getIllum() {
		return myIllum;
	}

	public String getAmbinetTextureFile() {
		return myAmbientTextureFile;
	}
	
	public FloatBuffer getAmbientColorBuffer()
	{
		FloatBuffer fBuf;
		ByteBuffer bBuf = ByteBuffer.allocateDirect(12);
		bBuf.order(ByteOrder.nativeOrder());
		fBuf = bBuf.asFloatBuffer();
		fBuf.put(myAmbientColor);
		fBuf.position(0);
		
		return fBuf;
	}
}

