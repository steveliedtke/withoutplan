package de.ggj14bremen.withoutplan.model;

public class Enemy
{
	private int age;
	private int maxAge;
	
	public Enemy(int maxAge)
	{
		this.age = 0;
		this.maxAge = age;
	}

	public void increaseAge()
	{
		age++;
	}
	
	public boolean isAlive()
	{
		return age <= maxAge;
	}
	
	public int getAge()
	{
		return age;
	}

	public int getMaxAge()
	{
		return maxAge;
	}
}
