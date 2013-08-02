package com.example.objmodelviewer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import android.opengl.GLSurfaceView;
import importer.*;

public class MainActivity extends Activity {
	private GLSurfaceView myGLSurfaceView;
	private ObjRender myRender;
	private Scene myScene;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myRender = new ObjRender(myScene);
		myGLSurfaceView = (GLSurfaceView)findViewById(R.id.MainSurfaceView);
		myGLSurfaceView.setRenderer(myRender);
		
		Importer importer = new ObjImporter();
		importer.importFile(this.getResources().getAssets(), "banana.obj");
		
		myScene = new Scene();
		myScene.update(importer);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
