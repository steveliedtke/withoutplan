package de.ggj14bremen.withoutplan.model;

public class Enemy extends ColoredItem
{
	private int age;
	private int timeToKill;
	
	public Enemy(int killAge)
	{
		super(0f, 0f, 0f, 0f);
		this.age = 0;
		this.timeToKill = killAge;
		updateColorArray();
	}

	public void increaseAge()
	{
		if (age < timeToKill)
		{
			age++;
			updateColorArray();
		}
	}
	
	public boolean isAlive()
	{
		return age < timeToKill;
	}
	
	public int getAge()
	{
		return age;
	}

	public int getMaxAge()
	{
		return timeToKill;
	}
	
	public float[] getColorArray()
	{
		return colorArray;
	}
	
	private void updateColorArray()
	{
		float color = 1.0f - ((float)age + 1) / (timeToKill + 1);
		setColorArray(color, color, color, 1.0f);
	}
}
