package de.ggj14bremen.withoutplan.controller;

public class TimeScoreInfo {

	
	private long stepTime;
	
	private int score;
	
	private String infoText;
	
	private boolean timeShowed;
	
	public TimeScoreInfo(long stepTime, int score){
		this.stepTime = stepTime;
		this.score = score;
		this.infoText = "";
		this.timeShowed = false;
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

	public String getInfoText() {
		return infoText;
	}

	public void setInfoText(String infoText) {
		this.infoText += infoText+"\n";
	}

	public boolean isTimeShowed() {
		return timeShowed;
	}

	public void setTimeShowed(boolean timeShowed) {
		this.timeShowed = timeShowed;
	}
}
