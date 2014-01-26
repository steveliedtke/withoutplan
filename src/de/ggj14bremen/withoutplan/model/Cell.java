package de.ggj14bremen.withoutplan.model;

import java.util.HashSet;
import java.util.Set;

public class Cell extends ColoredItem
{
	private Figure figure;
	private Enemy enemy;
	private Set<Figure> watchingFigures;
	private boolean walkable;
	private Figure orientationOption;
	//private float[] colorArray;
	
	public Cell()
	{
		super(1f, 1f, 1f, 1f);
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
	
	@Deprecated
	/**
	 * Please use getColorArray()
	 * @return rgba color array
	 */
	public float[] getColor()
	{
		return getColorArray();
	}
	
	public float[] getColorArray()
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
		
		int colorCount = 1 + watchingFigures.size();
		float alpha = 1.0f / colorCount;
		float[] colorPart;
		
		if (hasEnemy())
		{
			setColorArray(0f, 0f, 0f, 0f);
			colorPart =  enemy.getColorArray();
			addColorPart(colorPart, alpha);
		}
		else
		{
			setColorArray(1f, 1f, 1f, alpha);
		}
		
		for (Figure figure: watchingFigures)
		{
			colorPart = figure.getColorArray();
			addColorPart(colorPart, alpha);
		}
		
		return colorArray;
	}
	
//	private void setColor(float r, float g, float b, float a)
//	{
//		colorArray[0] = r;
//		colorArray[1] = g;
//		colorArray[2] = b;
//		colorArray[3] = a;
//	}
		
	private void addColorPart(float[] colorPart, float alpha)
	{
		for (int i = 0; i < colorArray.length && i < colorPart.length; i++)
		{
			colorArray[i] += colorPart[i] * alpha;
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
