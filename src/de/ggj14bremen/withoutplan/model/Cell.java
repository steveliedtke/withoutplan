package de.ggj14bremen.withoutplan.model;

import java.util.HashSet;
import java.util.Set;

public class Cell
{
	private Figure figure;
	private Enemy enemy;
	private Set<Figure> watchingFigures;
	private boolean walkable;
	private Figure orientationOption;
	
	public Cell()
	{
		this.watchingFigures = new HashSet<Figure>();
	}
	
	public Figure getFigure()
	{
		return figure;
	}
	
	public void setFigure(Figure figure)
	{
		this.figure = figure;
	}
	
	public Enemy getEnemy()
	{
		return enemy;
	}
	
	public void setEnemy(Enemy enemy)
	{
		this.enemy = enemy;
	}
	
	public Set<Figure> getWatchingFigures()
	{
		return watchingFigures;
	}
	
	public void setWatchingFigures(Set<Figure> watchingFigures)
	{
		this.watchingFigures = watchingFigures;
	}
	
	public boolean isWalkable()
	{
		return walkable;
	}
	
	public void setWalkable(boolean walkable)
	{
		this.walkable = walkable;
	}
	
	public Figure getOrientationOption()
	{
		return orientationOption;
	}
	
	public void setOrientationOption(Figure orientationOption)
	{
		this.orientationOption = orientationOption;
	}
}
