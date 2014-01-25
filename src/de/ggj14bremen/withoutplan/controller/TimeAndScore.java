package de.ggj14bremen.withoutplan.controller;

public class TimeAndScore {

	private long gameTime;
	
	private long stepTime;
	
	private int score;
	
	public TimeAndScore(long gameTime, long stepTime, int score){
		this.gameTime = gameTime;
		this.stepTime = stepTime;
		this.score = score;
	}
	
	public boolean reduceGameTime(long timeToReduce){
		gameTime -= timeToReduce;
		return gameTime < 0L;
	}
	
	public boolean reduceStepTime(long timeToReduce){
		stepTime -= timeToReduce;
		return stepTime < 0L;
	}
	
	public void addScore(int scoreToAdd){
		score += scoreToAdd;
	}
	
	public long getGameTime() {
		return gameTime;
	}

	public void setGameTime(long gameTime) {
		this.gameTime = gameTime;
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
