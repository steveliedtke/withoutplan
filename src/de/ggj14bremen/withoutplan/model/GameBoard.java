package de.ggj14bremen.withoutplan.model;

public class GameBoard implements Board{

	private Cell[][] cells;
	
	public void init(int x, int y){
		// TODO create cell array
	}
	
	public Cell getCell(int x, int y){
		// TODO return cell x,y
		return null;
	}
	
	public void spawnEnemy(int x, int y){
		// TODO
	}
	
	public void moveFigure(Figure figure, int x, int y){
		// TODO
	}

	@Override
	public Cell[][] getCells() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void showOrientation(Figure figure){
		// TODO
	}
	
	public void showMoveTarget(Figure figure){
		// TODO
	}
}