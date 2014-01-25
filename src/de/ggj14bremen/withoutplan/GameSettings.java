package de.ggj14bremen.withoutplan;

import android.content.Context;
import android.content.SharedPreferences;

public class GameSettings {

	private int boardSizeX;
	
	private int boardSizeY;
	
	public GameSettings(final Context context){
		SharedPreferences settings = context.getSharedPreferences("WithoutPlanPreferences", 0);
		this.setBoardSizeX(settings.getInt("boardSizeX", 6));
		this.setBoardSizeY(settings.getInt("boardSizeY", 6));
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
}
