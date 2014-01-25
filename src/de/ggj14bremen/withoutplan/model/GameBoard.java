package de.ggj14bremen.withoutplan.model;

public class GameBoard implements Board
{
	private Cell[][] cells;

	public GameBoard(int width, int height)
	{
		cells = new Cell[width][height];
		for (int x = 0; x < cells.length; x++)
		{
			for (int y = 0; y < cells[x].length; y++)
			{
				cells[x][y] = new Cell();
			}
		}
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
	
	public void spawnEnemy(int x, int y)
	{
		// TODO set maxAge from settings or randomly
		cells[x][y].setEnemy(new Enemy(2));
	}
	
	public void moveFigure(Figure figure, int x, int y)
	{
		if (figure.hasValidPosition())
		{
			cells[figure.getX()][figure.getY()].setFigure(null);			
		}
		cells[x][y].setFigure(figure);
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