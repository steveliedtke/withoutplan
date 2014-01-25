package de.ggj14bremen.withoutplan.model;

public class GameBoard implements Board
{
	private Cell[][] cells;

	public GameBoard(int width, int height)
	{
		cells = new Cell[width][height];
	}
	
	@Override
	public Cell[][] getCells()
	{
		return cells;
	}
	
	public Cell getCell(int x, int y)
	{
		return cells[x][y];
	}
	
	public void spawnEnemy(int x, int y){
		// TODO
	}
	
	public void moveFigure(Figure figure, int x, int y){
		// TODO
	}

	public void orientateFigure(Figure figure, Figure.Orientation orientation)
	{
		// TODO
	}
	
	public void showOrientation(Figure figure){
		// TODO
	}
	
	public void showMoveTarget(Figure figure){
		// TODO
	}
}