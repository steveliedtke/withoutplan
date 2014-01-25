package de.ggj14bremen.withoutplan.model;

public class Enemy
{
	private int age;
	private int killAge;
	
	public Enemy(int killAge)
	{
		this.age = 0;
		this.killAge = killAge;
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
		float color = 1.0f - ((float)age) / killAge;
		return new float[]{color, color, color, 1.0f};
	}
}
