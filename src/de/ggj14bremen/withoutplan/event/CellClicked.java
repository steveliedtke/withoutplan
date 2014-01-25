package de.ggj14bremen.withoutplan.event;

public class CellClicked {

	private final int x;
	
	private final int y;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public CellClicked(int x, int y){
		this.x = x;
		this.y = y;
	}
}
