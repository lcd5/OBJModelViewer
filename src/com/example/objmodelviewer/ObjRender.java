package com.example.objmodelviewer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;

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
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
		gl.glLoadIdentity();			
		
		myScene.renderAll(gl);
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		gl.glViewport(0, 0, width, height); 	
		gl.glMatrixMode(GL10.GL_PROJECTION); 	
		gl.glLoadIdentity(); 					

		//Calculate The Aspect Ratio Of The Window
		//
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 500.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	
		gl.glLoadIdentity(); 
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		gl.glShadeModel(GL10.GL_SMOOTH); 			
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f); 	
		//gl.glClearDepthf(1.0f); 					
		//gl.glEnable(GL10.GL_DEPTH_TEST); 			
		//gl.glDepthFunc(GL10.GL_LEQUAL); 	
		//gl.glDisable(GL10.GL_CULL_FACE);
	
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
	}
}
