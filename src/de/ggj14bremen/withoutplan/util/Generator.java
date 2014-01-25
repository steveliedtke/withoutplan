package de.ggj14bremen.withoutplan.util;

public class Generator {

	
	public static int randomIntBetween(int from, int to){
		to++;
		return (int) (Math.random() * (from - to) + to);
	}
}
