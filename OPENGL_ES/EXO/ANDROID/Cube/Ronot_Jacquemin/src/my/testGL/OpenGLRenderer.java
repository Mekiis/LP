package my.testGL;

import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.*;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRenderer implements Renderer 
{
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		// Ici : code d'initialisation			
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClearDepthf(1.0f);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glDepthFunc(GL10.GL_LEQUAL);		
	}
	
	public void onDrawFrame(GL10 gl)
	{				
		// Ici : Code de dessin
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);		
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		// Resize code goes here
		gl.glViewport(0, 0, width, height);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 45.0f, (float)width/(float)height, 1.0f, 100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		// Placement de la camera
		GLU.gluLookAt(gl, 0, 1.5f, 4.0f, 0, 0, 0, 0, 1, 0);		
	}
}
