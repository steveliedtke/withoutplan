package de.ggj14bremen.withoutplan.controller;

import de.ggj14bremen.withoutplan.model.WPColor;

public class TimeScoreInfo {

	
	private long stepTime;
	
	private int score;
	
	private int blueScore;
	
	private int greenScore;
	
	private int redScore;
	
	private String log = "";
	
	private boolean timeShowed;
	
	public TimeScoreInfo(long stepTime){
		this.stepTime 	= stepTime;
		this.score 		= 0;
		blueScore = 0;
		redScore = 0;
		greenScore = 0;
		this.log 		= "";
		this.timeShowed = false;
	}
	
	public boolean reduceStepTime(long timeToReduce){
		stepTime -= timeToReduce;
		return stepTime < 0L;
	}
	
	public void addScore(){
		score++;
	}
	
	public void addColorScore(WPColor color){
		switch (color) {
		case BLUE:
			this.blueScore++;
			break;
		case GREEN:
			this.greenScore++;
			break;
		case RED:
			this.redScore++;
			break;
		default:
			break;
		}
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
		return log;
	}
	public boolean isTimeShowed() {
		return timeShowed;
	}

	public int getBlueScore() {
		return blueScore;
	}

	public int getGreenScore() {
		return greenScore;
	}

	public int getRedScore() {
		return redScore;
	}

	public void setTimeShowed(boolean timeShowed) {
		this.timeShowed = timeShowed;
	}

	public void clearLog()
	{
		log = "";	
	}
	public void addToLog(String string)
	{
		log = string+"\n"+log;
	}
}
