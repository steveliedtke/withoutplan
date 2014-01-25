package de.ggj14bremen.withoutplan.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import de.ggj14bremen.withoutplan.MainActivity;
import de.ggj14bremen.withoutplan.controller.Game;

public class GLGameSurfaceView extends GLSurfaceView
{
	private GestureDetector gestureDetector;
	private ScaleGestureDetector scaleDetector;
	private GLRenderer glRenderer;
	
	public GLGameSurfaceView(Context context, Game gameThread)
	{
		super(context);
		this.gestureDetector 	= new GestureDetector(context, new MyGestureListener());
		this.scaleDetector 		= new ScaleGestureDetector(context, new MyScaleListener());
		glRenderer				= new GLRenderer(gameThread);
		setRenderer(glRenderer);
	     // Render the view only when there is a change in the drawing data.
        // To allow the triangle to rotate automatically, this line is commented out:
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
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
			return true;
		}
	}
	private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
	{

		@Override
		public boolean onDown(MotionEvent e) 
		{
			final int x = (int) e.getX();
			final int y = (int) (GLGameSurfaceView.this.getHeight()-(e.getY()));
			
			if(MainActivity.DEBUG) Log.d(MainActivity.TAG, getClass().getSimpleName()+".onDown() raw: "+x+"/"+y+" scale: "+glRenderer.getScale());

			int cellX = (int) (x/glRenderer.getScale());
			
			if(MainActivity.DEBUG) Log.d(MainActivity.TAG, getClass().getSimpleName()+"Cell: "+cellX+"/"+y);

			
			//check if the hud consumes the click
			/*if(!hud.processEvent(x, y, false))
			{
				projection.fromPixels(x, y, cmPoint);
				SacracyApplication.dispatchEvent(ClickEvent.obtainEvent().setData(false,x,y,cmPoint.x,cmPoint.y));
			}*/
			return true;
		}
		@Override
		public void onLongPress(MotionEvent e) 
		{
			super.onLongPress(e);
			final int x = (int) e.getX();
			final int y = (int) (GLGameSurfaceView.this.getHeight()-(e.getY()));
			//hud.processEvent(x, y, true);
		}
	}
}
