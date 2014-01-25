package de.ggj14bremen.withoutplan.controller;

public class TimeAndScore {

	
	private long stepTime;
	
	private int score;
	
	public TimeAndScore(long stepTime, int score){
		this.stepTime = stepTime;
		this.score = score;
	}
	
	public boolean reduceStepTime(long timeToReduce){
		stepTime -= timeToReduce;
		return stepTime < 0L;
	}
	
	public void addScore(int scoreToAdd){
		score += scoreToAdd;
	}

	public long getStepTime() {
		return stepTime;
	}

	public void setStepTime(long stepTime) {
		this.stepTime = stepTime;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
