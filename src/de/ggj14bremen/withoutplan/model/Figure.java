package de.ggj14bremen.withoutplan.model;



public class Figure
{
	public enum Orientation
	{
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	private Orientation orientation;
	private WPColor color;
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	public WPColor getColor() {
		return color;
	}
	
	public void setColor(WPColor color) {
		this.color = color;
	}
}
