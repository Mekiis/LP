package my.testGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class myGLView extends GLSurfaceView 
{
	private OpenGLRenderer myRenderer;

	public myGLView(Context context) 
	{
		super(context);
		myRenderer = new OpenGLRenderer();
		setRenderer(myRenderer);
	}	
}
