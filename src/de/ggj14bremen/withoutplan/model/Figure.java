package de.ggj14bremen.withoutplan.model;

public class Figure extends ColoredItem
{
	public enum Orientation
	{
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
		
		public final int getAngle()
		{
			switch (this)
			{
				case TOP: 		return 0;
				case BOTTOM: 	return 180;
				case LEFT: 		return 90;
				case RIGHT: 	return 270;
				default:		return 0;
			}
		}
	}
	
	private int x;
	private int y;
	private Orientation orientation;
	private WPColor color;
	private float[] auraColorArray;
	
	public Figure()
	{
		super(1f, 0f, 0f, 1f);
		this.x = -1;
		this.y = -1;
		this.auraColorArray = new float[]{0f, 1f, 0f, 1f};
		// TODO color param
		setColor(WPColor.RED);
	}
	
	public boolean hasValidPosition()
	{
		return x >= 0;
	}
	
	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public Orientation getOrientation()
	{
		return orientation;
	}
	
	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}
	
	public WPColor getColor()
	{
		return color;
	}
	
	public void setColor(WPColor color)
	{
		this.color = color;
		switch (color)
		{
			case RED: setColorArray(1f, 0f, 0f, 1f); break;
			case GREEN: setColorArray(0f, 1f, 0f, 1f); break;
			case BLUE: setColorArray(0f, 0f, 1f, 1f); break;
			default: setColorArray(1f, 0f, 0f, 1f); break; // TODO log/exception
		}

		switch (color.getContrary())
		{
			case RED: setAuraColorArray(1f, 0f, 0f, 1f); break;
			case GREEN: setAuraColorArray(0f, 1f, 0f, 1f); break;
			case BLUE: setAuraColorArray(0f, 0f, 1f, 1f); break;
			default: setAuraColorArray(1f, 0f, 0f, 1f); break; // TODO log/exception
		}
	}
	
	protected void setAuraColorArray(float r, float g, float b, float a)
	{
		auraColorArray[0] = r;
		auraColorArray[1] = g;
		auraColorArray[2] = b;
		auraColorArray[3] = a;
	}

	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public float[] getAuraColorArray()
	{
		return auraColorArray;
	}
	
	public float[] getColorArray()
	{
		return colorArray;
	}
	
	public void updateColorArray()
	{
		return;
	}
	
	public String getColorString()
	{
		switch (color)
		{
			case RED: return "RED";
			case GREEN: return "GREEN";
			case BLUE: return "BLUE";
			default: return "DEFAULT";
		}
	}
	
}
