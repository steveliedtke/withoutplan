package de.ggj14bremen.withoutplan;

import android.content.Context;
import android.util.Log;

public class GameSettings {

	private static int boardSizeX = 6;
	
	private static int boardSizeY = 6;
	
	private static int amountFigures = 3;
	
	private static long stepTime = 10000;

	private static boolean muted;
	
	private static float volume;
	
	public GameSettings(final Context context)
	{
		/*SharedPreferences settings = context.getSharedPreferences("WithoutPlanPreferences", 0);
		this.setBoardSizeX(settings.getInt("boardSizeX", 6));
		this.setBoardSizeY(settings.getInt("boardSizeY", 6));
		this.setAmountFigures(settings.getInt("amountFigures", 3));*/
		//this.setStepTime(settings.getLong("stepTime", 10000));
	}

	public static int getBoardSizeX() {
		return boardSizeX;
	}

	public static void setBoardSizeX(int boardSizeX) {
		GameSettings.boardSizeX = boardSizeX;
	}

	public static int getBoardSizeY() {
		return boardSizeY;
	}

	public static void setBoardSizeY(int boardSizeY) {
		GameSettings.boardSizeY = boardSizeY;
	}

	public static  int getAmountFigures() {
		return amountFigures;
	}

	public static void setAmountFigures(int amountFigures) {
		GameSettings.amountFigures = amountFigures;
	}

	public static long getStepTime() {
		return stepTime;
	}

	public static void setStepTime(long stepTime) {
		GameSettings.stepTime = stepTime;
	}

	public static void setVolume(float volume)
	{
		if(MainActivity.DEBUG) Log.d(MainActivity.TAG, "GameSettings.setVolume() "+volume);
		
		GameSettings.volume = volume;
	}
	public static float getVolume()
	{
		return GameSettings.volume;
	}

	public static boolean isMuted()
	{
		return muted;
	}

	public static void setMuted(boolean muted)
	{
		GameSettings.muted = muted;
	}

	
}
