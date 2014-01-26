package de.ggj14bremen.withoutplan.model;

public class Enemy extends ColoredItem
{
	private int age;
	private int killAge;
	
	public Enemy(int killAge)
	{
		super(0f, 0f, 0f, 0f);
		this.age = 0;
		this.killAge = killAge;
		updateColorArray();
	}

	public void increaseAge()
	{
		if (age < killAge) age++;
	}
	
	public boolean isAlive()
	{
		return age < killAge;
	}
	
	public int getAge()
	{
		return age;
	}

	public int getMaxAge()
	{
		return killAge;
	}
	
	public float[] getColorArray()
	{
		return colorArray;
	}
	
	private void updateColorArray()
	{
		float color = 1.0f - ((float)age + 1) / (killAge + 1);
		setColorArray(color, color, color, 1.0f);
	}
}
