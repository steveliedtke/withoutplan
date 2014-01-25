package de.ggj14bremen.withoutplan.view;



import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import de.ggj14bremen.withoutplan.controller.Game;

public class GLRenderer implements Renderer 
{
	private Square 		square;		// the square
	private Game 		game;
	
	/** Constructor to set the handed over context 
	 * @param gameThread */
	public GLRenderer(Game game) 
	{
		this.square		= new Square();
		this.game		= game;
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		if(height == 0)
		{ 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}

	@Override
	public void onDrawFrame(GL10 gl) 
	{
		// clear Screen and Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		// Reset the Modelview Matrix
		gl.glLoadIdentity();

		// Drawing
		gl.glTranslatef(0.0f, 0.0f, -5.0f);		// move 5 units INTO the screen
												// is the same as moving the camera 5 units away
		
		game.getBoard().
		
		square.draw(gl);						// Draw the triangle

	}
}
