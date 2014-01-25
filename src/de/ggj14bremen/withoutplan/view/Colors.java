package de.ggj14bremen.withoutplan.view;

public class Colors
{
	public static final float[] WHITE 	= {1f,1f,1f};
	public static final float[] RED 	= {1f,0,0};
	public static final float[] GREEN 	= {0,1f,0};
	public static final float[] BLUE 	= {0,0,1f};
	public static final float[] YELLOW 	= {1f,0.5f,0f};
	
	public static final float[] getColorWithAlpha(float[] color, float alpha)
	{
		float[] colorWithAlpha = new float[4];
		for (int i = 0; i < color.length && i < colorWithAlpha.length; i++)
		{
			colorWithAlpha[i] = color[i];
		}
		colorWithAlpha[3] = alpha;
		return colorWithAlpha;
	}
}
