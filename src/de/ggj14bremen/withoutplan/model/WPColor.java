package de.ggj14bremen.withoutplan.model;

import android.graphics.Color;

public enum WPColor {
	RED,
	BLUE,
	GREEN;
	
	public WPColor getContrary(){
		WPColor result = null;
		switch(this){
		case RED:
			result = BLUE;
			break;
		case BLUE:
			result = GREEN;
			break;
		case GREEN:
			result = RED;
			break;
		}
		return result;
	}

	public int getColorAsInt()
	{
		switch (this)
		{
			case RED: return Color.RED;
			case BLUE:return Color.BLUE;	
			case GREEN:return Color.GREEN;
			default: return Color.MAGENTA;
		}
	}
}
