package de.ggj14bremen.withoutplan.model;

import java.util.HashSet;
import java.util.Set;

import de.ggj14bremen.withoutplan.view.Colors;

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
	
	public boolean hasFigure()
	{
		return figure != null;
	}
	
	public boolean hasEnemy()
	{
		return enemy != null;
	}
	
	public boolean isAlive()
	{
		return enemy == null || enemy.isAlive(); 
	}
	
	public float[] getColor()
	{
		// TODO aura
		
		if (!isAlive())
		{
			return enemy.getColorArray();
		}
		
		if (hasFigure())
		{
			return figure.getColorArray();
		}
		
		float[] colorArray =  new float[]{0f, 0f, 0f, 0f};

		int colorCount = 1 + watchingFigures.size();
		float weight = 1.0f / colorCount;
		float[] colorPart;
		
		if (hasEnemy())
		{
			colorPart =  enemy.getColorArray();
			addColorPart(colorArray, colorPart, weight);
		}
		else
		{
			colorPart = Colors.WHITE;
			addColorPart(colorArray, colorPart, weight);
		}
		
		for (Figure figure: watchingFigures)
		{
			colorPart = figure.getColorArray();
			addColorPart(colorArray, colorPart, weight);
		}
		
		return colorArray;
	}
	
	private void addColorPart(float[] color, float[] colorPart, float weight)
	{
		for (int i = 0; i < color.length; i++)
		{
			color[i] += colorPart[i] * weight;
		}
	}
	
	public boolean isVisible()
	{
		return orientationOption != null;
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
