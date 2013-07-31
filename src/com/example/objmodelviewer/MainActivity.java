package com.example.objmodelviewer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends Activity {
	private GLSurfaceView myGLSurfaceView;
	private ObjRender myRender;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myRender = new ObjRender();
		myGLSurfaceView = (GLSurfaceView)findViewById(R.id.MainSurfaceView);
		myGLSurfaceView.setRenderer(myRender);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

class ObjRender implements GLSurfaceView.Renderer
{
	public ObjRender()
	{
	}
	
	@Override
	public void onDrawFrame(GL10 gl)
	{
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
