package de.ggj14bremen.withoutplan.view;



import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import de.ggj14bremen.withoutplan.controller.Game;
import de.ggj14bremen.withoutplan.model.Cell;

public class GLRenderer implements Renderer 
{
	private Square 		square;		// the square
	private Game 		game;
	private float 		scale 	= 1f;
	private float 		padding	= 1.1f;
	private int 		widht, height;
	private float 		cellSize;
	
	/** Constructor to set the handed over context 
	 * @param gameThread */
	public GLRenderer(Game game) 
	{
		this.game		= game;
		this.square		= new Square();
	}
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST); //GL10.GL_FASTEST, GL10.GL_NICEST
		gl.glShadeModel(GL10.GL_FLAT);
		gl.glDisable(GL10.GL_DEPTH_TEST); // disables depth-buffer for hidden surface removal 
		gl.glEnable(GL10.GL_TEXTURE_2D);     	
		gl.glDisable(GL10.GL_DITHER); // performance tweak especially on software renderer.
		gl.glDisable(GL10.GL_LIGHTING);	
        //gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
        gl.glEnable(GL10.GL_BLEND);        //transparency
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

	}
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		this.widht 	= width;
		this.height = height;
		if(height == 0)
		{ 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate The Aspect Ratio Of The Window
		//GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);
		GLU.gluOrtho2D(gl, 0, width, 0, height);

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

		int nrOfCells = game.getBoard().getCells().length;
		scale = height/(nrOfCells + nrOfCells*padding);
		
		cellSize = height/nrOfCells;

		gl.glScalef(scale, scale, scale);
		
		gl.glTranslatef(1, 1, 0.0f);

		drawBoard(gl);
	}
	private void drawBoard(GL10 gl)
	{
		gl.glPushMatrix();
		final Cell[][] cells = game.getBoard().getCells();

		for(int col = 0; col < cells.length; col++)
		{
			gl.glPushMatrix();
			gl.glTranslatef(col + col * padding, 0, 0.0f);
			for(int row = 0; row < cells[col].length; row++)
			{
				gl.glPushMatrix();
				gl.glTranslatef(0, row + row * padding, 0.0f);
				//draw basic color			
				square.setColor(1f, 1f, 1f, 1f);
				square.draw(gl);
				
				if(cells[col][row].hasEnemy()) 
				{
					square.setAlpha(.5f);
					square.setColor(Colors.RED);
					square.draw(gl);
				}
				if(cells[col][row].hasFigure()) 
				{
					square.setAlpha(.5f);
					square.setColor(Colors.BLUE);
					square.draw(gl);
				}
				//else if (cells[col][row].)
				//else 							square.setColor(1f, 0f, 1f, 1f);

				gl.glPopMatrix();
			}
			gl.glPopMatrix();
		}
		gl.glPopMatrix();
	}
	
	public void setScale(float scale)
	{
		this.scale *= scale;		
	}
	public float getScale()
	{
		return scale;
	}
	public float getCellSize()
	{
		return cellSize;
	}
}
