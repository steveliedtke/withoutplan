package de.ggj14bremen.withoutplan.view;

import javax.microedition.khronos.opengles.GL10;

public abstract class Shape
{
	protected float[] color = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
	
    public abstract void draw(GL10 gl);
	
	public void setColor(float r, float g, float b)
	{
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
	}
	public void setColor(float r, float g, float b, float alpha)
	{
		this.color[0] = r;
		this.color[1] = g;
		this.color[2] = b;
		this.color[3] = alpha;
	}

	public void setColor(float[] ccolor)
	{
		this.color[0] = ccolor[0];
		this.color[1] = ccolor[1];
		this.color[2] = ccolor[2];
	}
	public void setAlpha(float alpha)
	{
		this.color[3] = alpha;
	}
}
