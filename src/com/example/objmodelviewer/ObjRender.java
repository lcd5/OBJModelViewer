package com.example.objmodelviewer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

class ObjRender implements GLSurfaceView.Renderer
{
	private Scene myScene;
	
	public ObjRender(Scene scene)
	{
		myScene = scene;
	}
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
		myScene.renderAll(gl);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
	}
}
