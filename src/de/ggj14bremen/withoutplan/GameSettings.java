package de.ggj14bremen.withoutplan;

import android.content.Context;
import android.content.SharedPreferences;

public class GameSettings {

	private int boardSizeX;
	
	private int boardSizeY;
	
	private int amountFigures;
	
	private long stepTime;
	
	public GameSettings(final Context context){
		SharedPreferences settings = context.getSharedPreferences("WithoutPlanPreferences", 0);
		this.setBoardSizeX(settings.getInt("boardSizeX", 6));
		this.setBoardSizeY(settings.getInt("boardSizeY", 6));
		this.setAmountFigures(settings.getInt("amountFigures", 3));
		this.setStepTime(settings.getLong("stepTime", 10000));
	}

	public int getBoardSizeX() {
		return boardSizeX;
	}

	public void setBoardSizeX(int boardSizeX) {
		this.boardSizeX = boardSizeX;
	}

	public int getBoardSizeY() {
		return boardSizeY;
	}

	public void setBoardSizeY(int boardSizeY) {
		this.boardSizeY = boardSizeY;
	}

	public int getAmountFigures() {
		return amountFigures;
	}

	public void setAmountFigures(int amountFigures) {
		this.amountFigures = amountFigures;
	}

	public long getStepTime() {
		return stepTime;
	}

	public void setStepTime(long stepTime) {
		this.stepTime = stepTime;
	}

	
}
