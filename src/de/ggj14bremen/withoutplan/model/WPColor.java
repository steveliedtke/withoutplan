package de.ggj14bremen.withoutplan.model;

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
}
