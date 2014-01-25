package de.ggj14bremen.withoutplan.model;


public class GameBoard implements Board
{
	public static final int MOVE_RANGE = 2;

	private Cell[][] cells;

	// TODO throw invalid argument exception or impl Factory method
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
	
	public void removeEnemy(int x, int y)
	{
		cells[x][y].setEnemy(null);
	}
	
	public void moveFigure(Figure figure, int x, int y)
	{
		if (figure.hasValidPosition())
		{
			setWalkable(figure, false);
			setFigureOrientation(figure, false);
			cells[figure.getX()][figure.getY()].setFigure(null);			
		}
		cells[x][y].setFigure(figure);
		figure.setPosition(x, y);
		setFigureOrientation(figure, true);
	}

	public void orientateFigure(Figure figure, Figure.Orientation orientation)
	{
		// done in showOrientationOptions: setFigureOrientation(figure, false);
		setOrientationOptions(figure, false);
		figure.setOrientation(orientation);
		setFigureOrientation(figure, true);
	}
	
	public void showOrientation(Figure figure)
	{
		setFigureOrientation(figure, false);
		setOrientationOptions(figure, true);
	}
	
	public void showMoveTarget(Figure figure)
	{
		setWalkable(figure, true);
	}
	private void setFigureOrientation(Figure figure, boolean add)
	{
		int stepX = 0;
		int stepY = -1;
		
		switch(figure.getOrientation())
		{
			case TOP:
				stepY = -1;
				break;
			case BOTTOM:
				stepY = 1;
				break;
			case LEFT:
				stepX = -1;
				break;
			case RIGHT:
				stepX = 1;
				break;
			default:
				stepY = -1;
				break;
		}
		
		int x = figure.getX() + stepX;
		int y = figure.getY() + stepY;
		
		while (x >= 0 && y >= 0 && x < cells.length && y < cells[x].length)
		{
			if (add)
			{
				Cell cell = cells[x][y];

				if (cell.hasFigure()) break;
				
				cell.getWatchingFigures().add(figure);
				
				if (cell.hasEnemy()) break;
			}
			else
			{
				cells[x][y].getWatchingFigures().remove(figure);
			}
			
			x += stepX;
			y += stepY;
		}
	}

	private void setOrientationOptions(Figure figure, boolean add)
	{
		setOrientationOptions(figure, add, Figure.Orientation.TOP);
		setOrientationOptions(figure, add, Figure.Orientation.BOTTOM);
		setOrientationOptions(figure, add, Figure.Orientation.LEFT);
		setOrientationOptions(figure, add, Figure.Orientation.RIGHT);
	}
	
	private void setOrientationOptions(Figure figure, boolean add, Figure.Orientation orientation)
	{
		int stepX = 0;
		int stepY = 0;
		
		switch(orientation)
		{
			case TOP:
				stepY = -1;
				break;
			case BOTTOM:
				stepY = 1;
				break;
			case LEFT:
				stepX = -1;
				break;
			case RIGHT:
				stepX = 1;
				break;
			default:
				stepY = -1;
				break;
		}
		
		int x = figure.getX() + stepX;
		int y = figure.getY() + stepY;
		
		while (x >= 0 && y >= 0 && x < cells.length && y < cells[x].length)
		{
			if (add)
			{
				Cell cell = cells[x][y];

				if (cell.hasFigure()) break;
				
				cell.setOrientationOption(figure);
				
				if (cell.hasEnemy()) break;
			}
			else
			{
				cells[x][y].setOrientationOption(null);
			}
			
			x += stepX;
			y += stepY;
		}				
	}
		
	private void setWalkable(Figure figure, boolean walkable)
	{
		Cell cell;
		for (int x = 0; x < cells.length; x++)
		{
			for (int y = 0; y < cells[x].length; y++)
			{
				cell = cells[x][y];
				cell.setWalkable(walkable &&
						!cell.hasEnemy() &&
						(!cell.hasFigure() || figure.equals(cell.getFigure())) &&
						Math.abs(figure.getX() - x) + Math.abs(figure.getY() - y) <= 2);
			}
		}
	}
}