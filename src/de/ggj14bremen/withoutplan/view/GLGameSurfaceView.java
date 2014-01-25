package de.ggj14bremen.withoutplan.view;

import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import de.ggj14bremen.withoutplan.MainActivity;
import de.ggj14bremen.withoutplan.controller.Game;
import de.ggj14bremen.withoutplan.event.CellClicked;

public class GLGameSurfaceView extends GLSurfaceView
{
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleDetector;
	private GLRenderer glRenderer;
	private	MainActivity activity;
	private Game gameThread;
	
	public GLGameSurfaceView(MainActivity activity, Game gameThread)
	{
		super(activity);
		this.gameThread			= gameThread;
		this.activity			= activity;
		this.gestureDetector 	= new GestureDetector(activity, new MyGestureListener());
		this.scaleDetector 		= new ScaleGestureDetector(activity, new MyScaleListener());
		glRenderer				= new GLRenderer(gameThread);
		setRenderer(glRenderer);
	     // Render the view only when there is a change in the drawing data.
        // To allow the triangle to rotate automatically, this line is commented out:
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event) 
    {
		scaleDetector.onTouchEvent(event);
		gestureDetector.onTouchEvent(event);
        return true;
    }
	private class MyScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener
	{
		@Override
		public boolean onScale(ScaleGestureDetector detector) 
		{
			if(MainActivity.DEBUG) Log.d(MainActivity.TAG, getClass().getSimpleName()+".onScale()");
			glRenderer.setScale(detector.getScaleFactor());
			if(MainActivity.DEBUG)
			{
				Log.d(MainActivity.TAG, "cellSize: "+glRenderer.getCellSize());
			}
			return true;
		}
	}
	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
	{
		@Override
		public boolean onDown(MotionEvent e) 
		{
			final int x = (int) e.getX();
			final int y = (int) e.getY();
			
			if(MainActivity.DEBUG) Log.d(MainActivity.TAG, getClass().getSimpleName()+".onDown() raw: "+x+"/"+y+" scale: "+glRenderer.getScale());

			int cellX = (int) (x / glRenderer.getCellSize());
			int cellY = (int) (y / glRenderer.getCellSize());
			
			if(MainActivity.DEBUG) Log.v(MainActivity.TAG, getClass().getSimpleName()+"Cell: "+cellX+"/"+cellY);
			if(MainActivity.DEBUG) activity.showDebugToast("Cell: "+cellX+"/"+cellY);
			
			gameThread.dispatchEvent(new CellClicked(cellX, cellY));
			
			return true;
		}
		@Override
		public void onLongPress(MotionEvent e) 
		{
			super.onLongPress(e);
		}
	}
}
