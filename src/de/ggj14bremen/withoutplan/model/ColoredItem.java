package de.ggj14bremen.withoutplan.model;

public abstract class ColoredItem
{
	protected float[] colorArray;

	public ColoredItem(float r, float g, float b, float a)
	{
		this.colorArray = new float[]{r, g, b, a};
	}
	
	public abstract float[] getColorArray();
	
	public abstract void updateColorArray();
	
	protected void setColorArray(float r, float g, float b, float a)
	{
		colorArray[0] = r;
		colorArray[1] = g;
		colorArray[2] = b;
		colorArray[3] = a;
	}
}
