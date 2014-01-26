package de.ggj14bremen.withoutplan.model;

import android.util.Log;
import de.ggj14bremen.withoutplan.MainActivity;


public class GameBoard implements Board
{
	public static final String TAG = "BOARD";
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
	
	public void spawnEnemy(int x, int y, int timeToKill)
	{
		Cell cell = cells[x][y];
		Enemy enemy = new Enemy(timeToKill, cell);
		cell.setEnemy(enemy);
		updateWatchingFigures(cell);
		enemy.updateColorArray();
	}
	
	@Deprecated
	/**
	 * Please use spawnEnemy(int x, int y, int timeToKill)
	 * @param x x position
	 * @param y y position
	 */
	public void spawnEnemy(int x, int y)
	{
		spawnEnemy(x, y, 3);
	}
	
	public void removeEnemy(int x, int y)
	{
		Cell cell = cells[x][y];
		cell.setEnemy(null);
		updateWatchingFigures(cell);
		cell.updateColorArray();
	}
	
	public void moveFigure(Figure figure, int x, int y)
	{
		if(MainActivity.DEBUG)Log.d(TAG, String.format("%s.moveFigure(%s %d, %d to %d, %d)", getClass().getSimpleName(), figure.getColorString(), figure.getX(), figure.getY(), x, y));
		
		Cell cell;
		
		if (figure.hasValidPosition())
		{
			setWalkableStepping(figure, false);
			setFigureOrientation(figure, false);
			cell = cells[figure.getX()][figure.getY()];
			cell.setFigure(null);
			updateWatchingFigures(cell);
		}
		cell = cells[x][y];
		cell.setFigure(figure);
		updateWatchingFigures(cell);
		figure.setPosition(x, y);
		
		setFigureOrientation(figure, true);
	}

	public void orientateFigure(Figure figure, Figure.Orientation orientation)
	{
		// done in showOrientationOptions: setFigureOrientation(figure, false);
		if(MainActivity.DEBUG) Log.d(TAG, String.format("%s.orientateFigure(%s %s)", getClass().getSimpleName(), figure.getColorString(), getOrientationString(orientation)));
		setOrientationOptions(figure, false);
		figure.setOrientation(orientation);
		setFigureOrientation(figure, true);
	}
	
	private void updateWatchingFigures(Cell cell)
	{
		for (Figure figure: cell.getWatchingFigures())
		{
			updateFigureOrientation(figure);
		}
	}
	
	private void updateFigureOrientation(Figure figure)
	{
		setFigureOrientation(figure, false);
		setFigureOrientation(figure, true);
	}
	
	private String getOrientationString(Figure.Orientation orientation)
	{
		switch (orientation)
		{
			case TOP: return "TOP";
			case BOTTOM: return "BOTTOM";
			case LEFT: return "LEFT";
			case RIGHT: return "RIGHT";
			default: return "DEFAULT";
		}
	}
	
	public void showOrientation(Figure figure)
	{
		setFigureOrientation(figure, false);
		setOrientationOptions(figure, true);
	}
	
	public void showMoveTarget(Figure figure)
	{
		setWalkableStepping(figure, true);
	}
	
	private void setFigureOrientation(Figure figure, boolean add)
	{
		if(MainActivity.DEBUG)Log.d(TAG, String.format("%s.setFigureOrientation(%s %s %s)", getClass().getSimpleName(), add ? "add" : "remove", figure.getColorString(), getOrientationString(figure.getOrientation())));
		
		int stepX = 0;
		int stepY = 0;
		
		switch(figure.getOrientation())
		{
			case TOP:
				stepY = 1;
				break;
			case BOTTOM:
				stepY = -1;
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

				
				if(MainActivity.DEBUG)Log.d(TAG, String.format("%s.setFigureOrientation(add %s %d, %d)", getClass().getSimpleName(), figure.getColorString(), x, y));
				
				cell.addWatchingFigure(figure);
				
				if (cell.hasFigure()) break;
				if (cell.hasEnemy()) break;
			}
			else
			{
				if(MainActivity.DEBUG)Log.d(TAG, String.format("%s.setFigureOrientation(remove %s %d, %d)", getClass().getSimpleName(), figure.getColorString(), x, y));
				
				cells[x][y].removeWatchingFigure(figure);
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
				stepY = 1;
				break;
			case BOTTOM:
				stepY = -1;
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

				if (!cell.isAlive()) break;
//				if (cell.hasFigure()) break;
				
				cell.setOrientationOption(figure);

				if (cell.hasFigure()) break;
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
		
	private void setWalkableJumping(Figure figure, boolean walkable)
	{
		Cell cell;
		for (int x = 0; x < cells.length; x++)
		{
			for (int y = 0; y < cells[x].length; y++)
			{
				cell = cells[x][y];
				cell.setMoveOption(walkable &&
						!cell.hasEnemy() &&
						(!cell.hasFigure() || figure.equals(cell.getFigure())) &&
						Math.abs(figure.getX() - x) + Math.abs(figure.getY() - y) <= MOVE_RANGE ? figure : null);
			}
		}
	}
	
	private void setWalkableStepping(Figure figure, boolean walkable)
	{
		if (walkable)
		{
			// step through
			makeStep(figure, figure.getX(), figure.getY(), MOVE_RANGE);
		}
		else
		{
			// clear all move options
			for (int x = 0; x < cells.length; x++)
			{
				for (int y = 0; y < cells[x].length; y++)
				{
					cells[x][y].setMoveOption(null);
				}
			}
		}
	}
	
	private void makeStep(Figure figure, int x, int y, int steps)
	{
		if (x < 0 || x >= cells.length || y < 0 || y >= cells[0].length) return;
		
		Cell cell = cells[x][y];
		cell.setMoveOption(!cell.hasEnemy() &&
				(!cell.hasFigure() || figure.equals(cell.getFigure())) ? figure : null);
		
		if (steps > 0 && cell.isWalkable())
		{
			makeStep(figure, x, y + 1, steps - 1);
			makeStep(figure, x - 1, y, steps - 1);
			makeStep(figure, x + 1, y, steps - 1);
			makeStep(figure, x, y - 1, steps - 1);
		}
	}
}